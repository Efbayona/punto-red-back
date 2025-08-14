package com.project.punto_red.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeResponse;
import com.project.punto_red.recharge.entity.Recharge;
import com.project.punto_red.recharge.repository.RechargeRepository;
import com.project.punto_red.recharge.service.domain.RechargeDomain;
import com.project.punto_red.recharge.service.impl.RechargeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeServiceImplTest {

    @Mock
    private TokenStorage tokenStorage;
    @Mock
    private RechargeRepository rechargeRepository;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<String> httpResponse;
    @InjectMocks
    private RechargeServiceImpl rechargeService;

    @BeforeEach
    void setUp() throws Exception {
        // Simulamos con ReflectionTestUtils la inyeccion de dependencias para la URL
        ReflectionTestUtils.setField(rechargeService, "baseUrl", "http://localhost:8080");
    }

    @Test
    @DisplayName("Debe retornar RechargeResponse cuando la API responde con código 200")
    void shouldReturnRechargeResponse_whenApiReturns200() throws Exception {
        // Arrange: Preparamos datos y mocks para simular un escenario exitoso de recarga.
        UUID transactionId = UUID.randomUUID();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "OK");
        jsonResponse.addProperty("transactionalID", transactionId.toString());
        jsonResponse.addProperty("cellPhone", "3123456789");
        jsonResponse.addProperty("value", 5000.0);

        // Simulamos las respuestas esperadas de la API y el repositorio
        when(tokenStorage.getToken()).thenReturn("Bearer fake-token");
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(new Gson().toJson(jsonResponse));
        when(httpClient.send(
                ArgumentMatchers.any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        Recharge rechargeEntity = Recharge.create("OK", transactionId, "3123456789", 5000.0, "SUP123", "OP1");
        when(rechargeRepository.save(ArgumentMatchers.any()))
                .thenReturn(rechargeEntity);

        // Creamos el request de recarga
        RechargeDomain request = RechargeDomain.create("SUP123", "3123456789", "Movistar", BigDecimal.valueOf(5000));

        // Act: Ejecutamos el método bajo prueba
        RechargeResponse response = rechargeService.recharge(request);

        // Assert: Validamos que la respuesta contenga los valores esperados
        assertThat(response).isNotNull();
        assertThat(response.getCellPhone()).isEqualTo("3123456789");
        assertThat(response.getValue()).isEqualTo(5000.0);

        // Verificamos que se llamaron los métodos correctos en las dependencias
        verify(tokenStorage).getToken();
        verify(httpClient).send(
                ArgumentMatchers.<HttpRequest>any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        );
        verify(rechargeRepository).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Debe retornar una página de RechargeHistoryResponse desde el repositorio")
    void getRechargeRequests_shouldReturnPageFromRepository() {
        // Arrange: Configuramos la paginación y el contenido de prueba
        int pageNumber = 2;
        Pageable expectedPageable = PageRequest.of(pageNumber, 10);

        List<RechargeHistoryResponse> mockData = List.of(
                new RechargeHistoryResponse(
                        "Recarga exitosa",
                        "3001234567",
                        1500.0,
                        UUID.randomUUID(),
                        "supplier1",
                        "operator1",
                        LocalDateTime.now()
                ),
                new RechargeHistoryResponse(
                        "Recarga fallida",
                        "3009876543",
                        2500.0,
                        UUID.randomUUID(),
                        "supplier2",
                        "operator2",
                        LocalDateTime.now()
                )
        );
        Page<RechargeHistoryResponse> mockPage = new PageImpl<>(mockData);

        // Simulamos la respuesta del repositorio
        when(rechargeRepository.getRechargeRequests(expectedPageable)).thenReturn(mockPage);

        // Act: Ejecutamos el método bajo prueba
        Page<RechargeHistoryResponse> result = rechargeService.getRechargeRequests(pageNumber);

        // Assert: Verificamos que la página y los datos sean correctos
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(mockData, result.getContent());

        // Verificamos que el repositorio fue llamado con el pageable esperado
        verify(rechargeRepository).getRechargeRequests(expectedPageable);
    }



}