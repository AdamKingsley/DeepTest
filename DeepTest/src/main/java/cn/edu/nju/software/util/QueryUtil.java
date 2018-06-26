package cn.edu.nju.software.util;

import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

/**
 * Created by mengf on 2018/6/9 0009.
 */
public class QueryUtil {

    public static Query queryOnlyId() {
        BasicDBObject fields = new BasicDBObject();
        //只获取_id
        fields.put("_id", true);
        Query query = new BasicQuery(new BasicDBObject(), fields);
        return query;
    }

    public static Query queryByField(String... fieldNames) {
        BasicDBObject fileds = new BasicDBObject();
        for (String field : fieldNames) {
            fileds.put(field, true);
        }
        Query query = new BasicQuery(new BasicDBObject(), fileds);
        return query;
    }


    public static Query queryExceptField(String... fieldNames) {
        BasicDBObject fileds = new BasicDBObject();
        for (String field : fieldNames) {
            fileds.put(field, false);
        }
        Query query = new BasicQuery(new BasicDBObject(), fileds);
        return query;
    }
}
