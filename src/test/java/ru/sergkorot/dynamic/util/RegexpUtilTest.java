package ru.sergkorot.dynamic.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexpUtilTest {

    @Test
    void cutStringWithValuesToListWithValuesSuccess() {
        String values = "-id,name,-hello";

        List<String> generatedValues = RegexpUtils.transformToArrayFieldsNames(values);

        assertLinesMatch(List.of("-id", "name", "-hello"), generatedValues);
    }

    @Test
    void cutStringWithValuesInKebabCaseToListWithValuesSuccess() {
        String values = "-id,name-value,-hello-world";

        List<String> generatedValues = RegexpUtils.transformToArrayFieldsNames(values);

        assertLinesMatch(List.of("-id", "name-value", "-hello-world"), generatedValues);
    }

    @Test
    void cutStringWithValuesInUnderScoreToListWithValuesSuccess() {
        String values = "-id,name_value,-hello_world";

        List<String> generatedValues = RegexpUtils.transformToArrayFieldsNames(values);

        assertLinesMatch(List.of("-id", "name_value", "-hello_world"), generatedValues);
    }

    @Test
    void cutStringWithValuesInCamelCaseToListWithValuesSuccess() {
        String values = "-id,nameValue,-helloWorld";

        List<String> generatedValues = RegexpUtils.transformToArrayFieldsNames(values);

        assertLinesMatch(List.of("-id", "nameValue", "-helloWorld"), generatedValues);
    }

    @Test
    void cutStringWithValuesToListWithValuesFailed() {
        String values = "-i%d^,name;Value,-hello*World";

        assertThrows(IllegalArgumentException.class, () -> RegexpUtils.transformToArrayFieldsNames(values));
    }

    @Test
    void successOperationGrouping() {
        var query = "OR(name.eq=Jhon)(name.eq=Mike)";
        List<String> operationGroups = RegexpUtils.transformToArrayOperationGroups(query);

        assertEquals(2, operationGroups.size());
        assertTrue(operationGroups.contains("(name.eq=Jhon)"));
    }

    @Test
    void failIncorrectQuery() {
        var query = "ORsdfd";
        assertThrows(IllegalArgumentException.class, () -> RegexpUtils.transformToArrayOperationGroups(query));
    }

    @Test
    void failEmptyOperationGroups() {
        var query = "OR()()";
        assertThrows(IllegalArgumentException.class, () -> RegexpUtils.transformToArrayOperationGroups(query));
    }

    @Test
    void succesWhenAtLeastOneOperationGroupExists() {
        var query = "OR()(name.eq=Mike)";
        List<String> operationGroups = RegexpUtils.transformToArrayOperationGroups(query);

        assertEquals(1, operationGroups.size());
        assertTrue(operationGroups.contains("(name.eq=Mike)"));
    }
}
