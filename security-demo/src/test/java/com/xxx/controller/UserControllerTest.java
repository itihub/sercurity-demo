package com.xxx.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.context.embedded.WebApplicationContextServletContextAwareProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
       String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("userName","珍妮")
                .param("age","18")
                .param("ageTo", "20")
                .param("size","10")
                .param("page","3")
                .param("sort","age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

       log.info("response {}",result);
    }

    @Test
    public void queryUserInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Andy"))
                .andReturn().getResponse().getContentAsString();

        log.info("response {}", result);
    }

    @Test
    public void queryUserInfoFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {

        String content = "{\"userName\":\"Tom\",\"userPasswork\":\"123456\",\"birthday\":"+new Date().getTime()+"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        log.info("response {}", result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {

        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        String content = "{\"id\":\"1\",\"userName\":\"Tom\",\"userPasswork\":\"123456\",\"birthday\":"+date.getTime()+"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        log.info("response {}", result);

    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file", "text.txt", "multipart/form-data", "hellp upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        log.info("response {}", result);
    }
}