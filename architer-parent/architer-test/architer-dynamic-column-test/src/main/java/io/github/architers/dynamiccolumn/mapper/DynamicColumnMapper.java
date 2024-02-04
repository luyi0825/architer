package io.github.architers.dynamiccolumn.mapper;

import io.github.architers.model.query.DynamicColumnConditions;
import io.github.architers.model.request.ConditionPageRequest;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface DynamicColumnMapper {

    List<Map<String, Object>> getDynamicList(DynamicColumnConditions dynamicColumnConditions);


    @SelectProvider(type = DynamicColumnProvider.class, method = "dynamicList")
    List<Map<String, Object>> dynamicList(DynamicColumnConditions dynamicColumnConditions);


   public class DynamicColumnProvider {
        

       public final String dynamicList(DynamicColumnConditions dynamicColumnConditions){
           SQL sql=  new SQL();
           for (DynamicColumnConditions.Column column : dynamicColumnConditions.getColumns()) {
               sql.SELECT(column.getColumnName() + " as  " + column.getColumnAlias());
           }
           sql.FROM("dynamic_column");
           for (DynamicColumnConditions.Where where : dynamicColumnConditions.getWheres()) {
               sql.WHERE(where.getColumnName() +" in (?,?)");
               sql.INTO_VALUES((String[])where.getConvertValue());
           }
           return sql.toString();


        }

    }
}
