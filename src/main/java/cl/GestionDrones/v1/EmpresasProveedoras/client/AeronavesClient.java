package cl.GestionDrones.v1.EmpresasProveedoras.client;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.AeronaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AeronavesClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_AERONAVES =
            "http://localhost:8082/api/v1/aeronaves";

    public List<AeronaveResponse> obtenerAeronavesDisponibles() {

        return restTemplate.exchange(
                URL_AERONAVES + "/estado/disponible",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AeronaveResponse>>() {}
        ).getBody();
    }
}