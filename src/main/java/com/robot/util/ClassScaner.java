package com.robot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.google.common.collect.Lists;

public class ClassScaner {
    private String basePackage;
    private ClassLoader cl;

    /**
     * 初始化
     * @param basePackage
     */
    public ClassScaner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();
    }
    public ClassScaner(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }
    /**
     *获取指定包下的所有字节码文件的全类名
     */
    public List<String> getFullyQualifiedClassNameList() throws IOException {
        return doScan(basePackage, new ArrayList<String>());
    }
    
    public List<Class<?>> getByAnnotation(Class<? extends Annotation> annotation) throws IOException {
        List<String> fullyQualifiedClassNameList = getFullyQualifiedClassNameList();
        fullyQualifiedClassNameList.removeIf(className -> {
            if(!className.contains("service"))
                return true;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
            }
            if (clazz == null)
                return true;
            return clazz.getAnnotation(annotation) == null;
        });
        return Lists.transform(fullyQualifiedClassNameList, input -> {
            try {
                return Class.forName(input);
            } catch (ClassNotFoundException e) {
            }
            return null;
        });
    }

    /**
     *doScan函数
     * @param basePackage
     * @param nameList
     * @return
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        String splashPath = dotToSplash(basePackage);
        URL url = cl.getResource(splashPath);   
        String filePath = getRootPath(url);
        List<String> names = null; 
        if (isJarFile(filePath)) {
            names = readFromJarFile(filePath, splashPath);
        } else {
            names = readFromDirectory(filePath);
        }
        for (String name : names) {
            if (isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                doScan(basePackage + "." + name, nameList);
            }
        }
        return nameList;
    }
    
    public List<String> doScanByUrl(URL url) throws IOException {
        List<String> nameList = new ArrayList<>();
        try (JarInputStream jarIn = new JarInputStream(url.openStream())){
            JarEntry entry = jarIn.getNextJarEntry();
            while (null != entry) {
                String name = entry.getName();
                if (isClassFile(name)) {
                    nameList.add(name);
                }
                entry = jarIn.getNextJarEntry();
            }
        }
        return Lists.transform(nameList, f -> trimExtension(f).replaceAll("/", "."));
    }
    
    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(trimExtension(shortName));
        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        try (JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath))) {
            JarEntry entry = jarIn.getNextJarEntry();
            List<String> nameList = new ArrayList<String>();
            while (null != entry) {
                String name = entry.getName();
                if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                    nameList.add(name);
                }

                entry = jarIn.getNextJarEntry();
            }

            return nameList;
        }
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    public String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    public String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');

        return trimmed.substring(splashIndex);
    }
}
