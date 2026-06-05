package cl.GestionDrones.v1.EmpresasProveedoras.client;

import java.util.Collections;
import java.util.List;

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
            List<PilotoResponse> filtrados = webClient.get()
                    .uri("/por-vencer")
                    .retrieve()
                    .bodyToFlux(PilotoResponse.class)
                    .collectList()
                    .block();

            return filtrados != null ? filtrados : Collections.emptyList();

        } catch (Exception e) {
            System.out.println("Error al obtener pilotos: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}