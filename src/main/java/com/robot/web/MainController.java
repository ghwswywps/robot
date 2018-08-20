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
        r.getText().setContent("增加MARKDOWN模板 temple:::![\\bmahua\\b](\\bmahua\\b-\\blogo\\b.\\bjpg\\b)\\\\bn\\b##\\bMaHua是什么\\b?\\\\bn一个在线编辑markdown文档的编辑器\\b\\\\bn\\b\\\\bn向Mac下优秀的markdown编辑器mou致敬\\b\\\\bn\\b\\\\bn\\b##\\bMaHua有哪些功能\\b？\\\\bn\\b\\\\bn\\b*\\s\\b方便的\\b`\\b导入导出\\b`\\b功能\\b\\\\bn\\b\\s\\s\\s\\s*\\s\\s\\b直接把一个markdown的文本文件拖放到当前这个页面就可以了\\b\\\\bn\\b\\s\\s\\s\\s*\\s\\s\\b导出为一个html格式的文件\\b，\\b样式一点也不会丢失\\b\\\\bn\\b*\\s\\b编辑和预览\\b`\\b同步滚动\\b`，\\b所见即所得\\b（\\b右上角设置\\b）\\\\bn\\b*\\s`\\bVIM快捷键\\b`\\b支持\\b，\\b方便vim党们快速的操作\\b\\s（\\b右上角设置\\b）\\\\bn\\b*\\s\\b强大的\\b`\\b自定义CSS\\b`\\b功能\\b，\\b方便定制自己的展示\\b\\\\bn\\b*\\s\\b有数量也有质量的\\b`\\b主题\\b`,\\b编辑器和预览区域\\b\\\\bn\\b*\\s\\b完美兼容\\b`\\bGithub\\b`\\b的markdown语法\\b\\\\bn\\b*\\s\\b预览区域\\b`\\b代码高亮\\b`\\\\bn\\b*\\s\\b所有选项自动记忆\\b\\\\bn\\b\\\\bn\\b##\\b有问题反馈\\b\\\\bn在使用中有任何问题\\b，\\b欢迎反馈给我\\b，\\b可以用以下联系方式跟我交流\\b\\\\bn\\b\\\\bn\\b*\\s\\b邮件\\b(\\bdev\\b.\\bhubo\\b#\\bgmail\\b.\\bcom\\b,\\s\\b把\\b#\\b换成\\b@)\\\\bn\\b*\\s\\bQQ\\b:\\s\\b287759234\\b\\\\bn\\b*\\s\\bweibo\\b:\\s[@\\b草依山\\b](\\bhttp\\b://\\bweibo\\b.\\bcom\\b/\\bihubo\\b)\\\\bn\\b*\\s\\btwitter\\b:\\s[@\\bihubo\\b](\\bhttp\\b://\\btwitter\\b.\\bcom\\b/\\bihubo\\b)\\\\bn\\b\\\\bn\\b##\\b捐助开发者\\b\\\\bn在兴趣的驱动下\\b,\\b写一个\\b`\\b免费\\b`\\b的东西\\b，\\b有欣喜\\b，\\b也还有汗水\\b，\\b希望你喜欢我的作品\\b，\\b同时也能支持一下\\b。\\\\bn当然\\b，\\b有钱捧个钱场\\b（\\b右上角的爱心标志\\b，\\b支持支付宝和PayPal捐助\\b），\\b没钱捧个人场\\b，\\b谢谢各位\\b。\\\\bn\\b\\\\bn\\b##\\b感激\\b\\\\bn感谢以下的项目\\b,\\b排名不分先后\\b\\\\bn\\b\\\\bn\\b*\\s[\\bmou\\b](\\bhttp\\b://\\bmouapp\\b.\\bcom\\b/)\\s\\\\bn\\b*\\s[\\bace\\b](\\bhttp\\b://\\bace\\b.\\bajax\\b.\\borg\\b/)\\\\bn\\b*\\s[\\bjquery\\b](\\bhttp\\b://\\bjquery\\b.\\bcom\\b)\\\\bn\\b\\\\bn\\b##\\b关于作者\\b\\\\bn\\b\\\\bn\\b```\\bjavascript\\b\\\\bn\\b\\s\\s\\bvar\\b\\s\\bihubo\\b\\s=\\s{\\\\bn\\b\\s\\s\\s\\s\\bnickName\\b\\s\\s:\\s\"\\b草依山\\b\",\\\\bn\\b\\s\\s\\s\\s\\bsite\\b\\s:\\s\"\\bhttp\\b://\\bjser\\b.\\bme\\b\"\\\\bn\\b\\s\\s}\\\\bn\\b``` el:::aaaaac title:::杭州天气");
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
