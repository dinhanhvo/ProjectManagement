package com.vodinh.prime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableHandlerMethodArgumentResolver() {
            @Override
            public Pageable resolveArgument(
                    MethodParameter parameter,
                    @Nullable ModelAndViewContainer mavContainer,
                    NativeWebRequest webRequest,
                    @Nullable WebDataBinderFactory binderFactory) {

                Pageable pageable = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

                // Chuyển pageIndex 1-based về 0-based
                if (pageable.getPageNumber() > 0) {
                    return PageRequest.of(
                            Math.max(pageable.getPageNumber() - 1, 0),
                            pageable.getPageSize(),
                            pageable.getSort());
                }
                return pageable;
            }
        });
    }

}
