package ru.sergkorot.dynamic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.sergkorot.dynamic.model.enums.GlueOperation;
import ru.sergkorot.dynamic.model.shell.MultipleOperationShell;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuerySpreadUtils {

    public static MultipleOperationShell transformToOperationShell(String query) {
        var operationShell = new MultipleOperationShell();

        setExternalGlue(query, operationShell);

        List<String> operationGroups = RegexpUtils.transformToArrayOperationGroups(query);

        System.out.println(operationGroups);

        return operationShell;
    }

    private static void setExternalGlue(String query, MultipleOperationShell operationShell) {
        if (query.startsWith(GlueOperation.OR.name())) {
            operationShell.setExternalGlue(GlueOperation.OR);
        } else {
            operationShell.setExternalGlue(GlueOperation.AND);
        }
    }
}
