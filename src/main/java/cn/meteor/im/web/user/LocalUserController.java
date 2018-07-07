package cn.meteor.im.web.user;

import cn.meteor.im.dto.SysResult;
import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.UserStateEnum;
import cn.meteor.im.service.UserService;
import cn.meteor.im.util.ErrorUtils;
import cn.meteor.im.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meteor
 */
@RestController
@RequestMapping("/user/")
public class LocalUserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册本地账号",
            httpMethod = "POST",
            response = SysResult.class)
    @RequestMapping(value = "/localAuth", method = RequestMethod.POST)
    public SysResult registerAsLocalAuth(@Validated LocalAuth localAuth, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return SysResult.fail(ErrorUtils.parserError(bindingResult));
        }

        try {
            UserExecutor userExecutor = userService.addLocalUser(localAuth);

            if (userExecutor.getState() == UserStateEnum.SUCCESS.getState()) {
                return SysResult.success(userExecutor.getUserInfo());
            } else {
                return SysResult.fail(userExecutor.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "获取token",
            httpMethod = "POST",
            response = SysResult.class)
    @RequestMapping(value = "/localAuth/token", method = RequestMethod.POST)
    public SysResult loginAsLocalAuth(@Validated LocalAuth localAuth, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return SysResult.fail(ErrorUtils.parserError(bindingResult));
        }

        try {
            UserExecutor userExecutor = userService.loginLocalUser(localAuth);

            if (userExecutor.getState() == UserStateEnum.SUCCESS.getState()) {
                return SysResult.success(userExecutor.getJwt());
            } else {
                return SysResult.fail(userExecutor.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/localAuthFail", method = RequestMethod.GET)
    public SysResult registerAsLocalAuthFail(HttpServletRequest request, @Validated LocalAuth localAuth, BindingResult bindingResult) {
        UserInfo userInfo = UserUtil.getCurrentUser(request);
        return SysResult.fail();
    }

}
