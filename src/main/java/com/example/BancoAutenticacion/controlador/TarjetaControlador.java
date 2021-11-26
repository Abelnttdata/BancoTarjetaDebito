package com.example.BancoAutenticacion.controlador;

import com.example.BancoAutenticacion.Entidad.CompraTarjetaDebito;
import com.example.BancoAutenticacion.Entidad.Cuenta;
import com.example.BancoAutenticacion.Entidad.TarjetaDebito;
import com.example.BancoAutenticacion.servicio.TarjetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarjetadebito")
public class TarjetaControlador {
    private Integer idUsuario;
    private List<Cuenta> cuentaCliente;
    private  Integer idCuenta;
    private double saldo;

    @Autowired
    TarjetaServicio tarjetaServicio;

    @PostMapping("/solicitartarjeta")
    public ResponseEntity<TarjetaDebito> crearTarjetaDebito(@RequestBody TarjetaDebito tarjetaDebito) throws TarjetaCreada, CreacionFallida{
        idUsuario = tarjetaServicio.obtenerId();
        if(idUsuario == null){
            return new ResponseEntity("No se encuentra logeado ", HttpStatus.FORBIDDEN);
        }
        if(tarjetaServicio.crearTarjetaDebito(tarjetaDebito,idUsuario)){
            throw new TarjetaCreada();
        }

        throw new CreacionFallida();
    }

    @GetMapping("/listartarjetasdebito")
    public List<TarjetaDebito> listarTarjetasDeUsuario()throws noTieneTarjeta{
        idUsuario = tarjetaServicio.obtenerId();

         List<TarjetaDebito> tarjetaDebito = tarjetaServicio.listarTarjetasDeUsuario(idUsuario);
        if(tarjetaDebito.isEmpty()){
            throw new noTieneTarjeta();
        }
        return tarjetaDebito;
    }
    @PostMapping("/activartarjeta")
    public ResponseEntity activarTarjeta(@RequestBody TarjetaDebito tarjetaDebito) throws TodaTarjetaActivada{
        Integer tarjetaDebitoPorActivar = tarjetaServicio.econtrarTarjeta(idUsuario, tarjetaDebito.getNumTarjeta());
        if(tarjetaDebitoPorActivar == null){
            throw new TodaTarjetaActivada();
        }
        cuentaCliente = tarjetaServicio.obtenerCuentasClientes();
        if(cuentaCliente == null){
            return new ResponseEntity("No se pudo conectar al servicio cuentas ", HttpStatus.FORBIDDEN);
        }
        Integer numCuenta = tarjetaServicio.buscarNumCuenta(cuentaCliente,tarjetaDebito);
        if(numCuenta == 0){
            return new ResponseEntity("No coincide ninguna cuenta asociada ", HttpStatus.BAD_REQUEST);
        }
        boolean saldo = tarjetaServicio.obtenersaldo(cuentaCliente,numCuenta);
        if (saldo){
            boolean tarjetaActivada = tarjetaServicio.activarTarjeta(idUsuario,tarjetaDebito);
            if(tarjetaActivada){
                return new ResponseEntity("Tarjeta Activada",HttpStatus.ACCEPTED);
            }
            return new ResponseEntity("Saldo insuficiente", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Sucedio un error no se pudo activar", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/nuevolimiteextraccion")
    public ResponseEntity generarNuevoLimiteDeExtraccion(@RequestBody TarjetaDebito tarjetaDebito){
        idUsuario = tarjetaServicio.obtenerId();
        if(idUsuario == null){
            return new ResponseEntity("No se encuentra logeado ", HttpStatus.FORBIDDEN);
        }
        boolean nuevoLimite = tarjetaServicio.generarNuevoLimiteDeExtraccion(tarjetaDebito,idUsuario);
        if(nuevoLimite){
            return new ResponseEntity("Se a generado su nuevo limite", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("A sucedido un error, no se genero su nuevo limite", HttpStatus.FORBIDDEN);
    }

    // Se agregan las compras
    @PostMapping("/agregarcompra")
    public ResponseEntity agregarCompra(@RequestBody CompraTarjetaDebito compraTarjetaDebito) throws noLogeado{
        idUsuario = tarjetaServicio.obtenerId();
        if(idUsuario == null){
            throw  new noLogeado();
        }
        boolean numTarjeta = tarjetaServicio.obtenerNumTarjetaParaCompra(compraTarjetaDebito.getNumTarjeta());
        if(numTarjeta){
            return new ResponseEntity("La tarjeta no existe ", HttpStatus.BAD_REQUEST);
        }
        cuentaCliente = tarjetaServicio.obtenerCuentasClientes();
        boolean saldo = tarjetaServicio.obtenersaldoParaCompra(cuentaCliente,compraTarjetaDebito);
        if(saldo){
            return new ResponseEntity("Saldo insuficiente en su cuenta", HttpStatus.BAD_REQUEST);
        }
        boolean compraExitosa = tarjetaServicio.comprar(compraTarjetaDebito);
        if (compraExitosa){
            return new ResponseEntity("Compra exitosa", HttpStatus.OK);
        }
        return new ResponseEntity("Algo salio mal intente luego", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/listartodaslascompras")
    public List<CompraTarjetaDebito> obtenerListaDeCompras() throws  noTieneCompras, noLogeado{
        idUsuario = tarjetaServicio.obtenerId();
        if(idUsuario == null){
            throw new noLogeado();
        }

    }
}
