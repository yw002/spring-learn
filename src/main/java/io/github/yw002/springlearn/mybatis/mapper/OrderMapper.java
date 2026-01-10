package io.github.yw002.springlearn.mybatis.mapper;

import io.github.yw002.springlearn.mybatis.pojo.Order;

public interface OrderMapper {
    Order findByUid(int id);
}
