package ru.sergkorot.dynamic.glue;

public interface GlueOperationProvider<T> {

    Glue<T> and();

    Glue<T> or();
}
