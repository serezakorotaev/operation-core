package ru.sergkorot.dynamic.util;

import org.junit.jupiter.api.Test;
import ru.sergkorot.dynamic.model.enums.GlueOperation;
import ru.sergkorot.dynamic.model.shell.MultipleOperationShell;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuerySpreadUtilsTest {

    @Test
    void andExternalGlueDefault() {
        var query = "(name.eq=Jhon)(name.eq=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.AND, multipleOperationShell.getExternalGlue());
    }

    @Test
    void andExternalGlue() {
        var query = "AND(name.eq=Jhon)(name.eq=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.AND, multipleOperationShell.getExternalGlue());
    }

    @Test
    void orExternalGlue() {
        var query = "OR(name.eq=Jhon)(name.eq=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.OR, multipleOperationShell.getExternalGlue());
    }


}