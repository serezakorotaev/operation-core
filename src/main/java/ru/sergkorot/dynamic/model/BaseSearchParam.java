package ru.sergkorot.dynamic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergey Korotaev
 * Base class for search requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseSearchParam {
    private String name;
    private Object value;
    private String operation;
}
