package com.robot.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import com.robot.helper.ConfluenceHelper;
import com.robot.test.base.Base;

public class WikiTest extends Base {
    @Autowired
    private ConfluenceHelper confluenceHelper;

    @Test
    public void testSaveMarkdown() {
        confluenceHelper.updateContent("12638872", "123", "123234");
    }
}
