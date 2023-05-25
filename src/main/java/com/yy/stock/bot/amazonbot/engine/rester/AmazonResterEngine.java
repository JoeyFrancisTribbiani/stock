package com.yy.stock.bot.amazonbot.engine.rester;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.bot.helper.RestTemplateHelper;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class AmazonResterEngine extends ResterEngine {
    public AmazonResterEngine() {
        super();
    }
    @Override
    protected void initBotHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.set("accept-language", "zh-CN,zh;q=0.9");
        headers.set("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"macOS\"");
        headers.set("sec-fetch-dest", "document");
        headers.set("sec-fetch-mode", "navigate");
        headers.set("sec-fetch-site", "none");
        headers.set("sec-fetch-user", "?1");
        headers.set("upgrade-insecure-requests", "1");
        savedHeaders = headers;
    }

    public WebRequest initHtmlUnitRequest(String url) {

        WebRequest request = null;
        try {
            request = new WebRequest(new URL(url));
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
        request.setAdditionalHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        request.setAdditionalHeader("accept-language", "zh-CN,zh;q=0.9");
        request.setAdditionalHeader("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
        request.setAdditionalHeader("sec-ch-ua-mobile", "?0");
        request.setAdditionalHeader("sec-ch-ua-platform", "\"macOS\"");
        request.setAdditionalHeader("sec-fetch-dest", "document");
        request.setAdditionalHeader("sec-fetch-mode", "navigate");
        request.setAdditionalHeader("sec-fetch-site", "none");
        request.setAdditionalHeader("sec-fetch-user", "?1");
        request.setAdditionalHeader("upgrade-insecure-requests", "1");
        return request;
    }
    public Document getJSedHtmlDocument(String url){
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(true);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);//不启用ActiveX
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setDownloadImages(false);//不下载图片
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        webClient.getCookieManager().setCookiesEnabled(true);//开启cookies

        //设置请求头、cookies、代理
        var request = initHtmlUnitRequest(url);

        // 屏蔽HtmlUnit等系统 log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        HtmlPage rootPage = null;
        try {
            rootPage = webClient.getPage(request);
            //设置一个运行JavaScript的时间
            webClient.waitForBackgroundJavaScript(10000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        String html = rootPage.asXml();
        Document parse = Jsoup.parse(html);
        return parse;
    }

    @Override
    public String getStringResponse(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
//            RestTemplate restTemplate = MySpringUtil.getBean(RestTemplate.class);
            HttpEntity<String> entity = new HttpEntity<>(getBotHeaders());
            HttpEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            var body = response.getBody();

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    public String getGzipStringResponse(String url) {
        try {
//            RestTemplate restTemplate = new RestTemplate();
//            RestTemplate restTemplate = MySpringUtil.getBean(RestTemplate.class);
            HttpEntity<String> entity = new HttpEntity<>(getBotHeaders());
            HttpEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );
            var body = response.getBody();

            byte[] data = RestTemplateHelper.unGZip(new ByteArrayInputStream(body));

            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
