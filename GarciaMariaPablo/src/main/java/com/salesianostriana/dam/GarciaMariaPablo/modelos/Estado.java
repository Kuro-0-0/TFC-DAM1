package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String nombre;
    private String valor;
    private String colorFondo;
    private String colorTexto;
    @Builder.Default
    private boolean activo = true;

    @OneToMany
    @JoinColumn(name = "estado_id")
    private List<Incidencia> incidencias;
}
