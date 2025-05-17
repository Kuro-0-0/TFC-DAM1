package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.TipoEstados;
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

    private TipoEstados tipo;

    @OneToMany(mappedBy = "estado")
    private List<Incidencia> incidencias;

    @OneToMany(mappedBy = "estadoInicial")
    private List<HistorialEstados> historialComienzos;

    @OneToMany(mappedBy = "estadoActual")
    private List<HistorialEstados> historialActuales;


}
