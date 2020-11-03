package ch.cscf.jeci.persistence.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by henryp on 25/06/15.
 */
public class QueryDslTools {

    public static List<Map<String, Object>> tuplesToMaps(List<Tuple> tuples, List<Expression> expressions){
        return tuples.stream().map(tuple -> tupleToMap(tuple, expressions)).collect(Collectors.toList());
    }

    public static Map<String, Object> tupleToMap(Tuple tuple, List<Expression> expressions){
        Map<String,Object> resultMap = new HashMap<>(expressions.size());
        expressions.stream().forEach(e -> resultMap.put(e.toString(), tuple.get(e)));
        return resultMap;
    }
}
