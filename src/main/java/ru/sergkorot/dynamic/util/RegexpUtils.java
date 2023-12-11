package ru.sergkorot.dynamic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergey Korotaev
 * Util is used for transforming string by patterns
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexpUtils {

    /**
     * Regexp pattern to find all group with operation (lalal)(lala)
     */
    @SuppressWarnings({"S5857"})
    public static final Pattern OPERATION_GROUPING_PATTERN = Pattern.compile("\\([^()].+?\\)");

    /**
     * Regexp pattern to find string with searchParam structure (name.like=lalal)
     *
     * @see ru.sergkorot.dynamic.model.BaseSearchParam
     */
    public static final Pattern SEARCH_PARAM_PATTERN = Pattern.compile("\\w+\\.\\w+=(?:\"[^\"]*\"|\\w+)");
    /**
     * Regexp pattern to remove all spaces
     */
    public static final String REGEXP_DELETE_ALL_WHITESPACES = "\\s+";

    /**
     * Regexp pattern corresponding to the example: "-id,name,-hello".
     */
    @SuppressWarnings({"S5869", "S5998"})
    public static final String REGEXP_VALIDATION_SORT_BY_VALUES = "^(-?[A-z-_]*,)*(-?[A-z-_]+)$";

    /**
     * transforming string by pattern to list strings with strings for further paging
     *
     * @param fieldsNames - string with fields for sorting
     * @return - list with fields for sorting
     */
    public static List<String> transformToArrayFieldsNames(final String fieldsNames) {
        final List<String> fieldsNamesWithoutWhiteSpace = Arrays.asList(fieldsNames.replaceAll(REGEXP_DELETE_ALL_WHITESPACES, "").split(","));

        fieldsNamesWithoutWhiteSpace.forEach(field -> {
            final boolean isValidValue = field.matches(REGEXP_VALIDATION_SORT_BY_VALUES);
            if (!isValidValue) {
                throw new IllegalArgumentException(String.format("Fields names: [%s] - don't match with regexp pattern", fieldsNamesWithoutWhiteSpace));
            }
        });

        return fieldsNamesWithoutWhiteSpace;
    }

    /**
     * Transforming query by pattern to list with query groups
     *
     * @param query - query for request to database
     * @return List of String with request groups
     */
    public static List<String> transformToArrayOperationGroups(final String query) {
        return transformToArrayByPattern(query, OPERATION_GROUPING_PATTERN);
    }

    /**
     * Transforming query by pattern to list of string with searchParam structure
     *
     * @param query String with request group
     * @return list of string with searchParam structure
     * @see ru.sergkorot.dynamic.model.BaseSearchParam
     */
    public static List<String> transformToArraySearchParams(String query) {
        return transformToArrayByPattern(query, SEARCH_PARAM_PATTERN);
    }


    private static List<String> transformToArrayByPattern(String query, Pattern pattern) {

        List<String> params = new ArrayList<>();

        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            String param = matcher.group();
            params.add(param);
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new IllegalArgumentException(String.format("query - [%s] doesn't contain correct string for building request", query));
        }

        return params;
    }
}
