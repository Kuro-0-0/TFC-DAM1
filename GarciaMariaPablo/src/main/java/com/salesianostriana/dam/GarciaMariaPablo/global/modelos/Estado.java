package com.salesianostriana.dam.GarciaMariaPablo.global.modelos;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private TipoEstados tipo;

    @OneToMany(mappedBy = "estado") @Builder.Default
    private List<Incidencia> incidencias = new ArrayList<>();

    @OneToMany(mappedBy = "estadoInicial") @Builder.Default
    private List<HistorialEstados> historialComienzos = new ArrayList<>();

    @OneToMany(mappedBy = "estadoActual") @Builder.Default
    private List<HistorialEstados> historialActuales = new ArrayList<>();


}
