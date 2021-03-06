package com.applepieme.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 权限拦截器
 * 未登录的部分操作无法进行
 *
 * @author applepieme@yeah.net
 * @date 2020/6/29 11:13
 */
@WebFilter("*.page")
public class PowerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 把ServletRequest对象强制转换成HttpServletRequest对象
        HttpServletRequest req = (HttpServletRequest) request;
        // 获取请求地址
        String path = req.getServletPath();
        // 管理员或用户登录 则不拦截
        if (req.getSession().getAttribute("userCart") != null ||
                req.getSession().getAttribute("manage") != null) {
            chain.doFilter(request, response);
            // 部分页面不拦截
        } else if ("/login.page".equals(path) || "/index.page".equals(path) || "/signup.page".equals(path) ||
                "/front_welcome.page".equals(path)) {
            chain.doFilter(request, response);
            // 不符合条件 跳转到登录页面
        } else {
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
