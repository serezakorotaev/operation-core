package ru.sergkorot.dynamic.glue;

import java.util.List;

public interface Glue<T> {

    T buildGlue(List<T> conditions);
}
