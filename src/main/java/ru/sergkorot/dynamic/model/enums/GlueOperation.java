package ru.sergkorot.dynamic.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.sergkorot.dynamic.glue.Glue;
import ru.sergkorot.dynamic.glue.GlueOperationProvider;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum GlueOperation {

    AND(GlueOperationProvider::and),
    OR(GlueOperationProvider::or);

    private final Function<GlueOperationProvider<?>, Glue<?>> glueFunction;

    @SuppressWarnings("unchecked")
    public <G> Glue<G> getGlue(GlueOperationProvider<G> glueOperationProvider) {
        return (Glue<G>) glueFunction.apply(glueOperationProvider);
    }

}
