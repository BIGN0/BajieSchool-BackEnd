package com.zlfinfo.controller;

import com.zlfinfo.commons.base.BaseController;
import com.zlfinfo.model.LoginStatus;
import com.zlfinfo.model.User;
import com.zlfinfo.service.LoginStatusService;
import com.zlfinfo.service.UserService;
import com.zlfinfo.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/22.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginStatusService loginStatusService;

    /**
     * 根据username查用户
     *
     * @param username 用户名称
     * @return
     */
    @RequestMapping(value = "/login/{username}", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@PathVariable String username, @RequestParam String password, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.findUserByUsername(username);
        loginStatusService.updateStatus(new LoginStatus(username, 1, new Date()));
        if (null != user) {
            logger.debug(user + "--------------------------------" + password);
            if (Encryption.encrypt(password.trim()).equals(user.getPassword())) {
                return renderSuccess("密码验证成功!", response);
            } else {
                return renderError("用户名或密码错误", response);
            }

        } else {
            return renderError("用户名或密码错误", response);
        }
    }

    /**
     * 显示学习发帖人的用户信息
     *
     * @param stdId
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "/userstudy/{stdId}", method = RequestMethod.GET)
    @ResponseBody
    public Object showUser(@PathVariable Integer stdId, HttpServletResponse httpServletResponse) {
        User user = userService.selectStudyUser(stdId);

        return renderSuccess(user, httpServletResponse);
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public Object adduser(@RequestParam(required = false) String username, @RequestParam(required = false) String
            password,
                          @RequestParam(required = false) String sex, @RequestParam(required = false) String cellphone,
                          @RequestParam(required = false) String enrollYear, @RequestParam(required = false) Integer
                                  university,
                          @RequestParam(required = false) String institution,
                          @RequestParam(required = false) String level, HttpServletResponse response) throws
            UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User(username, null, Encryption.encrypt(password), sex, cellphone, null, null, university,
                institution, enrollYear, level, null, null, null, null, null);

        System.out.println(user.toString());
        userService.insert(user);
        return renderSuccess("注册成功", response);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/chpassword", method = RequestMethod.POST)
    @ResponseBody
    public Object chPassword(@RequestParam String username, String password, HttpServletResponse httpServletResponse) {
        int i = userService.updatePassword(username, password);
        if (i != 0) {
            return renderSuccess("修改成功", httpServletResponse);
        } else {
            return renderError("修改失败", httpServletResponse);
        }
    }


    /**
     * 查询所有用户
     *
     * @deprecated
     */
    @Deprecated
    @RequestMapping(value = "/showallusers", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllUsers(HttpServletResponse response) {
        return renderSuccess(userService.findAllUsers(), response);
    }

    @RequestMapping(value = "/checkname/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Object checkUserExist(@PathVariable String username, HttpServletResponse httpServletResponse) {
        User user = userService.findUserByUsername(username);
        return null == user ? renderSuccess("YES", httpServletResponse) :
                renderError("NO", httpServletResponse);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Object findUserByUsername(@PathVariable String username, HttpServletResponse httpServletResponse) {
        User user = userService.findUserByUsername(username);
        return null != user ? renderSuccess(user, httpServletResponse) : renderError("用户查询失败!", httpServletResponse);
    }

}
