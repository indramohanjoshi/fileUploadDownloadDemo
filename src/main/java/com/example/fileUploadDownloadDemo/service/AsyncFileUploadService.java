package com.example.fileUploadDownloadDemo.service;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AsyncFileUploadService {

	private static final Logger LOG = LoggerFactory.getLogger(AsyncFileUploadService.class);

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Autowired
	private ListenableFutureCallback<String> threadListenableCallback;
	
	@Value("${manual.file.processing.delay.in.milli :4000}")
	private long manualProcessingDelay;

	public void uploadCSVFileAsync(final MultipartFile file) throws IOException {
		LOG.info("inside asynch service file size :: {}", file.getSize());
		ListenableFuture<String> listenableFuture = executor.submitListenable(getTask(file));
		listenableFuture.addCallback(threadListenableCallback);
		try {
			LOG.info(
					"Manual delay of :: {} mili second, so that main request should not commit respond before async task start.", manualProcessingDelay);
			Thread.sleep(manualProcessingDelay);
		} catch (InterruptedException e) {
			LOG.error("Manual delay of :: {} mili seconds completed.", manualProcessingDelay);
		}
	}

	private Callable<String> getTask(final MultipartFile file) {
		return () -> {
			LOG.info("inside getTask file size :: {}", file.getSize());
			fileUploadService.uploadCSVFile(file, false);
			return file.getOriginalFilename();
		};
	}

}