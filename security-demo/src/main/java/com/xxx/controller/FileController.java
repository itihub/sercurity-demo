package com.xxx.controller;

import com.xxx.dto.FileInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 9:22
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 文件 存放路径
     */
    private final String folder = "C:/Users/Public/Desktop/";

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping
    @ApiOperation(value = "文件上传")
    public FileInfo upload(MultipartFile file) throws IOException {

        log.info("request param -  name :{} file name : {} file size : {}"
                , file.getName(), file.getOriginalFilename(), file.getSize());

        File localFile = new File(folder, UUID.randomUUID().toString() + ".txt");

        file.transferTo(localFile);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath(localFile.getAbsolutePath());

        return fileInfo;
    }

    /**
     * 文件下载
     *
     * @param id
     * @param request
     * @param response
     */
    @GetMapping("{id}")
    @ApiOperation(value = "文件下载")
    public void download(@ApiParam(value = "下载ID") @PathVariable String id
            , HttpServletRequest request, HttpServletResponse response) {
        File file = new File(folder, id + ".txt");

        // jdk7 新语法 写在 try() 里，无需手动写 finally{}块来关流，jdk自动关流

        try (
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream();
        ) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=text.txt");

            //将文件的输入流拷贝到输出流
            IOUtils.copy(inputStream, outputStream);
            //下载
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
