package com.baizhi;

import com.baizhi.dao.AdminDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YingxueServiceJspApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Test
    void contextLoads() {
        adminDao.queryById(1);
    }

}
