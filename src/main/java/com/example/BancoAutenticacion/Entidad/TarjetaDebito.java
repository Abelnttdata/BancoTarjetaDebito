package com.example.BancoAutenticacion.Entidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "tarjetasdebito")
public class TarjetaDebito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer idUsuario;
    private Integer idCuenta;
    private Integer numTarjeta;
    private String marca;
    private String tipo;
    private String estado;
    private Integer limiteExtraccion;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd-MM-yyyy")
   private Date vencimiento;
    private boolean activada = false;
}
