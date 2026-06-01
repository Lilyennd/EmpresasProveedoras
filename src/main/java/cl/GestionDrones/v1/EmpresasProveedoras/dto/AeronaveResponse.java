package cl.GestionDrones.v1.EmpresasProveedoras.dto;

import java.time.LocalDate;

public record AeronaveResponse(
        Long id,
        String patente,
        String numeroSerie,
        String marca,
        String modelo,
        String estado,
        LocalDate fechaVencimientoSeguro
) {
}