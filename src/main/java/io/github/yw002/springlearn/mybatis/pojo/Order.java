package io.github.yw002.springlearn.mybatis.pojo;

public class Order {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer status;
    private User user;

    public Order() {
    }
    public Order(Integer id, String name, Integer userId, Integer status, User user) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.status = status;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
