package com.salesianostriana.dam.GarciaMariaPablo.old.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String titulo;

    @Column(length = 500)
    private String descripcion;
    private String ubicacion;
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "reportante_id")
    private Usuario reportante;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    @ToString.Exclude
    private Estado estado;

    @OneToMany(mappedBy = "incidencia")
    private List<HistorialEstados> historialEstados;

    /* HELPERS */

    public void anadirEstado(Estado estado) {
        this.estado = estado;
        estado.getIncidencias().add(this);
    }


    public void eliminarEstado() {
        estado.getIncidencias().remove(this);
        estado = null;
    }


    public void anadirUsuario(Usuario usuario) {
        switch (usuario.getRol()) {
            case reportante -> {
                this.reportante = usuario;
                usuario.getIncidenciasReportadas().add(this);
            }
            case tecnico -> {
                this.tecnico = usuario;
                usuario.getIncidenciasGestionadas().add(this);
            }
            default -> throw new IllegalArgumentException("No se puede asignar incidencias a usuarios de rol :"+usuario.getRol() );
        }
    }

    public void eliminarUsuarios() {
        tecnico.getIncidenciasGestionadas().remove(this);
        reportante.getIncidenciasGestionadas().remove(this);
        tecnico = null;
        reportante = null;
    }

    public String getNombreEstado() {
        return estado.getNombre();
    }

    public void anadirRegistroHistorialEstados(HistorialEstados nuevaEntrada) {
        if (historialEstados == null) {
            historialEstados = new ArrayList<>();
        }
        this.historialEstados.add(nuevaEntrada);
        nuevaEntrada.setIncidencia(this);
    }

}
