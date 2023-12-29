package ru.sergkorot.dynamic.glue;

import ru.sergkorot.dynamic.model.enums.GlueOperation;

import java.util.List;

@SuppressWarnings("unused")
public interface GlueOperationService<T> {

    default T buildGlue(GlueOperationProvider<T> glueOperationProvider, List<T> conditions, GlueOperation glue) {
        return glue.getGlue(glueOperationProvider).buildGlue(conditions);
    }
}
