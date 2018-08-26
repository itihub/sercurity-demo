package com.xxx.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Description: wiremock 配置 相当于wiremock客户端
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 12:46
 */
public class MockServer {

    public static void main(String[] args) throws IOException {
        //配置主机及端口
        WireMock.configureFor("127.0.0.1",8090);
        //清空历史配置
        WireMock.removeAllMappings();



        //处理请求
        mock("/order/1", "01.txt");
    }

    /**
     * 处理请求抽取方法
     * @param url 请求url
     * @param file   返回结果
     */
    private static void mock(String url, String file) throws IOException {
        //获取本地文件
        ClassPathResource resource = new ClassPathResource(String.format("response/%s",file));

        String content = StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray()
                ,"\n");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url))
                        .willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
