package com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor
@NoArgsConstructor @Data
public class EstadisticasDao {

    private int resueltas;
    private long tiempoPromedio;

}
