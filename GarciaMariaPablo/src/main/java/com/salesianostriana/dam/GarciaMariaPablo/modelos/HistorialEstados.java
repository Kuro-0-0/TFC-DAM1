package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private LocalDate fechaComienzo; // Fehca cuando puso el estado (Incidencia.IEA)
    private LocalDate fechaFinal; // Fecha actual, cuadno se a cambiado el estado

}
