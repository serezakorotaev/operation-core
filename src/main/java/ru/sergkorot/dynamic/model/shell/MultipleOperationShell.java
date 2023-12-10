package ru.sergkorot.dynamic.model.shell;

import lombok.Data;
import ru.sergkorot.dynamic.model.ComplexSearchParam;
import ru.sergkorot.dynamic.model.PageAttribute;
import ru.sergkorot.dynamic.model.enums.GlueOperation;

import java.util.List;

import static ru.sergkorot.dynamic.model.enums.GlueOperation.AND;

/**
 * @author Sergey Korotaev
 * Shell for complex search requests
 */
@Data
public class MultipleOperationShell {
    private List<ComplexSearchParam> search;
    private GlueOperation externalGlue = AND;
    private PageAttribute pageAttribute;
}
