package com.salesianostriana.dam.GarciaMariaPablo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descripcion;
    private String ubicacion;
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "reportante_id")
    private Usuario reportante;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    /* HELPERS */

    /**
     * Establecemos el estado recibido en el atributo de la incidencia y añadimos la incidencia al listado que se
     * encuentra en el estado.
     * @param estado Estado que se añadira a la incidencia.
     */
    public void anadirEstado(Estado estado) {
        this.estado = estado;
        estado.getIncidencias().add(this);
    }

    /**
     * Eliminamos esta incidencia del listado ubicado dentro del estado actual de la incidencia y posteriormente
     * removemos el estado de la incidencia colocandolo a null
     */
    public void eliminarEstado() {
        estado.getIncidencias().remove(this);
        estado = null;
    }

    /**
     * En funcion del rol del usuario indicado como parametro, se establecere como uno u otro de los atributos
     * disponibles para usuario en el objeto.
     *
     * @param usuario Usuario que se añadira a la incidencia.
     */
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

    /**
     * Usado para eliminar de los usuarios asociados asi como de la propia incidencia toda la informacion que los
     * relacione, usado unicamente cuando se prevee eliminar la incidencia al completo
     */
    public void eliminarUsuarios() {
        tecnico.getIncidenciasGestionadas().remove(this);
        reportante.getIncidenciasGestionadas().remove(this);
        tecnico = null;
        reportante = null;
    }
}
