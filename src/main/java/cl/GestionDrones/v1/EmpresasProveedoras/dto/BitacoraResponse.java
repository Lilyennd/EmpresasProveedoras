package cl.GestionDrones.v1.EmpresasProveedoras.dto;

import java.time.LocalDateTime;

public record BitacoraResponse(
        Long id,
        String accion,
        String descripcion,
        LocalDateTime fecha
) {
}