package cn.meteor.im.web.message;

import cn.meteor.im.dto.MessageExecutor;
import cn.meteor.im.dto.SysResult;
import cn.meteor.im.entity.Message;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.MessageStateEnum;
import cn.meteor.im.service.MessageService;
import cn.meteor.im.util.ErrorUtils;
import cn.meteor.im.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meteor
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/message")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public SysResult sendMessage(HttpServletRequest request, @Validated Message message, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return SysResult.fail(ErrorUtils.parserError(bindingResult));
        }

        try {
            UserInfo currentUser = UserUtil.getCurrentUser(request);
            message.setSenderId(currentUser.getUserId());
            MessageExecutor executor = messageService.sendMessage(message);

            if (executor.getState() == MessageStateEnum.SUCCESS.getState()) {
                return SysResult.success(executor.getChatMessage());
            } else {
                return SysResult.fail();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SysResult.fail(e.getMessage());
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult sendMessage(HttpServletRequest request) {

        try {
            UserInfo currentUser = UserUtil.getCurrentUser(request);
            Long userId = currentUser.getUserId();

            MessageExecutor executor = messageService.getHistory(userId);

            if (executor.getState() == MessageStateEnum.SUCCESS.getState()) {
                return SysResult.success(executor.getMessageHistoryList());
            } else {
                return SysResult.fail();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SysResult.fail(e.getMessage());
        }
    }

}
