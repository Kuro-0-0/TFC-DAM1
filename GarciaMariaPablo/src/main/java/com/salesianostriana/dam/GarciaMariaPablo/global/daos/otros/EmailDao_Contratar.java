package com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  @AllArgsConstructor
@NoArgsConstructor @Builder
public class EmailDao_Contratar {

    private String planSeleccionado;
    private String email;
}
