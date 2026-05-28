package cl.GestionDrones.v1.EmpresasProveedoras.mapper;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.CreateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.UpdateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.model.EmpresaProveedora;

public class EmpresasProveedorasMapper {

    public static EmpresaProveedora toEmpresa(CreateEmpresaRequest request) {
        EmpresaProveedora empresa = new EmpresaProveedora();

        empresa.setRut(request.rut());
        empresa.setRazonSocial(request.razonSocial());
        empresa.setCantidadAeronaves(request.cantidadAeronaves());
        empresa.setCantidadPilotos(request.cantidadPilotos());
        empresa.setContactoEmail(request.contactoEmail());
        empresa.setEstado(request.estado());

        return empresa;
    }

    public static void updateEntityFromDto(
            UpdateEmpresaRequest dto,
            EmpresaProveedora entity) {

        entity.setRut(dto.rut());
        entity.setRazonSocial(dto.razonSocial());
        entity.setCantidadAeronaves(dto.cantidadAeronaves());
        entity.setCantidadPilotos(dto.cantidadPilotos());
        entity.setContactoEmail(dto.contactoEmail());
        entity.setEstado(dto.estado());
    }
}