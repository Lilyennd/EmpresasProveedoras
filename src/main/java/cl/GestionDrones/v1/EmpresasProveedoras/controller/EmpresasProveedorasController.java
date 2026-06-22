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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Empresas Proveedoras", description = "Operaciones relacionadas con las empresas proveedoras de drones y pilotos")
@RestController
@RequestMapping("/api/v1/empresasProveedoras")
public class EmpresasProveedorasController {

    @Autowired
    private EmpresasProveedorasService empresaProveedorasService;

    @Operation(summary = "Obtener todas las empresas proveedoras", description = "Retorna una lista completa con todas las empresas proveedoras registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class)))
    })
    @GetMapping
    public ResponseEntity<List<EmpresaProveedora>> getAllEmpresas() {
        return new ResponseEntity<>(
                empresaProveedorasService.getEmpresasProveedoras(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar empresa proveedora por ID", description = "Busca y retorna los detalles de una empresa proveedora específica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa proveedora encontrada correctamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna empresa con el ID proporcionado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpresaById(
        @Parameter(description = "ID único de la empresa proveedora a consultar", required = true, example = "1")
        @PathVariable Long id
    ) {
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

    @Operation(summary = "Crear una nueva empresa proveedora", description = "Registra una nueva empresa proveedora en el sistema validando sus datos de entrada")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estructura JSON de la nueva empresa proveedora a registrar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CreateEmpresaRequest.class),
            examples = @ExampleObject(
                name = "Ejemplo de Nueva Empresa Proveedora",
                value = "{\n  \"rut\": \"77345678-K\",\n  \"razonSocial\": \"Proveedora de Sistemas Autónomos SpA\",\n  \"cantidadAeronaves\": 15,\n  \"cantidadPilotos\": 8,\n  \"contactoEmail\": \"logistica@sistemasautonomos.cl\",\n  \"estado\": \"ACTIVO\"\n}"
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error en los parámetros del payload", content = @Content)
    })
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

    @Operation(summary = "Actualizar una empresa proveedora", description = "Modifica los datos de una empresa proveedora existente basándose en su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estructura JSON con los nuevos campos de la empresa",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UpdateEmpresaRequest.class),
            examples = @ExampleObject(
                name = "Ejemplo de Actualización de Empresa Proveedora",
                value = "{\n  \"rut\": \"77345678-K\",\n  \"razonSocial\": \"Sistemas Autónomos y Drones Internacionales SpA\",\n  \"cantidadAeronaves\": 18,\n  \"cantidadPilotos\": 10,\n  \"contactoEmail\": \"operaciones@sistemasautonomos.cl\",\n  \"estado\": \"ACTIVO\"\n}"
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa proveedora actualizada correctamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o errores en el payload", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @Parameter(description = "ID de la empresa proveedora que se desea actualizar", required = true, example = "1")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa proveedora", description = "Elimina físicamente una empresa proveedora del inventario mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa eliminada de forma exitosa"),
        @ApiResponse(responseCode = "404", description = "No se pudo eliminar porque la empresa no fue encontrada", content = @Content)
    })
    public ResponseEntity<?> deleteEmpresa(
        @Parameter(description = "ID de la empresa proveedora a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
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

    @Operation(summary = "Buscar empresas proveedoras por estado", description = "Retorna una lista de empresas filtradas de acuerdo con su estado operacional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresas encontradas de manera correcta", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron empresas proveedoras con el estado consultado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getEmpresaPorEstado(
        @Parameter(description = "Estado actual de la empresa (ej. ACTIVO, INACTIVO)", required = true, example = "ACTIVO")
        @PathVariable String estado
    ) {
        List<EmpresaProveedora> empresas = empresaProveedorasService.getEmpresaPorEstado(estado);
        if (empresas.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay empresas proveedoras con estado: " + estado);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @Operation(summary = "Buscar empresas proveedoras por RUT", description = "Busca y retorna las empresas que coinciden con el número de RUT proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresas encontradas con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaProveedora.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron empresas proveedoras con el RUT especificado", content = @Content)
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<?> getEmpresaPorRut(
        @Parameter(description = "RUT completo de la empresa proveedora", required = true, example = "76123456-K")
        @PathVariable String rut
    ) {
        List<EmpresaProveedora> empresas = empresaProveedorasService.getEmpresaPorRut(rut);
        if (empresas.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "No hay empresas proveedoras con RUT: " + rut);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener total de empresas proveedoras", description = "Devuelve un conteo numérico absoluto de las empresas proveedoras registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteo obtenido con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    })
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalEmpresas() {
        return new ResponseEntity<>(
            empresaProveedorasService.totalEmpresasProveedoras(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener aeronaves con seguro por vencer", description = "Retorna un listado de aeronaves asociadas que tienen el seguro próximo a expirar en los siguientes 30 días")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros de aeronaves con seguro por vencer obtenidos con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = AeronaveResponse.class))),
        @ApiResponse(responseCode = "404", description = "No hay aeronaves con seguros que venzan en los próximos 30 días", content = @Content)
    })
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

    @Operation(summary = "Obtener pilotos con certificados por vencer", description = "Retorna un listado de pilotos asociados cuyos certificados DGAC venzan en los próximos 30 días")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros de pilotos con certificado por vencer obtenidos con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PilotoResponse.class))),
        @ApiResponse(responseCode = "404", description = "No hay pilotos con certificados que venzan en los próximos 30 días", content = @Content)
    })
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

    private ResponseEntity<Map<String, Object>> manejarErrores(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("errors", result.getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
