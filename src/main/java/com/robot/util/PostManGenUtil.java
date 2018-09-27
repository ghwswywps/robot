package com.robot.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.ele.napos.vine.descriptor.annotation.soa.SoaService;

public class PostManGenUtil {
    public String gen(String collName, int port) throws IOException {
        ClassScaner classScaner = new ClassScaner("me");
        List<Class<?>> serviceList = classScaner.getByAnnotation(SoaService.class);
        return start(collName, port, serviceList);
    }
    
    public String gen(String url, String collName, int port ,List<String> urlList) throws Exception {
        List<Class<?>> serviceList = getClassFromUrl(url, SoaService.class, urlList);
        return start(collName, port, serviceList);
    }

    private String start(String collName, int port, List<Class<?>> serviceList) {
        PostManCollection coll = new PostManCollection();
        Info info = new Info();
        info.set_postman_id("gen_by_genutil");
        info.setName(collName);
        info.setSchema("https://schema.getpostman.com/json/collection/v2.0.0/collection.json");
        coll.setInfo(info);
        List<Item> item = new ArrayList<>();
        serviceList.forEach(serviceClass -> {
            Item itemSingle = new Item();
            itemSingle.setName(serviceClass.getSimpleName());
            item.add(itemSingle);
            List<Item> childItemList = new ArrayList<>();
            Method[] methods = serviceClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Item methodItem = new Item();
                childItemList.add(methodItem);
                Method method = methods[i];
                methodItem.setName(method.getName());
                methodItem.setResponse(Arrays.asList());
                Request request = new Request();
                methodItem.setRequest(request);
                request.setMethod("POST");
                request.setUrl("http://localhost:" + port + "/rpc");
                request.setDescription("");
                request.setHeader(getHeader());
                Body body = new Body();
                request.setBody(body);
                body.setMode("raw");
                Raw raw = new Raw();
                raw.setVer("1.0");
                raw.setIface(serviceClass.getName());
                raw.setMethod(method.getName());
                Parameter[] parameters = method.getParameters();
                Map<String, String> args = new LinkedHashMap<>();
                for (int j = 0; j < parameters.length; j++) {
                    args.put(parameters[j].getName(), "");
                }
                raw.setArgs(args);
                body.setRaw(JsonFormatter.JsonFormart(JSON.toJSONString(raw)));

            }
            itemSingle.setItem(childItemList);
        });
        coll.setItem(item);
        return JSON.toJSONString(coll);
    }

    private List<Header> getHeader() {
        return Arrays.asList(new Header("Cache-Control", "no-cache"), new Header("Content-Type", "application/json"),
                new Header("Invocation-Protocol", "Napos-Communication-Protocol-2"));
    }

    private List<Class<?>> getClassFromUrl(String url, Class<? extends Annotation> annotationClazz, List<String> urlList) throws Exception {
        List<Class<?>> res = new ArrayList<>();
        List<URL> relayList = new ArrayList<>();
        URL urll = new URL(url);
        relayList.add(urll);
        if (!CollectionUtils.isEmpty(urlList)) {
            relayList.addAll(Lists.transform(urlList, arg0 -> {
                try {
                    return new URL(arg0);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                return null;
            }));
        }
        URL[] urlArray = new URL[relayList.size()];
        try (URLClassLoader urlClassLoader = new URLClassLoader(relayList.toArray(urlArray))) {
            relayList.forEach(allUrl -> {
                try {
                    List<String> AllList = new ClassScaner("").doScanByUrl(allUrl);
                    AllList.forEach(l -> {
                        try {
                            urlClassLoader.loadClass(l);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            List<String> clazzStrList = new ClassScaner("").doScanByUrl(urll);
            clazzStrList.forEach(clazzStr -> {
                try {
                    Class<?> loadClass = urlClassLoader.loadClass(clazzStr);
                    Annotation annotation = loadClass.getAnnotation(annotationClazz);
                    if (annotation != null)
                        res.add(loadClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return res;
    }
    
    public String getPostManConfigByMavenXml(String xml, int port, String relayOn) throws Exception {
        MavenConfig mavenConfig = new PostManGenUtil().getMavenConfig(xml);
        String url_pre = getHttpUrl(mavenConfig);
        List<String> urlList = null;
        List<String> relayUrlList = null;
        if (!StringUtils.isEmpty(relayOn)) {
            urlList = new ArrayList<>(Arrays.asList(relayOn.split("-----")));
            relayUrlList = urlList.stream().map(urlsingle -> {
                MavenConfig _mavenConfig = null;
                try {
                    _mavenConfig = new PostManGenUtil().getMavenConfig(urlsingle);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                urlsingle = getHttpUrl(_mavenConfig);
                return urlsingle;
            }).collect(Collectors.toList());
        }
        return gen(url_pre, mavenConfig.artifactId, port, relayUrlList);
    }
    
    public String getHttpUrl (MavenConfig mavenConfig) {
        String url_pre = "http://maven.dev.elenet.me/nexus/content/repositories/ele-napos-release/";
        url_pre += mavenConfig.groupId.replaceAll("\\.", "/") + "/";
        url_pre += mavenConfig.artifactId + "/";
        url_pre += mavenConfig.version + "/";
        url_pre += mavenConfig.artifactId + "-" + mavenConfig.version + ".jar";
        return url_pre;
    }
    
    public MavenConfig getMavenConfig(String xml) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
        Element rootElement = document.getRootElement();
        String groupId = rootElement.element("groupId").getText();
        String artifactId = rootElement.element("artifactId").getText();
        String version = rootElement.element("version").getText();
        MavenConfig cfg = new MavenConfig(groupId, artifactId, version);
        return cfg;
    }
    
    @Data
    @AllArgsConstructor
    public static class MavenConfig {
        private String groupId;
        private String artifactId;
        private String version;
    }

}
