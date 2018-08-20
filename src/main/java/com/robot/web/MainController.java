package com.robot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.Body;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.handler.ContentHandler;

@Controller
public class MainController {
    @Autowired
    private TempleRepository templeRepository;
    @Autowired
    private ContentHandler contentHandler;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    String home(@RequestBody String requestStr) throws Exception {
        Request request = JSON.parseObject(requestStr, Request.class);
        Body body = contentHandler.getBodyByRequest(request);
        String res = JSON.toJSONString(body);
        System.out.println(res);
        return res;
    }
    
    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    @ResponseBody
    String test() {
        Request r = new Request();
        r.setText(new Text());
        r.getText().setContent("md测试123");
        Body body = contentHandler.getBodyByRequest(r);
        return JSON.toJSONString(body);
    }

    @RequestMapping(value = "/h2savetest", produces = "application/json; charset=utf-8")
    @ResponseBody
    String h2s() {
        Temple entity = new Temple();
        entity.setEl("test");
        entity.setTemple("temple");
        templeRepository.save(entity);
        return "ok";
    }

    @RequestMapping(value = "/h2gettest", produces = "application/json; charset=utf-8")
    @ResponseBody
    String h2g() {
        return templeRepository.findAll().toString();
    }

    @RequestMapping(value = "/deleteAll", produces = "application/json; charset=utf-8")
    @ResponseBody
    String h2DeletAll() {
        templeRepository.deleteAll();
        return "ok";
    }
    
    @RequestMapping(value = "/delete/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    String h2Delet(@PathVariable("id") Long id) {
        templeRepository.delete(id);
        return "ok";
    }

}
