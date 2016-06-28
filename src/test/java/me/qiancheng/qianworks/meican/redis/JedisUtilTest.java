package me.qiancheng.qianworks.meican.redis;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtilTest {

    public static final String HOST = "127.0.0.1";

    Jedis jedis;

    @BeforeMethod
    public void setUp() {
        jedis = new JedisPool(new JedisPoolConfig(), HOST).getResource();
        //   jedis.auth("password");
    }


    @Test
    public void testGet(){
        Assert.assertNull(jedis.get("lu"));
    }

    /**
     * Redis存储初级的字符串
     * CRUD
     */
    @Test
    public void testBasicString(){
        //-----添加数据----------
        jedis.set("name","qian");//向key-->name中放入了value-->qian
        Assert.assertEquals(jedis.get("name"),"qian");//执行结果：qian

        //-----修改数据-----------
        //1、在原来基础上修改
        jedis.append("name","qianworks");   //很直观，类似map 将jarorwar append到已经有的value之后
        Assert.assertEquals(jedis.get("name"),"qianqianworks");//执行结果:qianqianworks

        //2、直接覆盖原来的数据
        jedis.set("name","千橙");
        Assert.assertEquals(jedis.get("name"),"千橙");//执行结果：千橙

        //删除key对应的记录
        jedis.del("name");
        Assert.assertNull(jedis.get("name"));//执行结果：null

        /**
         * mset相当于
         * jedis.set("name","qian");
         * jedis.set("qianworks","千橙");
         */
        jedis.mset("name","qian","qianworks","千橙");
        Assert.assertEquals(jedis.mget("name","qianworks").get(0).toString(),"qian");
        Assert.assertEquals(jedis.mget("name","qianworks").get(1).toString(),"千橙");

    }

    /**
     * jedis操作Map
     */
    @Test
    public void testMap(){
        Map<String,String> user=new HashMap<String,String>();
        user.put("name","qian");
        user.put("pwd","password");
        jedis.hmset("user",user);
        //取出user中的name，执行结果:[qian]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name");
        Assert.assertEquals(rsmap.get(0).toString(),"qian");

        //删除map中的某个键值
        jedis.hdel("user","pwd");
        Assert.assertNull(jedis.hmget("user", "pwd").get(0)); //因为删除了，所以返回的是null
        Assert.assertEquals(jedis.hlen("user").intValue(),1); //返回key为user的键中存放的值的个数1
        Assert.assertTrue(jedis.exists("user"));//是否存在key为user的记录 返回true
        Assert.assertFalse(jedis.hkeys("user").contains("pwd"));//返回map对象中的所有key  [pwd, name]
        Assert.assertTrue(jedis.hvals("user").contains("qian"));//返回map对象中的所有value  [qian, password]

        Iterator<String> iter=jedis.hkeys("user").iterator();
        while (iter.hasNext()){
            String key = iter.next();
            System.out.println(key+":"+jedis.hmget("user",key));
        }

    }

    /**
     * jedis操作List
     */
    @Test
    public void testList(){
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework",0,-1));
        //先向key java framework中存放三条数据
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","struts");
        jedis.lpush("java framework","hibernate");
        jedis.lpop("java framework");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework",0,-1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet(){
        //添加
        jedis.sadd("sname","qian");
        jedis.sadd("sname","qianworks");
        jedis.sadd("sname","千橙");
        jedis.sadd("sanme","noname");
        //移除noname
        jedis.srem("sname","noname");
        System.out.println(jedis.smembers("sname"));//获取所有加入的value
        System.out.println(jedis.sismember("sname", "qian"));//判断 qian 是否是sname集合的元素
        System.out.println(jedis.srandmember("sname"));
        System.out.println(jedis.scard("sname"));//返回集合的元素个数
    }

    @Test
    public void test() throws InterruptedException {
        //keys中传入的可以用通配符
        System.out.println(jedis.keys("*")); //返回当前库中所有的key  [sose, sanme, name, qianworks, foo, sname, java framework, user, braand]
        System.out.println(jedis.keys("*name"));//返回的sname   [sname, name]
        System.out.println(jedis.del("sanmdde"));//删除key为sanmdde的对象  删除成功返回1 删除失败（或者不存在）返回 0
        System.out.println(jedis.ttl("sname"));//返回给定key的有效时间，如果是-1则表示永远有效
        jedis.setex("timekey", 10, "min");//通过此方法，可以指定key的存活（有效时间） 时间为秒
        Thread.sleep(5000);//睡眠5秒后，剩余时间将为<=5
        System.out.println(jedis.ttl("timekey"));   //输出结果为5
        jedis.setex("timekey", 1, "min");        //设为1后，下面再看剩余时间就是1了
        System.out.println(jedis.ttl("timekey"));  //输出结果为1
        System.out.println(jedis.exists("key"));//检查key是否存在
        System.out.println(jedis.rename("timekey","time"));
        System.out.println(jedis.get("timekey"));//因为移除，返回为null
        System.out.println(jedis.get("time")); //因为将timekey 重命名为time 所以可以取得值 min

        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a","6");
        jedis.lpush("a","3");
        jedis.lpush("a","9");
        System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
        System.out.println(jedis.lrange("a",0,-1));

    }


}