package cl.GestionDrones.v1.EmpresasProveedoras.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.BitacoraResponse;

@Component
public class BitacorasWebClient {

    private final WebClient webClient;

    public BitacorasWebClient(@Qualifier("bitacorasWC") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BitacoraResponse> obtenerTodasLasBitacoras() {
        try {
            return webClient.get()
                    .uri("")
                    .retrieve()
                    .bodyToFlux(BitacoraResponse.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.out.println("Error al obtener bitácoras: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}