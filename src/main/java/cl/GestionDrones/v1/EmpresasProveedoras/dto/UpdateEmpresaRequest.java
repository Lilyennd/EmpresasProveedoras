package cl.GestionDrones.v1.EmpresasProveedoras.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateEmpresaRequest(

    @NotNull(message = "El ID de la empresa proveedora es obligatorio para actualizar")
    Long id,

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 15, message = "El RUT no puede superar los 15 caracteres")
    String rut,

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 150, message = "La razón social no puede superar los 150 caracteres")
    String razonSocial,

    @NotNull(message = "La cantidad de aeronaves es obligatoria")
    Integer cantidadAeronaves,

    @NotNull(message = "La cantidad de pilotos es obligatoria")
    Integer cantidadPilotos,

    @NotBlank(message = "El correo de contacto es obligatorio")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    String contactoEmail,

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    String estado

) {
}