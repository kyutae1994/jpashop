package jpabook.jpashop.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {  // request 요청이 올 때 동작

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰 : cos 이걸 만들어줘야 함. id, pw 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 그걸 응댑을 해준다.
        // 요청할때마다 header에 Authorization에 value 값으로 토큰을 가지고 오면
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 됨. (RSA, HS256)
        if (req.getMethod().equals("POST")) {
            String authorization = req.getHeader("Authorization");
            System.out.println(authorization);
            System.out.println("필터");
            chain.doFilter(request, response);

            if (authorization.equals("cos")) {
                chain.doFilter(req, res);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }
    }
}
