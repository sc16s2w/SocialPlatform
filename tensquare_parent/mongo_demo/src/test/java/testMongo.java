
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class testMongo {
    /**
     * 找到所有的记录
     */

    private MongoClient client;

    private MongoCollection<Document> comment;
    @Test
    public void test1() {
        //创建连接
        MongoClient client = new MongoClient("localhost" , 27017);
        //打开数据库
        MongoDatabase commentdb = client.getDatabase("commentdb");
        //获取集合
        MongoCollection<Document> comment = commentdb.getCollection("comment");

        //查询
        FindIterable<Document> documents = comment.find();
        //查询记录获取文档集合
        for (Document document : documents) {
            System.out.println("_id：" + document.get("_id"));
            System.out.println("内容：" + document.get("content"));
            System.out.println("用户ID:" + document.get("userid"));
            System.out.println("点赞数：" + document.get("thumbup"));
        }
        //关闭连接
        client.close();
    }

    @Before
    public void init() {
        //创建连接
        client = new MongoClient("localhost" , 27017);
        //打开数据库
        MongoDatabase commentdb = client.getDatabase("commentdb");
        //获取集合
        comment = commentdb.getCollection("comment");
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void test2(){
        //封装查询条件
        BasicDBObject bson = new BasicDBObject("_id","1");
        FindIterable<Document> documents  = comment.find(bson);
        for(Document document:documents){
            System.out.println("_id：" + document.get("_id"));
            System.out.println("内容：" + document.get("content"));
            System.out.println("用户ID:" + document.get("userid"));
            System.out.println("点赞数：" + document.get("thumbup"));
        }
    }

    /**
     * 新增
     */
    @Test
    public void test3(){
        Map<String,Object> map = new HashMap<>();
        map.put("_id","6");
        map.put("content","很烦");
        map.put("userid","1024");
        map.put("thumbup","1000");
        Document document = new Document(map);
        comment.insertOne(document);
    }

    /**
     * 修改
     */
    @Test
    public void test4(){
        BasicDBObject bson = new BasicDBObject("_id","6");
        BasicDBObject bson1 = new BasicDBObject("$set",new Document("userid","999"));
        comment.updateOne(bson,bson1);

    }
    /**
     * 删除
     */
    @Test
    public void test5(){
        BasicDBObject bson = new BasicDBObject("_id","6");
        comment.deleteOne(bson);
    }
}
