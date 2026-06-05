package cl.GestionDrones.v1.EmpresasProveedoras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean(name = "aeronavesWC")
    public WebClient aeronavesWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8082/api/v1/aeronaves").build();
    }

    @Bean(name = "pilotosWC")
    public WebClient pilotosWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1/pilotos").build();
    }

    @Bean(name = "notificacionesWC")
    public WebClient notificacionesWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8088/api/v1/notificaciones").build();
    }

    @Bean(name = "bitacorasWC")
    public WebClient bitacorasWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8085/api/v1/bitacoras").build();
    }
}