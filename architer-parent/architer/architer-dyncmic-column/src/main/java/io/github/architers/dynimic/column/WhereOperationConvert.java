package io.github.architers.dynimic.column;

import io.github.architers.model.query.WhereOperator;

public interface WhereOperationConvert {

    String convert(WhereOperator whereOperator);
}
