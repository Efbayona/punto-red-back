package com.project.punto_red.common.object;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Clase que representa los criterios de búsqueda para realizar consultas.
 */
public class SearchByCriteria {

    private final List<HashMap<String, String>> filters;
    private final Optional<String> orderBy;
    private final Optional<String> orderType;
    private final Optional<Integer> limit;
    private final Optional<Integer> offset;

    /**
     * Constructor de la clase SearchByCriteria.
     *
     * @param filters   La lista de filtros para aplicar en la consulta.
     * @param orderBy   El campo por el cual ordenar los resultados.
     * @param orderType El tipo de ordenamiento de los resultados.
     * @param limit     El límite de resultados a devolver.
     * @param offset    El desplazamiento de los resultados.
     */
    public SearchByCriteria(
            List<HashMap<String, String>> filters,
            Optional<String> orderBy,
            Optional<String> orderType,
            Optional<Integer> limit,
            Optional<Integer> offset
    ) {
        this.filters = filters;
        this.orderBy = orderBy;
        this.orderType = orderType;
        this.limit = limit;
        this.offset = offset;
    }

    /**
     * Obtiene la lista de filtros para aplicar en la consulta.
     *
     * @return La lista de filtros.
     */
    public List<HashMap<String, String>> filters() {
        return filters;
    }

    /**
     * Obtiene el campo por el cual ordenar los resultados.
     *
     * @return El campo de ordenamiento.
     */
    public Optional<String> orderBy() {
        return orderBy;
    }

    /**
     * Obtiene el tipo de ordenamiento de los resultados.
     *
     * @return El tipo de ordenamiento.
     */
    public Optional<String> orderType() {
        return orderType;
    }

    /**
     * Obtiene el límite de resultados a devolver.
     *
     * @return El límite de resultados.
     */
    public Optional<Integer> limit() {
        return limit;
    }

    /**
     * Obtiene el desplazamiento de los resultados.
     *
     * @return El desplazamiento de los resultados.
     */
    public Optional<Integer> offset() {
        return offset;
    }

}
