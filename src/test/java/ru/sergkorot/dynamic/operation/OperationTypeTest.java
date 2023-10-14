package ru.sergkorot.dynamic.operation;

import org.junit.jupiter.api.Test;
import ru.sergkorot.dynamic.model.enums.OperationType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTypeTest {

    @Test
    void inOperationTypeTest() {
        String inOperation = "in";
        assertEquals(inOperation, OperationType.of(inOperation).getOperationName());
    }

    @Test
    void notInOperationTypeTest() {
        String notInOperation = "notIn";
        assertEquals(notInOperation, OperationType.of(notInOperation).getOperationName());
    }

    @Test
    void likeOperationTypeTest() {
        String likeOperation = "like";
        assertEquals(likeOperation, OperationType.of(likeOperation).getOperationName());
    }

    @Test
    void eqOperationTypeTest() {
        String eqOperation = "eq";
        assertEquals(eqOperation, OperationType.of(eqOperation).getOperationName());
    }

    @Test
    void notEqOperationTypeTest() {
        String notEqOperation = "notEq";
        assertEquals(notEqOperation, OperationType.of(notEqOperation).getOperationName());
    }

    @Test
    void isNullOperationTypeTest() {
        String isNullOperation = "isNull";
        assertEquals(isNullOperation, OperationType.of(isNullOperation).getOperationName());
    }

    @Test
    void ltOperationTypeTest() {
        String ltOperation = "lt";
        assertEquals(ltOperation, OperationType.of(ltOperation).getOperationName());
    }

    @Test
    void gtOperationTypeTest() {
        String gtOperation = "gt";
        assertEquals(gtOperation, OperationType.of(gtOperation).getOperationName());
    }

    @Test
    void leOperationTypeTest() {
        String leOperation = "le";
        assertEquals(leOperation, OperationType.of(leOperation).getOperationName());
    }

    @Test
    void geOperationTypeTest() {
        String geOperation = "ge";
        assertEquals(geOperation, OperationType.of(geOperation).getOperationName());
    }

    @Test
    void containsOperationTypeTest() {
        String containsOperation = "contains";
        assertEquals(containsOperation, OperationType.of(containsOperation).getOperationName());
    }
}
