package cl.GestionDrones.v1.EmpresasProveedoras.client;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.EmpresasProveedoras.dto.NotificacionRequest;

@Service
public class NotificacionesWebClient {

    private final WebClient webClient;

    public NotificacionesWebClient(@Qualifier("notificacionesWC") WebClient webClient) {
        this.webClient = webClient;
    }

    public void enviarAlerta(String mensaje, String tipo) {
        try {
            NotificacionRequest notificacion = new NotificacionRequest(
                "EMPRESA_PROVEEDORA",
                1L,
                tipo,
                mensaje,
                "PENDIENTE",
                LocalDateTime.now()
            );

            webClient.post()
                    .uri("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(notificacion)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .onErrorComplete()
                    .block();

        } catch (Exception e) {
            System.out.println("Error al enviar notificación: " + e.getMessage());
        }
    }
}
