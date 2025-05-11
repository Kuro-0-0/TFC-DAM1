package com.salesianostriana.dam.GarciaMariaPablo.daos.otros;

import com.salesianostriana.dam.GarciaMariaPablo.modelos.utilidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDao_ListarUsuarios {

    private String name;
    private boolean selected;

    public static RolDao_ListarUsuarios crearDao(Rol rol, boolean status) {
        return RolDao_ListarUsuarios.builder()
                .name(rol.name())
                .selected(status)
                .build();
    }

}
