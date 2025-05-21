package com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EmailDao_Contactar {

    public String nombre;
    public String email;
    public String asunto;
    public String mensaje;

}
