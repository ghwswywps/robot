package com.robot.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.robot.entity.confluence.Body;
import com.robot.entity.confluence.ConfluenceRequest;
import com.robot.entity.confluence.Storage;
import com.robot.entity.confluence.Version;
import com.robot.util.ConfluenceUtil;

@Component
public class ConfluenceHelper {
    @Value("${robot.confluence.authorization}")
    private String authorization;

    @Value("${robot.confluence.url}")
    private String url;
    
    public static final String CONTENT = "content/";

    public void updateContent(String contectId, String title, String text) {
        ConfluenceRequest confluenceRequest = new ConfluenceRequest();
        confluenceRequest.setId(contectId);
        confluenceRequest.setTitle(title);
        confluenceRequest.setType("page");
        Body body = new Body();
        Storage storage = new Storage();
        storage.setRepresentation("storage");
        storage.setValue(text);
        body.setStorage(storage);
        Version version = new Version();
        version.setNumber(5);
        confluenceRequest.setVersion(version);
        confluenceRequest.setBody(body);
        ConfluenceUtil.put(url + CONTENT + contectId, authorization,
                confluenceRequest);
    }
    
    public void getContentById(String contectId) {
    }
    
    public int findNextVersion(String contectId) {
        return 1;
    }
}
