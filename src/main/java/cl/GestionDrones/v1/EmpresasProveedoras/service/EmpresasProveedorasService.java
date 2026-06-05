package cl.GestionDrones.v1.EmpresasProveedoras.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import cl.GestionDrones.v1.EmpresasProveedoras.client.AeronavesWebClient;
import cl.GestionDrones.v1.EmpresasProveedoras.client.BitacorasWebClient;
import cl.GestionDrones.v1.EmpresasProveedoras.client.NotificacionesWebClient;
import cl.GestionDrones.v1.EmpresasProveedoras.client.PilotosWebClient;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.AeronaveResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.BitacoraResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.CreateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.PilotoResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.UpdateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.EmpresasProveedoras.mapper.EmpresasProveedorasMapper;
import cl.GestionDrones.v1.EmpresasProveedoras.model.EmpresaProveedora;
import cl.GestionDrones.v1.EmpresasProveedoras.repository.EmpresasProveedorasRepository;

import org.springframework.stereotype.Service;


@Service
public class EmpresasProveedorasService {

    @Autowired
    private EmpresasProveedorasRepository empresasProveedorasRepository;

    @Autowired
    private AeronavesWebClient aeronavesWebClient;

    @Autowired
    private PilotosWebClient pilotosWebClient;

    @Autowired
    private NotificacionesWebClient notificacionesWebClient;

    @Autowired
    private BitacorasWebClient bitacorasWebClient;

    public List<EmpresaProveedora> getEmpresasProveedoras() {
        return empresasProveedorasRepository.findAll();
    }

    public EmpresaProveedora saveEmpresaProveedora(CreateEmpresaRequest request) {
        return empresasProveedorasRepository.save(
            EmpresasProveedorasMapper.toEmpresa(request));
    }

    public EmpresaProveedora getEmpresaProveedoraById(Long id) {
        return empresasProveedorasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la empresa proveedora con el ID: " + id));
    }

    public EmpresaProveedora updateEmpresaProveedora(UpdateEmpresaRequest request) {
        EmpresaProveedora empresaExistente = empresasProveedorasRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se puede actualizar. No existe la empresa proveedora con el ID: " + request.id()));

        EmpresasProveedorasMapper.updateEntityFromDto(request, empresaExistente);
        return empresasProveedorasRepository.save(empresaExistente);
    }

    public String deleteEmpresaProveedora(Long id) {
        if (!empresasProveedorasRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "No se puede eliminar. No existe la empresa proveedora con el ID: " + id);
        }
        empresasProveedorasRepository.deleteById(id);
        return "Empresa proveedora eliminada";
    }

    public List<EmpresaProveedora> getEmpresaPorEstado(String estado) {
        return empresasProveedorasRepository.findByEstado(estado);
    }

    public List<EmpresaProveedora> getEmpresaPorRut(String rut) {
        return empresasProveedorasRepository.findByRut(rut);
    }

    public int totalEmpresasProveedoras() {
        return empresasProveedorasRepository.findAll().size();
    }

    // Seguros aeronaves 
    public List<AeronaveResponse> obtenerSegurosPorVencer() {
        List<AeronaveResponse> aeronaves = aeronavesWebClient.obtenerSegurosPorVencer();

        aeronaves.forEach(a -> notificacionesWebClient.enviarAlerta(
            "El seguro de la aeronave patente " + a.patente()
            + " vence el " + a.fechaVencimientoSeguro(),
            "SEGURO_POR_VENCER"
        ));

        return aeronaves;
    }

    // Certificados pilotos
    public List<PilotoResponse> obtenerCertificadosPorVencer() {
        List<PilotoResponse> pilotos = pilotosWebClient.obtenerCertificadosPorVencer();

        pilotos.forEach(p -> notificacionesWebClient.enviarAlerta(
            "El certificado DGAC del piloto " + p.nombres() + " " + p.apellidos()
            + " (RUN: " + p.run() + ") vence el " + p.fechaVencimientoCertificacion(),
            "CERTIFICADO_POR_VENCER"
        ));

        return pilotos;
    }

    
    public List<BitacoraResponse> obtenerBitacoras() {
        return bitacorasWebClient.obtenerTodasLasBitacoras();
    }
}