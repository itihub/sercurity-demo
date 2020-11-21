package xyz.itihub.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import xyz.itihub.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import xyz.itihub.demo.dto.UserDTO;
import xyz.itihub.demo.dto.UserQueryCondition;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final ProviderSignInUtils providerSignInUtils;

    /**
     * app注册用户工具类
     */
//    @Autowired
//    private AppSingUpUtils appSingUpUtils;

    private final SecurityProperties securityProperties;

    /**
     * 用户注册
     * @param user
     * @param request
     */
    @PostMapping("/register")
    public void register(User user, HttpServletRequest request){

        //不管是注册用户还是绑定用户，都会拿到用户唯一标识
        String userId = user.getUsername();
        // TODO: 2018/10/13 注册或绑定业务逻辑
        //通过工具类将用户唯一标识传给spring social
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));

//        appSingUpUtils.doPostSingUp(new ServletWebRequest(request), userId);
    }

    /**
     * 查看当前用户
     * @param user
     * @return
     */
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @GetMapping("/app/me")
    public Object getCurrentUserApp(Authentication user, HttpServletRequest request) throws Exception {

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        //解析jwt token  注册密签 密钥
        Claims claims = Jwts.parser().setSigningKey(securityProperties.oauth2.getSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        String company = (String) claims.get("company");

        log.info("----->{}", company);

        return user;
    }

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
    @ApiOperation(value = "修改用户信息")
    @PutMapping("{id:\\d+}")
    @JsonView(UserDTO.UserSimpleView.class)
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
    @ApiOperation(value = "删除用户信息")
    @DeleteMapping("{id:\\d+}")
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
    @ApiOperation(value = "条件查询用户信息")
    @GetMapping
    @JsonView(UserDTO.UserSimpleView.class)
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
    @ApiOperation(value = "查询指定用户信息详情")
    @GetMapping("{id:\\d+}")
    @JsonView(UserDTO.UserDetailView.class)
    public UserDTO queryUserInfo(@PathVariable(name = "id", required = true) String id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Andy");
        return userDTO;
    }


}