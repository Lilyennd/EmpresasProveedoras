package cl.GestionDrones.v1.EmpresasProveedoras.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.CreateEmpresaProveedoraRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.UpdateEmpresaProveedoraRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.EmpresasProveedoras.mapper.EmpresaProveedoraMapper;
import cl.GestionDrones.v1.EmpresasProveedoras.model.EmpresaProveedora;
import cl.GestionDrones.v1.EmpresasProveedoras.repository.EmpresaProveedoraRepository;

import org.springframework.stereotype.Service;

@Service
public class EmpresaProveedoraService {

    @Autowired
    private EmpresaProveedoraRepository EmpresasProveedorasRepository;

    public List<EmpresaProveedora> getEmpresasProveedoras() {
        return EmpresasProveedorasRepository.findAll();
    }

    public EmpresaProveedora saveEmpresaProveedora(CreateEmpresaProveedoraRequest request) {

        EmpresaProveedora nuevaEmpresa =
                EmpresaProveedoraMapper.toEmpresa(request);

        return EmpresasProveedorasRepository.save(nuevaEmpresa);
    }

    public EmpresaProveedora getEmpresaProveedoraById(Long id) {
        return EmpresasProveedorasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encontró la empresa proveedora con el ID: " + id));
    }

    public EmpresaProveedora updateEmpresaProveedora(
            UpdateEmpresaProveedoraRequest request) {

        EmpresaProveedora empresaExistente =
                EmpresasProveedorasRepository.findById(request.id())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No se puede actualizar. No existe la empresa proveedora con el ID: "
                                                + request.id()));

        EmpresaProveedoraMapper.updateEntityFromDto(
                request,
                empresaExistente
        );

        return EmpresasProveedorasRepository.save(empresaExistente);
    }

    public String deleteEmpresaProveedora(Long id) {

        if (!EmpresasProveedorasRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "No se puede eliminar. No existe la empresa proveedora con el ID: " + id);
        }

        EmpresasProveedorasRepository.deleteById(id);

        return "Empresa proveedora eliminada";
    }

    public List<EmpresaProveedora> getEmpresaPorEstado(String estado) {
        return EmpresasProveedorasRepository.findByEstado(estado);
    }

    public List<EmpresaProveedora> getEmpresaPorRut(String rut) {
        return EmpresasProveedorasRepository.findByRut(rut);
    }

    public int totalEmpresasProveedoras() {
        return EmpresasProveedorasRepository.findAll().size();
    }
}