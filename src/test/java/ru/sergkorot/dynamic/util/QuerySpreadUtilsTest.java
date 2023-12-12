package ru.sergkorot.dynamic.util;

import org.junit.jupiter.api.Test;
import ru.sergkorot.dynamic.model.BaseSearchParam;
import ru.sergkorot.dynamic.model.ComplexSearchParam;
import ru.sergkorot.dynamic.model.enums.GlueOperation;
import ru.sergkorot.dynamic.model.enums.OperationType;
import ru.sergkorot.dynamic.model.shell.MultipleOperationShell;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void buildMultipleOperationShellSuccess() {
        var query = "OR(name.eq=Jhon)(name.eq=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.OR, multipleOperationShell.getExternalGlue());

        List<ComplexSearchParam> search = multipleOperationShell.getSearch();
        assertEquals(GlueOperation.AND, search.get(0).getInternalGlue());
        assertEquals(GlueOperation.AND, search.get(1).getInternalGlue());

        assertEquals(1, search.get(0).getBaseSearchParams().size());
        assertEquals(1, search.get(1).getBaseSearchParams().size());
    }

    @Test
    void buildMultipleOperationShellWithDifferentInnerGlueOperation() {
        var query = "OR(name.eq=Jhon)(OR name.eq=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.OR, multipleOperationShell.getExternalGlue());

        List<ComplexSearchParam> search = multipleOperationShell.getSearch();
        assertEquals(GlueOperation.AND, search.get(0).getInternalGlue());
        assertEquals(GlueOperation.OR, search.get(1).getInternalGlue());

        assertEquals(1, search.get(0).getBaseSearchParams().size());
        assertEquals(1, search.get(1).getBaseSearchParams().size());
    }

    @Test
    void buildMultipleOperationShellWithDifferentInnerGlueOperationAndFieldsAndOperationTypes() {
        var query = "OR(name.eq=Jhon)(OR surname.like=Mike)";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.OR, multipleOperationShell.getExternalGlue());

        List<ComplexSearchParam> search = multipleOperationShell.getSearch();
        assertEquals(GlueOperation.AND, search.get(0).getInternalGlue());
        assertEquals(GlueOperation.OR, search.get(1).getInternalGlue());

        assertEquals(1, search.get(0).getBaseSearchParams().size());
        assertEquals(1, search.get(1).getBaseSearchParams().size());

        BaseSearchParam searchParam1 = search.get(0).getBaseSearchParams().get(0);
        BaseSearchParam searchParam2 = search.get(1).getBaseSearchParams().get(0);

        assertEquals("name", searchParam1.getName());
        assertEquals("Jhon", searchParam1.getValue());
        assertEquals(OperationType.EQUAL, OperationType.of(searchParam1.getOperation()));
        assertEquals("surname", searchParam2.getName());
        assertEquals("Mike", searchParam2.getValue());
        assertEquals(OperationType.LIKE, OperationType.of(searchParam2.getOperation()));
    }

    @Test
    void buildComplexMultipleOperationShellWithDifferentInnerGlueOperationAndFieldsAndOperationTypes() {
        var query = "OR(name.like=Jhon age.le=13)(OR surname.like=Mike description.contains=\"When i was young!\")";
        MultipleOperationShell multipleOperationShell = QuerySpreadUtils.transformToOperationShell(query);
        assertEquals(GlueOperation.OR, multipleOperationShell.getExternalGlue());

        List<ComplexSearchParam> search = multipleOperationShell.getSearch();
        assertEquals(GlueOperation.AND, search.get(0).getInternalGlue());
        assertEquals(GlueOperation.OR, search.get(1).getInternalGlue());

        assertEquals(2, search.get(0).getBaseSearchParams().size());
        assertEquals(2, search.get(1).getBaseSearchParams().size());

        BaseSearchParam searchParam1 = search.get(0).getBaseSearchParams().get(0);
        BaseSearchParam searchParam2 = search.get(1).getBaseSearchParams().get(1);

        assertEquals("name", searchParam1.getName());
        assertEquals("Jhon", searchParam1.getValue());
        assertEquals(OperationType.LIKE, OperationType.of(searchParam1.getOperation()));
        assertEquals("description", searchParam2.getName());
        assertEquals("\"When i was young!\"", searchParam2.getValue());
        assertEquals(OperationType.CONTAINS, OperationType.of(searchParam2.getOperation()));
    }

    @Test
    void incorrectRequest() {
        var query = "OR(name.like=Jhon age.le=13)(OR lalala)";
        assertThrows(IllegalArgumentException.class, () -> QuerySpreadUtils.transformToOperationShell(query));
    }
}