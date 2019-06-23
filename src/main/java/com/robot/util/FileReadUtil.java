package com.robot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.aspectj.util.FileUtil;

public class FileReadUtil {

    public static InputStream getStreamByFileName(String path) throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }

    public static String readAll(String string) throws IOException {
        return FileUtil.readAsString(new File(string));
    }

}
