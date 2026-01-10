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
    @Test
    public void testSelectById() throws IOException {
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
}
