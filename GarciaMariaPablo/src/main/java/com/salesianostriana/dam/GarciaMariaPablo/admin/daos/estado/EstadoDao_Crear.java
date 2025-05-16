package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.estado;


import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.Estado;
import com.salesianostriana.dam.GarciaMariaPablo.global.modelos.utilidades.TipoEstados;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.Normalizer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDao_Crear {

    private String nombre;
    private boolean activo;
    @Builder.Default
    private String colorTexto = "#ffffff";
    @Builder.Default
    private String colorFondo = "#000000";
    private String tipo;

    public static EstadoDao_Crear crearDao(Estado estado) {
        return EstadoDao_Crear.builder()
                .nombre(estado.getNombre())
                .activo(estado.isActivo())
                .colorFondo(estado.getColorFondo())
                .colorTexto(estado.getColorTexto())
                .tipo(estado.getTipo().name())
                .build();
    }

    public Estado revertirDao() {
        String valor;

        valor = this.nombre.toLowerCase();
        valor = valor.replace(" ", "-");
        valor = Normalizer.normalize(valor, Normalizer.Form.NFD);
        valor = valor.replaceAll("[^\\p{ASCII}]", "");

        return Estado.builder()
                .nombre(this.nombre)
                .valor(valor)
                .colorFondo(this.colorFondo)
                .colorTexto(this.colorTexto)
                .activo(this.activo)
                .colorFondo(this.colorFondo)
                .colorTexto(this.colorTexto)
                .tipo(TipoEstados.valueOf(this.tipo))
                .build();
    }

}
