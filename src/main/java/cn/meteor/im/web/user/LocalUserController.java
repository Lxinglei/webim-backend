package cn.meteor.im.web.user;

import cn.meteor.im.dto.SysResult;
import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.enums.UserStateEnum;
import cn.meteor.im.service.UserService;
import cn.meteor.im.util.ErrorUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            return SysResult.fail(e.getMessage());
        }
    }

}
