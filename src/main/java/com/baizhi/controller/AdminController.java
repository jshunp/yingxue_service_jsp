package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.dto.AdminBody;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController{
    @Autowired
    private AdminService adminService;

    @PostMapping("getImageCode")
    public Map<String,Object> getImageCode() throws IOException {
        System.out.println("--------------------");
        return adminService.verificationCode();
    }

    @PostMapping("login")
    public Map<String,Object>  login(@RequestBody AdminBody adminBody){
       return adminService.login(adminBody);
    }

    @PostMapping("queryToken")
    public Admin queryToken(String token){
      return   adminService.queryToken(token);
    }
}
