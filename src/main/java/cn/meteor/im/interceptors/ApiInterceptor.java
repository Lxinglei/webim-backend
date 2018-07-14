package cn.meteor.im.interceptors;

import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Meteor
 */

@Component
public class ApiInterceptor implements HandlerInterceptor {

    private static final String TOKEN_HEADER= "Bearer ";

    private Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("进入ApiInterceptor...");
        String authorization = request.getHeader("Authorization");
        if (null == authorization) {
            authorization = request.getParameter("access_token");
            if (null != authorization) {
                authorization = TOKEN_HEADER + authorization;
            }
        }
        logger.debug(authorization);
        if (null == authorization || !authorization.startsWith(TOKEN_HEADER)) {
            logger.error("未带token");
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().print("{\"code\": \"401\", \"error\": \"unauthorized\", \"message\": \"authorization required\"}");
            response.getWriter().flush();
            return false;
        } else {
            String token = authorization.replace(TOKEN_HEADER, "");

            try {
                Claims claims = JwtUtil.verifyToken(token);
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(Long.parseLong(claims.get("userId").toString()));
                request.setAttribute("currentUser", userInfo);
            } catch (ExpiredJwtException e) {
                response.reset();
                PrintWriter pw = response.getWriter();
                logger.error("token已过期:{}", e.getMessage());
                response.setStatus(404);
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                pw.print("{\"code\": \"4042\", \"error\": \"ExpiredToken\", \"message\": \"" + e.getMessage() + "\"}");
                pw.flush();
            } catch (UnsupportedJwtException | MalformedJwtException e) {
                response.reset();
                PrintWriter pw = response.getWriter();
                logger.error("非法的token:{}", e.getMessage());
                response.setStatus(404);
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                pw.print("{\"code\": \"4041\", \"error\": \"IllegalToken\", \"message\": \"" + e.getMessage() + "\"}");
                pw.flush();
            }

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
