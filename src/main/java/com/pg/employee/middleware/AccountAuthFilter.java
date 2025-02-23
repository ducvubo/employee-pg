package com.pg.employee.middleware;

import com.pg.employee.utils.ApiUtils;
import com.pg.employee.utils.IBackendRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class AccountAuthFilter extends OncePerRequestFilter {

    @Value("http://localhost:5001/api/v1")
    private String urlServiceBack;

    @Autowired
    private final ObjectMapper objectMapper;

    public AccountAuthFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy token từ headers
        String accessTokenRtr = extractToken(request, "x-at-rtr");
        String refreshTokenRtr = extractToken(request, "x-rf-rtr");

        String accessTokenEpl = extractToken(request, "x-at-epl");
        String refreshTokenEpl = extractToken(request, "x-rf-epl");

        String accessToken = (accessTokenRtr != null) ? accessTokenRtr : accessTokenEpl;
        String refreshToken = (refreshTokenRtr != null) ? refreshTokenRtr : refreshTokenEpl;

        if (accessToken == null || refreshToken == null) {
//            throw new UnauthorizedCodeException("Token không hợp lệ 99", -10);
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "Token không hợp lệ 99", -10);
            return;
        }

        // Xác định key header gửi đi
        String keyAccess = (accessTokenEpl != null) ? "x-at-epl" : "x-at-rtr";
        String keyRefresh = (refreshTokenEpl != null) ? "x-rf-epl" : "x-rf-rtr";

        try {
            // Gửi request xác thực đến service backend
            IBackendRes<Account> responseData = ApiUtils.sendPostRequest(
                    urlServiceBack + "/restaurants/authen",
                    keyAccess, "Bearer " + accessToken,
                    keyRefresh, "Bearer " + refreshToken,
                    objectMapper
            );


            if (responseData.getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                //throw new UnauthorizedCodeException(responseData.getMessage(), -10);
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), responseData.getMessage(), -10);
            }
            if (responseData.getStatusCode() == HttpStatus.FORBIDDEN.value()) {
//                throw new ForbiddenException(responseData.getMessage());
                writeErrorResponse(response, HttpStatus.FORBIDDEN.value(), responseData.getMessage(), -10);
            }
            if (responseData.getStatusCode() != HttpStatus.CREATED.value()) {
//                throw new UnauthorizedCodeException("Token không hợp lệ 98", -10);
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "Token không hợp lệ 98", -10);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.convertValue(responseData.getData(), Account.class);

            SecurityContextHolder.getContext().setAuthentication(new AccountAuthenticationToken(account));

            filterChain.doFilter(request, response);

        } catch (Exception e) {
//            throw new UnauthorizedCodeException("Token không hợp lệ 97", -10);
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "Token không hợp lệ 97", -10);
        }
    }

    private String extractToken(HttpServletRequest request, String headerName) {
        String headerValue = request.getHeader(headerName);
        return (headerValue != null && headerValue.startsWith("Bearer ")) ? headerValue.substring(7) : null;
    }

    private void writeErrorResponse(HttpServletResponse response, int status, String message, int errorCode) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(Map.of(
                "statusCode", status,
                "message", message,
                "code", errorCode
        ));

        response.getWriter().write(jsonResponse);
        response.flushBuffer();
    }

}
