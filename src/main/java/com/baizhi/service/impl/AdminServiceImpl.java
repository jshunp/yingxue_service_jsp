package com.baizhi.service.impl;

import com.baizhi.entity.Admin;
import com.baizhi.dao.AdminDao;
import com.baizhi.dto.AdminBody;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * (YxAdmin)表服务实现类
 *
 * @author makejava
 * @since 2021-09-22 10:23:44
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Admin queryById(Integer id) {
        return this.adminDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Admin> queryAllByLimit(int offset, int limit) {
        return this.adminDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin insert(Admin admin) {
        this.adminDao.insert(admin);
        return admin;
    }

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin update(Admin admin) {
        this.adminDao.update(admin);
        return this.queryById(admin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.adminDao.deleteById(id) > 0;
    }

    @Override
    public Map<String, Object> verificationCode() throws IOException {
        //获取验证码
        String securityCode = ImageCodeUtil.getSecurityCode();
        //将字符串转成图片存储到redis里
        String careateImgBase64 = ImageCodeUtil.careateImgBase64(securityCode);
        // 生成一个存入redis的唯一key
        String codeId = UUID.randomUUID().toString();
        //key -- value --  time
        // 将生成的id存入key，值为验证码，时间60秒
        redisTemplate.opsForValue().set(codeId,securityCode, TimeUnit.SECONDS.toSeconds(60));
        System.out.println(securityCode+"---------------------");
        // 创建一个map，存入codeId，imageCode
        HashMap<String, Object> map = new HashMap<>();
        // 返回给前台的redis--key
        map.put("codeId",codeId);
        // 将图片返回前台
        map.put("imageCode",careateImgBase64);
        return map;
    }

    /**
     * 登录验证
     * @param adminBody
     * @return
     */
    @Override
    public Map<String, Object> login(AdminBody adminBody) {
        HashMap<String,Object> map = new HashMap<>();
        //通过页面传过来的id拿到值和redis里的对比
        String enCode = adminBody.getEnCode();
        String enCode1=(String) redisTemplate.opsForValue().get(adminBody.getCodeId());
       //判断验证码是否过期
        if (enCode!=null) {
            if (!enCode.equals(enCode1)) {
                map.put("message", "验证码错误");
                map.put("status",401);
            }
           else if(adminBody.getUsername()!=null){
                List<Admin> admins = adminDao.queryAll(new Admin(null, adminBody.getUsername(), null, null, null, null));
                if (admins.size()==0){
                    map.put("message","用户名不存在");
                    map.put("status",401);
                }else {
                    List<Admin> admins1 = adminDao.queryAll(new Admin(null, adminBody.getUsername(), adminBody.getPassword(), null, null, null));
                    if (admins1.size()>0){
                        map.put("status",200);
                        //
                        String token = UUID.randomUUID().toString();
                        redisTemplate.opsForValue().set(token,admins1.get(0).getId(),TimeUnit.SECONDS.toSeconds(15));
                        map.put("message",token);
                    }else {
                        map.put("message","密码错误");
                        map.put("status",401);
                    }
                }
            }
        }else {
            map.put("message", "验证码为空");
            map.put("status",401);
        }
        return map;
    }

    @Override
    public Admin queryToken(String token) {
        Integer o = (Integer) redisTemplate.opsForValue().get(token);
        return adminDao.queryById(o);
    }
}