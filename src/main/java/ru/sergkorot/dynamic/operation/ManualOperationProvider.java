package ru.sergkorot.dynamic.operation;

import ru.sergkorot.dynamic.model.BaseSearchParam;

/**
 * @author Sergey Korotaev
 * Interface for user's implementations with custom operations for requests into databse
 * @param <T> - interface for data access (for example, Specification)
 */
public interface ManualOperationProvider<T> {

    /**
     * Method which marks the implementation for which of field will be used this implementation
     * @return filed's name
     */
    String fieldName();

    /**
     * Method for building custom implementation for specified field
     * @param searchParam - model for base search request
     * @return - interface for data access (for example, Specification)
     */
    T buildOperation(BaseSearchParam searchParam);
}
