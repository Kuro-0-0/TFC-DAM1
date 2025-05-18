package com.salesianostriana.dam.GarciaMariaPablo.global.modelos;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.RolesUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario
        implements UserDetails, Comparable<Usuario> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RolesUsuario rol;

    private String nombre;
    private String apellidos;

    @Builder.Default
    private boolean editable = true;

    @OneToMany(mappedBy = "reportante")
    @ToString.Exclude @Builder.Default
    private List<Incidencia> incidenciasReportadas = new ArrayList<>();

    @OneToMany(mappedBy = "tecnico")
    @ToString.Exclude @Builder.Default
    private List<Incidencia> incidenciasGestionadas = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+rol.name()));
    }

    @Override
    public int compareTo(Usuario u) {
        String thisNombreCompleto = this.getNombre() + " " + this.getApellidos();
        String uNombreCompleto = u.getNombre() + " " + u.getApellidos();
        return thisNombreCompleto.toLowerCase().compareTo(uNombreCompleto.toLowerCase());
    }

    /* HELPERS */

    public void transferirIncidencias(Usuario userDefault) {
        switch (userDefault.getRol()) {
            case TECH -> this.incidenciasGestionadas.forEach( incidencia ->  {incidencia.setTecnico(userDefault);userDefault.getIncidenciasGestionadas().add(incidencia);});
            case USER -> this.incidenciasReportadas.forEach( incidencia -> {incidencia.setReportante(userDefault);userDefault.getIncidenciasReportadas().add(incidencia);});
        }
    }

}
