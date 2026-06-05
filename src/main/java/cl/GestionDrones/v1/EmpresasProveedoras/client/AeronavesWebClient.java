package cl.GestionDrones.v1.EmpresasProveedoras.client;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.AeronaveResponse;

@Service
public class AeronavesWebClient {

    private final WebClient webClient;

    public AeronavesWebClient(@Qualifier("aeronavesWC") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<AeronaveResponse> obtenerSegurosPorVencer() {
        try {
            List<AeronaveResponse> todas = webClient.get()
                    .uri("")
                    .retrieve()
                    .bodyToFlux(AeronaveResponse.class)
                    .collectList()
                    .block();

            if (todas == null) return Collections.emptyList();

            LocalDate limite = LocalDate.now().plusDays(30);
            return todas.stream()
                    .filter(a -> a.fechaVencimientoSeguro() != null
                            && !a.fechaVencimientoSeguro().isAfter(limite))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Error al obtener aeronaves: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}