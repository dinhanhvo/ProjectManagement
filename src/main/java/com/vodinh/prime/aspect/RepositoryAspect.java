package com.vodinh.prime.aspect;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class RepositoryAspect {

    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository.findBy*(..))")
    public Object handleFindByMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        // Lấy kết quả từ phương thức
        Object result = joinPoint.proceed();

        // Kiểm tra nếu kết quả là Optional rỗng
        if (result instanceof Optional<?> optional && optional.isEmpty()) {
            // Lấy tham số đầu vào (ví dụ: ID)
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                throw new EntityNotFoundException("Entity with ID " + args[0] + " not found!");
            } else {
                throw new EntityNotFoundException("Entity not found!");
            }
        }

        return result;
    }
}
