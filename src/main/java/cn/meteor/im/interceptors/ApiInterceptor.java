package cn.meteor.im.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Meteor
 */

@Component
public class ApiInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("进入ApiInterceptor...");
        String authorization = request.getHeader("Authorization");
        logger.debug(authorization);
        if (null == authorization || !authorization.startsWith("Bearer ")) {
            logger.error("未带token");
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().print("{\"code\": \"401\", \"error\": \"unauthorized\", \"message\": \"authorization required\"}");
            response.getWriter().flush();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
