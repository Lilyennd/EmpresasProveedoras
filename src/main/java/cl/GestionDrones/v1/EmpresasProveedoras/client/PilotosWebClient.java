package cl.GestionDrones.v1.EmpresasProveedoras.client;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.PilotoResponse;

@Service
public class PilotosWebClient {

    private final WebClient webClient;

    public PilotosWebClient(@Qualifier("pilotosWC") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PilotoResponse> obtenerCertificadosPorVencer() {
        try {
            List<PilotoResponse> todos = webClient.get()
                    .uri("")
                    .retrieve()
                    .bodyToFlux(PilotoResponse.class)
                    .collectList()
                    .block();

            if (todos == null) return Collections.emptyList();

            LocalDate limite = LocalDate.now().plusDays(30);
            return todos.stream()
                    .filter(p -> p.fechaVencimientoCertificacion() != null
                            && !p.fechaVencimientoCertificacion().isAfter(limite))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Error al obtener pilotos: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}