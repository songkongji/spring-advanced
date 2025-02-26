package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Object userRoleObj = request.getAttribute("userRole");
        String userRoleObjString = userRoleObj.toString();
        UserRole userRole = UserRole.of(userRoleObjString);
        //UserRole userRole = UserRole.valueOf(request.getAttribute("userRole").toString());

        if (userRole != UserRole.ADMIN) {
            log.error("관리자 권한이 없는 유저가 접근");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "관리자 권한이 없습니다.");
            return false;
        }

        log.info("requestURL : {}, requestTime : {}", request.getRequestURI(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return true;
    }
}
