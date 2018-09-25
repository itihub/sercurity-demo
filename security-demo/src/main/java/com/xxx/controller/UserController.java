package com.xxx.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xxx.dto.UserDTO;
import com.xxx.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    @PostMapping
    @JsonView(UserDTO.UserSimpleView.class)
    @ApiOperation(value = "创建用户")
    public UserDTO create(@RequestBody UserDTO user) {
        log.info("request param {}", user);
        user.setId(1);
        return user;
    }

    /**
     * 修改用户
     *
     * @param user
     * @param errors
     * @return
     */
    @PutMapping("{id:\\d+}")
    @JsonView(UserDTO.UserSimpleView.class)
    @ApiOperation(value = "修改用户信息")
    public UserDTO update(@Valid @RequestBody UserDTO user, BindingResult errors) {
        log.info("request param {}", user);

        //打印参数校验错误信息
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String msg = String.format("{}:{}", fieldError.getField(), error.getDefaultMessage());
                System.out.println(msg);
            });
        }
        user.setId(1);
        return user;
    }


    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("{id:\\d+}")
    @ApiOperation(value = "删除用户信息")
    public void delete(@ApiParam(value = "用户ID") @PathVariable String id) {
        log.info("delete {}", id);
    }


    /**
     * 查询用户信息
     *
     * @param condition 查询对象
     * @param pageable  分页 Spring Data
     * @return
     */
    @GetMapping
    @JsonView(UserDTO.UserSimpleView.class)
    @ApiOperation(value = "条件查询用户信息")
    public List<UserDTO> query(UserQueryCondition condition, @PageableDefault(page = 2, size = 10, sort = "userName,asc") Pageable pageable) {

        log.info("request param {} pageable {}"
                , ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE)
                , pageable
        );

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(new UserDTO());
        userDTOList.add(new UserDTO());
        userDTOList.add(new UserDTO());
        return userDTOList;
    }

    /**
     * 查询用户详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id:\\d+}")
    @JsonView(UserDTO.UserDetailView.class)
    @ApiOperation(value = "查询指定用户信息详情")
    public UserDTO queryUserInfo(@PathVariable(name = "id", required = true) String id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Andy");
        return userDTO;
    }


}
