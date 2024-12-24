package com.edu.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.pojo.Users;
import com.edu.service.IUsersService;
import com.edu.util.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class LoginController {
    @Autowired
    private IUsersService usersService;
    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody Users user, HttpServletRequest request){
        RespBean respBean= usersService.login(user.getUsername(),user.getPassword(),
                request);
//        System.out.println(res);
        return respBean;
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/users/info")
    public Users getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        Users user = usersService.getAdminByUserName(username);
        user.setPassword(null);
        return user;
    }
}
