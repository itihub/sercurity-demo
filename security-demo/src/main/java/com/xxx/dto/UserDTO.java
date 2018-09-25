package com.xxx.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.xxx.annotation.validator.BaseConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @Package: com.xxx.dto
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 9:45
 * @UpdateUser: Revised author
 * @UpdateDate: 2018/8/25 9:45
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Data
public class UserDTO {

    public interface UserSimpleView {
    }

    ;

    public interface UserDetailView extends UserSimpleView {
    }

    ;

    @JsonView(UserSimpleView.class)
    private Integer id;

    @JsonView(UserSimpleView.class)
    @BaseConstraint
    private String userName;

    @JsonView(UserDetailView.class)
    @NotBlank(message = "密码不能为空")
    private String userPassword;

    @JsonView(UserSimpleView.class)
    @Past(message = "生日必须合法")
    private Date birthday;


}
