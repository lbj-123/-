package com.edu.controller;


import com.edu.pojo.Users;
import com.edu.service.IUsersService;
import com.edu.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService iUsersService;
    @PostMapping("/add")
    public RespBean addUser(@RequestBody Users users){
        boolean isExist = iUsersService.existsByName(users.getUsername());
        if(isExist){
            return RespBean.error("用户已存在，添加失败");
        }
        if(iUsersService.save(users)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/delete")
    public RespBean deleteUser(@RequestParam String username){
        boolean isRemoved = iUsersService.removeByName(username);
        if(isRemoved){
            return RespBean.success("删除成功");
        }
        return RespBean.error("没有该用户，删除失败！");
    }

}
