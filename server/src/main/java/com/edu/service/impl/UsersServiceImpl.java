package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.config.security.JwtTokenUtil;
import com.edu.pojo.Users;
import com.edu.mapper.UsersMapper;
import com.edu.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.util.RespBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pst
 * @since 2024-12-20
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public boolean existsByName(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return this.count(queryWrapper)>0;
    }

    @Override
    public boolean removeByName(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Users users =this.getOne(queryWrapper);
        if(users!=null){
            return this.remove(queryWrapper);
        }
        return false;
    }
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Users getAdminByUserName(String username) {
        return usersMapper.selectOne(new QueryWrapper<Users>().eq("username",username).eq(
                "enabled",true));
    }

    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        //        String captcha = (String) request.getSession().getAttribute("captcha");
//        if(StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
//            return RespBean.error("验证码输入错误！,请重新输入");
//        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails||!userDetails.getPassword().equals(password)){
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理人员!");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken=new
                UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

}
