package tw.com.ispan.ted.utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        urlPatterns = {
                "/home",
                "/backOfficelogout"
        }
)
public class AccessFilter extends HttpFilter {

    public void doFilter(HttpServletRequest request,
                         HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(request.getSession().getAttribute("login") == null) {
            response.sendRedirect("/");   //如果抓不到login屬性的session就把人送回登入頁面
        }
        else {
            chain.doFilter(request, response);   //如果抓到就讓request過來&response回去
        }
    }
}