package com.salesianostriana.dam.GarciaMariaPablo.admin.daos.otros;

import com.salesianostriana.dam.GarciaMariaPablo.admin.daos.usuario.external.UsuarioDao_Estadisticas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class EstadisticasDao {

    private List<UsuarioDao_Estadisticas> topResueltas;
    private List<UsuarioDao_Estadisticas> topRapidos;
    private List<UsuarioDao_Estadisticas> topReportantes;
}
