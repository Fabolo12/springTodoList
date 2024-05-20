package org.example.springprojecttodo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class AdderAfterReturnAspect {

    @Value("#{new Double('${maximum-time-endpoint}')}")
    private double maxTime;

    @Around("@annotation(org.example.springprojecttodo.annotation.LogTime)")
    public Object logTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object value = proceedingJoinPoint.proceed();
        stopWatch.stop();
        final double totalTime = stopWatch.getTotalTime(TimeUnit.MILLISECONDS);

        if (totalTime > maxTime) {
            log.warn("Method {}.{} took {} ms / max {}",
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(),
                    totalTime,
                    maxTime
            );
        } else {
            log.info("Method {}.{} took {} ms",
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(),
                    totalTime
            );
        }

        return value;
    }
}
