package gsm.gsmjava.global.config.async;

import gsm.gsmjava.global.error.GlobalAsyncExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final GlobalAsyncExceptionHandler globalAsyncExceptionHandler;

    @Override
    @Bean(name = "asyncTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("Async-Task");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return globalAsyncExceptionHandler;
    }
}
