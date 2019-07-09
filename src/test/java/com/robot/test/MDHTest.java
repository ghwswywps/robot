package com.robot.test;

import java.io.File;
import java.io.IOException;

import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

import com.overzealous.remark.Remark;
import com.robot.entity.md.MarkDown2HtmlWrapper;
import com.robot.entity.md.MarkdownEntity;
import com.robot.test.base.Base;

public class MDHTest extends Base{
    
    @Test
    public void markdown2html() throws IOException {
        File file = new ClassPathResource("md/README.md").getFile();
        MarkdownEntity html = MarkDown2HtmlWrapper.ofFile(file.getPath());
        System.out.println(html.toString());
        File htmlFile = new File("/Users/flaki/Downloads/test.html");
        if (!htmlFile.exists()) {
            htmlFile.createNewFile();
        }
        FileUtil.writeAsString(htmlFile, html.toString());
    }
    
    @Test
    public void html2md() throws IOException {
        Remark remark = new Remark();
        String convert = remark.convert(new File("/Users/flaki/Downloads/test.html"));
        System.out.println(convert);
    }

}
