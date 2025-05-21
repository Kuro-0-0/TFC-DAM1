package com.salesianostriana.dam.GarciaMariaPablo.user.daos.incidencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor  @NoArgsConstructor
public class IncidenciaUserDao_Crear {

    private String titulo, descripcion, ubicacion;
}
