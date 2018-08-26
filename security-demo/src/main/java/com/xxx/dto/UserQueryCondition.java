package com.xxx.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Package: com.xxx.dto
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 9:48
 * @UpdateUser: Revised author
 * @UpdateDate: 2018/8/25 9:48
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Data
public class UserQueryCondition {

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "查询起始年龄")
    private Integer age;

    @ApiModelProperty(value = "查询起止年龄")
    private Integer ageTo;

}
