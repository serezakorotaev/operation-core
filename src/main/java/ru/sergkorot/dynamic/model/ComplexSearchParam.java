package ru.sergkorot.dynamic.model;

import lombok.Data;
import ru.sergkorot.dynamic.model.enums.GlueOperation;

import java.util.List;

import static ru.sergkorot.dynamic.model.enums.GlueOperation.AND;

/**
 * @author Sergey Korotaev
 * Class for complex search params
 */
@Data
public class ComplexSearchParam {
    List<BaseSearchParam> baseSearchParams;
    GlueOperation internalGlue = AND;
}
