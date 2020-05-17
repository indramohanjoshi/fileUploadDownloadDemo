package com.example.fileUploadDownloadDemo;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.fileUploadDownloadDemo.config.MyListenableFutureCallback;

@SpringBootApplication
public class FileUploadDownloadDemoApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadDownloadDemoApplication.class, args);
	}
	
	@Override
    public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }
	
	@Bean
    public ListenableFutureCallback<String> taskListenableCallback () {
        return new MyListenableFutureCallback();
    }
	
}
