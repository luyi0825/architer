package io.github.architers.query.dynimic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Where {
    private List<WhereCondition> whereConditions;

    private boolean and = true;

    private List<Where> wheres;

    public Where() {
    }

    public static Where builder() {
        return new Where();
    }

    public Where(List<WhereCondition> whereConditions, boolean and, List<Where> wheres) {
        this.whereConditions = whereConditions;
        this.and = and;
        this.wheres = wheres;
    }

    public Where addWhereCondition(WhereCondition... whereConditionParams) {
        if (whereConditions == null) {
            whereConditions = new ArrayList<>();
        }
        whereConditions.addAll(List.of(whereConditionParams));
        return this;
    }

    public Where addWhere(Where... whereParams) {
        if (wheres == null) {
            wheres = new ArrayList<>();
        }
        wheres.addAll(List.of(whereParams));
        return this;
    }
}
