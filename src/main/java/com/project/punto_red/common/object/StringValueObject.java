package com.project.punto_red.common.object;

import java.util.Objects;

/**
 * Clase abstracta que representa un objeto de valor de tipo String.
 */
public abstract class StringValueObject {
    private String value;

    /**
     * Constructor de la clase StringValueObject.
     *
     * @param value El valor del objeto de valor.
     */
    public StringValueObject(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor del objeto de valor.
     *
     * @return El valor del objeto de valor.
     */
    public String value() {
        return value;
    }

    /**
     * Devuelve una representación en cadena del objeto de valor.
     *
     * @return La representación en cadena del objeto de valor.
     */
    @Override
    public String toString() {
        return this.value();
    }

    /**
     * Compara si el objeto de valor es igual a otro objeto.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringValueObject)) {
            return false;
        }
        StringValueObject that = (StringValueObject) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Devuelve el valor hash del objeto de valor.
     *
     * @return El valor hash del objeto de valor.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
