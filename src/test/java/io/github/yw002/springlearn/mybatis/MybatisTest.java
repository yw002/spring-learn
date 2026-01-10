package io.github.yw002.springlearn.mybatis;

import io.github.yw002.springlearn.mybatis.mapper.UserMapper;
import io.github.yw002.springlearn.mybatis.pojo.Order;
import io.github.yw002.springlearn.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    /**
     * 测试mybatis的延迟加载功能
     * 全局配置 mybatis-config.xml 配置 <setting name="lazyLoadingEnabled" value="true"/>
     * 局部配置 userMapper.xml 中配置 <collection ... fetchType="lazy" />
     *
     * @throws IOException
     */
    @Test
    public void testLazyLoading() throws IOException {
        // 1.加载Mybatis核心配置类，解析XML配置信息，构建SqlSessionFactory工厂对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 执行build()方法时，Mybatis解析配置+加载所有XML文件（resource中的文件）
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 2.通过SqlSessionFactory工厂创建SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        /*
        3.一个sqlSession中有一个Executor, Mybatis底层核心组件Executor执行器（调度核心），
        接收SqlSession指令，负责找到对应SQL并执行数据库交互操作
        */
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        /*
        4.Executor执行器创建MappedStatement对象，
         封装Mapper.xml中SQL的完整元数据（id/参数映射/结果映射/语句类型）

         内部完成：接收Mapper接口传入的输入参数并赋值给SQL占位符，
         执行SQL得到结果集后，自动转换映射为指定的Java实体对象
        */
        User user = userMapper.selectById(1);
        System.out.println(user.getUsername());

        System.out.println("\n--------- 在这行之后执行查询订单的sql则是延迟加载 ----------\n");
        // 测试mybatis延迟加载，是否在user.getOrderList()后才执行相关sql
        List<Order> orderList = user.getOrderList();
        System.out.println(orderList);

        // 关闭资源
        sqlSession.close();
    }

    /**
     * 测试mybatis的一级缓存
     * 一级缓存是基于PerpetualCache的HashMap本地缓存，session作用域，在同一个sqlSession才有效，flush或close后就清空
     *
     * @throws IOException
     */
    @Test
    public void testMybatisCacheLevel1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper1 = sqlSession.getMapper(UserMapper.class);
        UserMapper userMapper2 = sqlSession.getMapper(UserMapper.class);
        User user1 = userMapper1.selectById(1);
        System.out.println(user1);
        System.out.println("\n------- 测试一级缓存perpetualCache是否会生效，只执行一次sql查询，第二次使用缓存 -------\n");
        User user2 = userMapper2.selectById(1);
        System.out.println(user2);
        sqlSession.close();
    }

    /**
     * 测试mybatis的二级缓存
     * 二级缓存也是基于PerpetualCache的HashMap，作用域namespace/Mapper.xml，不依赖于SqlSession
     * 默认关闭的，需要开启 <setting name="cacheEnabled" value="true" />, 同时在mapper文件中加<cache/>标签生效二级缓存
     * <p>
     * 特点
     * 二级缓存需要缓存的数据实现Serializable接口
     * 二级缓存只有在会话提交或关闭后，一级缓存中的数据才会转移到二级缓存
     * 当某个作用域（一级缓存sqlSession/二级缓存Namespace）进行了增删改操作后，该作用域下所有的select缓存将被clear
     *
     * @throws IOException
     */
    @Test
    public void testMybatisCacheLevel2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User user1 = userMapper1.selectById(1);
        // 二级缓存只有在会话提交或关闭后，一级缓存中的数据才会转移到二级缓存
        sqlSession1.close();
        System.out.println(user1);
        System.out.println("\n------- 测试二级缓存perpetualCache是否会生效，只执行一次sql查询，第二次使用缓存 -------\n");

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.selectById(1);
        sqlSession2.close();
        System.out.println(user2);
    }
}
