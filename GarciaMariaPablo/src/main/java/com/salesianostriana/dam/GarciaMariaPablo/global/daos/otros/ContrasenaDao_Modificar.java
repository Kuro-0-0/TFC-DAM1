package com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class ContrasenaDao_Modificar {

    private String actual,nueva,confirmar;

}
