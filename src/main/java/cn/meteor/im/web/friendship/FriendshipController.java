package cn.meteor.im.web.friendship;

import cn.meteor.im.dto.FriendshipExecutor;
import cn.meteor.im.dto.SysResult;
import cn.meteor.im.entity.Friendship;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.FriendshipStateEnum;
import cn.meteor.im.service.FriendShipService;
import cn.meteor.im.util.UserUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meteor
 */
@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private FriendShipService friendShipService;

    /**
     * 获取好友列表
     * @param request
     * @return
     */
    @ApiOperation(value = "获取好友列表",
            httpMethod = "GET",
            response = SysResult.class)
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public SysResult getFriendList(HttpServletRequest request) {

        try {
            UserInfo currentUser = UserUtil.getCurrentUser(request);
            FriendshipExecutor executor = friendShipService.listFriends(currentUser);
            if (executor.getState() == FriendshipStateEnum.SUCCESS.getState()) {
                return SysResult.success(executor.getFriendshipList());
            } else {
                return SysResult.fail("获取好友列表失败");
            }
        } catch (Exception e) {
            return SysResult.fail(e.getMessage());
        }
    }
}
