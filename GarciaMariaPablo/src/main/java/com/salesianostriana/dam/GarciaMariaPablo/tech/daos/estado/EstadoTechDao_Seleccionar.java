package com.salesianostriana.dam.GarciaMariaPablo.tech.daos.estado;

import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EstadoTechDao_Seleccionar {
    private String nombre;
    private String valor;

    public static EstadoTechDao_Seleccionar crearDao(Estado estado) {
        return new EstadoTechDao_Seleccionar(estado.getNombre(), estado.getValor());
    }
}
