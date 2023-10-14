package ru.sergkorot.dynamic.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegexpUtilTest {

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
}
