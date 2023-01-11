package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String resultString = "";
        boolean result = true;

        String token = request.getHeader("Authorization");

        try {
            JwtUtils.parseToken(token);
        } catch (ArithmeticException e) {
            resultString = "ArithmeticException";
            result = false;
        } catch (SignatureVerificationException e) {
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token expire";
            result = false;
        } catch (Exception e) {
            resultString = "token error";
            result = false;
        }

        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(ResponseResult.fail(resultString).toString());
        }

        return result;
    }
}
