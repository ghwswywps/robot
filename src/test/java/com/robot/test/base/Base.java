package com.robot.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.robot.Application;

@ContextConfiguration(classes = {Application.class})
public class Base extends AbstractTestNGSpringContextTests {

}
