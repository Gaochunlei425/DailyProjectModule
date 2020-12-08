package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_test")
public class UserTest {
    /**
     * id主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户
     */
    private String user;

    /**
     * 密码
     */
    private Integer password;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modified_time")
    private Date modifiedTime;

    /**
     * 获取id主键
     *
     * @return id - id主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id主键
     *
     * @param id id主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户
     *
     * @return user - 用户
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置用户
     *
     * @param user 用户
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public Integer getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(Integer password) {
        this.password = password;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return modified_time - 更新时间
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置更新时间
     *
     * @param modifiedTime 更新时间
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}