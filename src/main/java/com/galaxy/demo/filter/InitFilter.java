package com.galaxy.demo.filter;
import org.apache.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class InitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest hreq = (HttpServletRequest) req;
            HttpServletResponse hresp = (HttpServletResponse) resp;

            hresp.setHeader("Access-Control-Allow-Origin", "*");
            hresp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            hresp.setHeader("Access-Control-Max-Age", "3600");
            hresp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, token");
            hresp.addHeader("Access-Control-Allow-Credentials","true");
            // 浏览器是会先发一次options请求，如果请求通过，则继续发送正式的post请求
            // 配置options的请求返回
            if (hreq.getMethod().equals("OPTIONS")) {
                hresp.setStatus(HttpStatus.SC_OK);
                // hresp.setContentLength(0);
                hresp.getWriter().write("");
                return;
            }
            /*SysContent.setRequest((HttpServletRequest) req);
            SysContent.setResponse((HttpServletResponse) resp);*/
            // Filter 只是链式处理，请求依然转发到目的地址。
            chain.doFilter(req, resp);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}