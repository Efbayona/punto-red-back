package com.project.punto_red.impl;

import com.project.punto_red.common.exception.service.ResourceNotFoundException;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.operators.service.impl.OperatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    @BeforeEach
    void setUp() {
        // Simulamos con ReflectionTestUtils la inyeccion de dependencias para la URL
        ReflectionTestUtils.setField(operatorService, "baseUrl", "http://localhost:8080");
    }

    @Test
    @DisplayName("Debe retornar operadores cuando la API responde con código 200")
    void shouldReturnOperators_whenApiReturns200() throws Exception {
        // Arrange - Configuramos el escenario de prueba
        // Respuesta simulada de la API
        String expectedResponse = "[{\"name\":\"Operator1\"}]";
        // Simulamos que el token de autenticación es retornado correctamente
        when(tokenStorage.getToken()).thenReturn("Bearer fake-token");
        // Simulamos que la API responde con código 200 (éxito)
        when(httpResponse.statusCode()).thenReturn(200);
        // Simulamos que el cuerpo de la respuesta contiene la lista de operadores
        when(httpResponse.body()).thenReturn(expectedResponse);
        // Simulamos que el HttpClient retorna la respuesta simulada
        when(httpClient.send(
                ArgumentMatchers.<HttpRequest>any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        // Act - Ejecutamos el método que queremos probar
        String result = operatorService.getOperators();

        // Assert - Verificamos resultados
        // Validamos que la respuesta devuelta es la esperada
        assertThat(result).isEqualTo(expectedResponse);
        // Verificamos que se solicitó el token una vez
        verify(tokenStorage).getToken();
        // Verificamos que se hizo la llamada HTTP
        verify(httpClient).send(
                ArgumentMatchers.<HttpRequest>any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        );
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando la API no responde con código 200")
    void shouldThrowResourceNotFoundException_whenApiFails() throws Exception {
        // Arrange - Configuramos el escenario de prueba
        // Simulamos que el token de autenticación es retornado correctamente
        when(tokenStorage.getToken()).thenReturn("Bearer fake-token");
        // Simulamos que la API responde con código distinto a 200 (error)
        when(httpResponse.statusCode()).thenReturn(500);
        // Simulamos que el HttpClient retorna la respuesta fallida
        when(httpClient.send(
                ArgumentMatchers.<HttpRequest>any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        // Act & Assert - Ejecutamos y verificamos que se lance la excepción esperada
        assertThrows(ResourceNotFoundException.class, () -> operatorService.getOperators());
        // Verificamos que se solicitó el token una vez
        verify(tokenStorage).getToken();
        // Verificamos que se hizo la llamada HTTP
        verify(httpClient).send(
                ArgumentMatchers.<HttpRequest>any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        );
    }


}