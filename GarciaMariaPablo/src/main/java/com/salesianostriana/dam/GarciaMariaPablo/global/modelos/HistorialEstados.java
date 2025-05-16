package com.salesianostriana.dam.GarciaMariaPablo.global.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class HistorialEstados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "incidencia_id")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "estado_incial_id")
    private Estado estadoInicial;

    @ManyToOne
    @JoinColumn(name = "estado_actual_id")
    private Estado estadoActual;

    private LocalDateTime fechaComienzo; // Fehca cuando puso el estado (Incidencia.IEA)
    private LocalDateTime fechaFinal; // Fecha actual, cuadno se a cambiado el estado

}
