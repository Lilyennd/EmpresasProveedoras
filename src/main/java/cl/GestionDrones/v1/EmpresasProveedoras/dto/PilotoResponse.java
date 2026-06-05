package cl.GestionDrones.v1.EmpresasProveedoras.dto;

import java.time.LocalDate;

public record PilotoResponse(
        Long id,
        String run,
        String nombres,
        String apellidos,
        String telefono,
        String numeroCertificadoDgac,
        LocalDate fechaVencimientoCertificacion
) {}