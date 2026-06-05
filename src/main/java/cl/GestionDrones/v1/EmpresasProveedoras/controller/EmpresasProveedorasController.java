package cl.GestionDrones.v1.EmpresasProveedoras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.AeronaveResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.BitacoraResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.CreateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.PilotoResponse;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.UpdateEmpresaRequest;
import cl.GestionDrones.v1.EmpresasProveedoras.model.EmpresaProveedora;
import cl.GestionDrones.v1.EmpresasProveedoras.service.EmpresasProveedorasService;


@RestController
@RequestMapping("/api/v1/empresasProveedoras")
public class EmpresasProveedorasController {

    @Autowired
    private EmpresasProveedorasService empresaProveedorasService;

    @GetMapping
    public ResponseEntity<List<EmpresaProveedora>> getAllEmpresas() {
        return new ResponseEntity<>(
                empresaProveedorasService.getEmpresasProveedoras(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpresaById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                empresaProveedorasService.getEmpresaProveedoraById(id), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmpresa(
            @Valid @RequestBody CreateEmpresaRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(
                empresaProveedorasService.saveEmpresaProveedora(request), HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear");
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmpresaRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return manejarErrores(result);
        }

        var empresaActualizada = empresaProveedorasService.updateEmpresa(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("mensaje", "Empresa proveedora actualizada correctamente");
        response.put("datos", empresaActualizada);

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> manejarErrores(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("errors", result.getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable Long id) {
        try {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", empresaProveedorasService.deleteEmpresaProveedora(id));
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar");
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getEmpresaPorEstado(@PathVariable String estado) {
        List<EmpresaProveedora> empresas = empresaProveedorasService.getEmpresaPorEstado(estado);
        if (empresas.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay empresas proveedoras con estado: " + estado);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<?> getEmpresaPorRut(@PathVariable String rut) {
        List<EmpresaProveedora> empresas = empresaProveedorasService.getEmpresaPorRut(rut);
        if (empresas.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay empresas proveedoras con RUT: " + rut);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalEmpresas() {
        return new ResponseEntity<>(
            empresaProveedorasService.totalEmpresasProveedoras(), HttpStatus.OK);
    }

    @GetMapping("/aeronaves/seguros-por-vencer")
    public ResponseEntity<?> getAeronavesConSeguroPorVencer() {
        List<AeronaveResponse> aeronaves = empresaProveedorasService.obtenerSegurosPorVencer();
        if (aeronaves.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay aeronaves con seguros que venzan en los próximos 30 días");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(aeronaves);
    }

    @GetMapping("/pilotos/certificados-por-vencer")
    public ResponseEntity<?> getPilotosConCertificadoPorVencer() {
        List<PilotoResponse> pilotos = empresaProveedorasService.obtenerCertificadosPorVencer();
        if (pilotos.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay pilotos con certificados que venzan en los próximos 30 días");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(pilotos);
    }

}
