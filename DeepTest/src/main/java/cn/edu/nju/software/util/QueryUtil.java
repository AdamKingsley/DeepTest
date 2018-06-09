package cn.edu.nju.software.util;

import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by mengf on 2018/6/9 0009.
 */
public class QueryUtil {

    public static Query queryOnlyId() {
        BasicDBObject fileds = new BasicDBObject();
        //只获取image_id
        fileds.put("_id", true);
        Query query = new BasicQuery(new BasicDBObject(), fileds);
        return query;
    }
}
