package com.phoenix.zsfast.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

@Component
@Slf4j
public class RequestLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        
        // 1. 打印基本信息（INFO 级别）
        log.info("📨 请求到达: {} {}, 客户端IP: {}, User-Agent: {}",
            req.getMethod(),
            req.getRequestURI(),
            req.getRemoteAddr(),
            req.getHeader("User-Agent")
        );

        // 2. 打印 GET 请求参数
        Map<String, String[]> paramMap = req.getParameterMap();
        if (paramMap != null && !paramMap.isEmpty()) {
            paramMap.forEach((key, values) -> 
                log.info("📌 参数: {} = {}", key, Arrays.toString(values))
            );
        }

        // 3. 处理 POST/PUT 请求体（必须用包装器，否则 Controller 读不到流）
        ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(req);
        chain.doFilter(wrapper, response);

        // 4. 打印请求体（在 Controller 处理完后读取）
        byte[] body = wrapper.getContentAsByteArray();
        if (body.length > 0) {
            try {
                String bodyStr = new String(body, wrapper.getCharacterEncoding());
                log.info("📦 请求体: {}", bodyStr);
            } catch (UnsupportedEncodingException e) {
                log.warn("获取请求体编码失败: {}", e.getMessage());
            }
        }
    }
}