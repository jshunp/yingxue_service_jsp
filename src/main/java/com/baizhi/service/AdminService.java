package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.dto.AdminBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (YxAdmin)表服务接口
 *
 * @author makejava
 * @since 2021-09-22 10:23:44
 */
public interface AdminService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Admin queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Admin> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Admin insert(Admin admin);

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Admin update(Admin admin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 验证码
     * @return
     */
    Map<String,Object>  verificationCode() throws IOException;

    Map<String,Object> login(AdminBody adminBody);

    Admin queryToken(String token);

}