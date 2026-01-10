package io.github.yw002.springlearn.mybatis.mapper;

import io.github.yw002.springlearn.mybatis.pojo.User;

public interface UserMapper {
    User selectById(int id);
}
