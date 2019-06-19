package com.robot.handler;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.stream.ChatStream;
import com.robot.stream.StaticText;
import com.robot.stream.StreamCenter;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class ContentHandler {
    public Body getBodyByRequest(Request request) {
        Body body = new Body();
        try {
            body = handleBody(request);
        } catch (Exception e) {
            body.setMsgtype("text");
            body.setText(Text.builder().content(e.getMessage()).build());
            log.error(e, e);
        }
        body.setAt(At.builder().atDingtalkIds(Arrays.asList(request.getSenderId())).build());
        return body;
    }
    

    private Body handleBody(Request request) throws Exception {
        String content = request.getText().getContent();
        if (StringUtils.isEmpty(content.trim())) {
            request.getText().setContent(StaticText.DEFAULT_ORDER);
            content = request.getText().getContent();
        }
        List<ChatStream> streamList = StreamCenter.getStream();
        
        Body result = StaticText.EMPTY_BODY;
        for (ChatStream chatStream : streamList) {
            Body handle = chatStream.handle(content, request);
            if (!StaticText.EMPTY_BODY.equals(handle)) {
                result = handle;
                break;
            }
        }
        return result;
    }

    

}
