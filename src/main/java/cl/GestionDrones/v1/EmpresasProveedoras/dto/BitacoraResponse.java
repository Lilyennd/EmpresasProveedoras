package cl.GestionDrones.v1.EmpresasProveedoras.dto;

import java.time.LocalDateTime;

public record BitacoraResponse(
        Long id,
        Long idPlanVuelo,
        Integer duracionRealMinutos,
        String estadoFinal,
        String observaciones,
        String firmaDigital,
        LocalDateTime fechaCierre
) {}
