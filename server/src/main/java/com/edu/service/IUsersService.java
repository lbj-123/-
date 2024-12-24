package com.edu.service;

import com.edu.pojo.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.util.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
public interface IUsersService extends IService<Users> {

    boolean existsByName(String username);

    boolean removeByName(String username);

    Users getAdminByUserName(String username);

    RespBean login(String username, String password, HttpServletRequest request);
}
