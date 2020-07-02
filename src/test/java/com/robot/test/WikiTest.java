package com.robot.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.robot.helper.ConfluenceHelper;
import com.robot.test.base.Base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

public class WikiTest extends Base {

    @Value("${robot.confluence.url}")
    private String url;
    @Autowired
    private ConfluenceHelper confluenceHelper;

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200), new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void testSaveMarkdown() throws InterruptedException, ExecutionException {

    }
}
