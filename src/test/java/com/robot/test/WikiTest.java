package com.robot.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentBody;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Version;
import com.atlassian.confluence.api.model.content.id.ContentId;
import com.atlassian.confluence.rest.client.RemoteContentServiceImpl;
import com.atlassian.confluence.rest.client.RestClientFactory;
import com.atlassian.confluence.rest.client.authentication.AuthenticatedWebResourceProvider;
import com.atlassian.util.concurrent.Promise;
import com.google.common.util.concurrent.MoreExecutors;
import com.robot.helper.ConfluenceHelper;
import com.robot.test.base.Base;
import com.sun.jersey.api.client.Client;

public class WikiTest extends Base {

    @Value("${robot.confluence.url}")
    private String url;
    @Autowired
    private ConfluenceHelper confluenceHelper;

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200), new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void testSaveMarkdown() throws InterruptedException, ExecutionException {
        Client client = RestClientFactory.newClient();
        String path = "";
        AuthenticatedWebResourceProvider provider = new AuthenticatedWebResourceProvider(client, url, path);
        provider.setAuthContext("yang.wu", "!@Ming0924".toCharArray());
        RemoteContentServiceImpl remoteContentServiceImpl = new RemoteContentServiceImpl(provider,
                MoreExecutors.listeningDecorator(threadPoolExecutor));
        Optional<Content> optional = remoteContentServiceImpl.find().withId(ContentId.of(12638872L)).fetch().get();
        Content x = optional.get();
        System.err.println(x);
        Version version = x.getVersion();
        Version next = version.nextBuilder().build();
        Map<ContentRepresentation, ContentBody> body = new HashMap<>();
        body.put(ContentRepresentation.STORAGE,
                ContentBody.contentBodyBuilder()
                .value("haha")
                .representation(ContentRepresentation.STORAGE)
                .build());
        Content newContent = Content
                .builder()
                .version(next)
                .type(ContentType.PAGE)
                .title("hello")
                .id(ContentId.of(12638872L))
                .body(body)
                .build();
        Version version2 = newContent.getVersion();
        System.err.println(version2);
        Promise<Content> update = remoteContentServiceImpl.update(newContent);
        System.err.println(update.get());
    }
}
