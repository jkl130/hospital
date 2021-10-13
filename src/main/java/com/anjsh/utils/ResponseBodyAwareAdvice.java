package com.anjsh.utils;

import com.anjsh.dto.MultiResponse;
import com.anjsh.dto.PageResponse;
import com.anjsh.dto.Response;
import com.anjsh.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @author anjsh
 * @date 2021/4/12 14:36
 */
@ControllerAdvice
@Slf4j
public class ResponseBodyAwareAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !(returnType.getContainingClass().isAnnotationPresent(IgnoreAware.class) || returnType.hasMethodAnnotation(IgnoreAware.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();

        if (status != HttpServletResponse.SC_OK) {
            return Response.buildFailure(HttpStatus.valueOf(status), String.valueOf(body));
        }

        if (Void.class.isAssignableFrom(returnType.getParameterType())) {
            return Response.buildSuccess();
        }

        if (IPage.class.isAssignableFrom(returnType.getParameterType())) {
            IPage<?> page = (IPage<?>) body;
            return PageResponse.of(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent());
        }

        if (Collection.class.isAssignableFrom(returnType.getParameterType())) {
            return MultiResponse.of((Collection<?>) body);
        }

        return SingleResponse.of(body);
    }
}
