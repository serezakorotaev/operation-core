package ru.sergkorot.dynamic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.sergkorot.dynamic.model.BaseSearchParam;
import ru.sergkorot.dynamic.model.ComplexSearchParam;
import ru.sergkorot.dynamic.model.shell.MultipleOperationShell;
import ru.sergkorot.dynamic.model.enums.GlueOperation;

import java.util.List;

/**
 * @author Sergey Korotaev
 * Util is used for transforming string with user's query by pattern to object for further requests into the database
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuerySpreadUtils {

    /**
     * Transforming query from outside to MultipleOperationShell for further requests into the database
     *
     * @param query -  query from outside. example OR(name.eq=Jhon)(OR surname.like=Mike)
     * @return MultipleOperationShell
     * @see MultipleOperationShell
     */
    public static MultipleOperationShell transformToOperationShell(String query) {

        var operationShell = new MultipleOperationShell();

        operationShell.setExternalGlue(defineGlue(query));

        // part one - grouping
        List<String> operationGroups = RegexpUtils.transformToArrayOperationGroups(query);

        // part two - building ComplexSearchParam
        operationShell.setSearch(defineComplexSearchParams(operationGroups));

        return operationShell;
    }

    private static List<ComplexSearchParam> defineComplexSearchParams(List<String> operationGroups) {
        return operationGroups.stream()
                .map(QuerySpreadUtils::defineComplexSearchParam)
                .toList();
    }

    private static ComplexSearchParam defineComplexSearchParam(String group) {
        ComplexSearchParam complexSearchParam = new ComplexSearchParam();
        complexSearchParam.setInternalGlue(defineGlue(group));

        List<String> searchParams = RegexpUtils.transformToArraySearchParams(group);
        complexSearchParam.setBaseSearchParams(buildSearchParams(searchParams));

        return complexSearchParam;
    }

    private static List<BaseSearchParam> buildSearchParams(List<String> searchParams) {
        return searchParams.stream()
                .map(QuerySpreadUtils::buildSearchParam)
                .toList();
    }

    private static BaseSearchParam buildSearchParam(String rawString) {
        String name = rawString.substring(0, rawString.indexOf("."));
        String operation = rawString.substring(rawString.indexOf(".") + 1, rawString.indexOf("="));
        Object value = rawString.substring(rawString.indexOf("=") + 1);
        return new BaseSearchParam(name, value, operation);
    }

    private static GlueOperation defineGlue(String query) {
        if (query.startsWith(GlueOperation.OR.name()) ||
                query.startsWith("(" + GlueOperation.OR.name())) {
            return GlueOperation.OR;
        }
        return GlueOperation.AND;
    }
}
