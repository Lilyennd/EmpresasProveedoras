package cl.GestionDrones.v1.EmpresasProveedoras.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import cl.GestionDrones.v1.EmpresasProveedoras.dto.AeronaveResponse;

@Service
public class AeronavesWebClient {

    @Autowired
    private WebClient webClient;

    private final String URL_AERONAVES =
            "http://localhost:8082/api/v1/aeronaves/seguros/por-vencer";

    public List<AeronaveResponse> obtenerSegurosPorVencer() {

        return webClient
                .get()
                .uri(URL_AERONAVES)
                .retrieve()
                .bodyToFlux(AeronaveResponse.class)
                .collectList()
                .block();
    }
}