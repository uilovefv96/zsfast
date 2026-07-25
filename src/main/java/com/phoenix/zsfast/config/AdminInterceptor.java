package com.phoenix.zsfast.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("拦截请求: {}", uri);

        HttpSession session = request.getSession(false);
        Boolean isAdmin = session != null ? (Boolean) session.getAttribute("IS_ADMIN") : false;

        if (isAdmin != null && isAdmin) {
            log.info("管理员已登录，放行: {}", uri);
            return true;
        }

        log.warn("未登录或权限不足，拦截: {}", uri);

        // 判断是否为 AJAX/API 请求
        String requestedWith = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(requestedWith)
                || request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json");

        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录或权限不足\"}");
            log.info("返回 401 给 AJAX 请求");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html");
            log.info("重定向到登录页");
        }
        return false;
    }
}