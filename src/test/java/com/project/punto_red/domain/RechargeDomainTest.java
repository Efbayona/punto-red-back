package com.project.punto_red.domain;

import com.project.punto_red.recharge.service.domain.RechargeDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RechargeDomainTest {

    @Test
    @DisplayName("Deberia fallar cuando el valor de la transaccion es invalido")
    void shouldFail_whenTransactionValueIsInvalid() {
        assertAll(
                () -> assertThatThrownBy(() -> RechargeDomain.create("9773", "3123456789", "Movistar", BigDecimal.valueOf(999)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("at least 1000"), // Deberia fallar cuando el valor esta por debajo del minimo
                () -> assertThatThrownBy(() -> RechargeDomain.create("9773", "3123456789", "Movistar", BigDecimal.valueOf(100001)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("must not exceed 100000") // Deberia fallar cuando el valor esta por encima del maximo
        );
    }

    @Test
    @DisplayName("Deberia pasar cuando el valor  valido")
    void shouldPass_whenTransactionValueIsValid() {
        RechargeDomain domain = RechargeDomain.create("SUP123", "3123456789", "Movistar", BigDecimal.valueOf(5000));
        assertThat(domain.getSupplierId()).isEqualTo("SUP123");
        assertThat(domain.getCellPhone()).isEqualTo("3123456789");
        assertThat(domain.getValue()).isEqualTo(BigDecimal.valueOf(5000));
    }

    @Test
    @DisplayName("Deberia fallar cuando el numero de telefono no es un numero permitido")
    void shouldFail_whenPhoneNumberIsNotValid() {
        assertAll(
                () -> assertThatThrownBy(() -> RechargeDomain.create("SUP123", "3123456", "Movistar", BigDecimal.valueOf(5000)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("must have exactly 10 digits"),
                () -> assertThatThrownBy(() -> RechargeDomain.create("SUP123", "4123456789", "Movistar",BigDecimal.valueOf(5000)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("must start with '3'"),
                () -> assertThatThrownBy(() -> RechargeDomain.create("SUP123", "31A3456789", "Movistar", BigDecimal.valueOf(5000)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("must contain only digits")
        );
    }

}