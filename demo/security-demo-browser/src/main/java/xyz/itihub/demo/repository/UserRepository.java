package xyz.itihub.demo.repository;

import xyz.itihub.demo.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 数据库操作类
 * @Author: Jimmy
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return
     */
    UserEntity findByUsername(String username);

    UserEntity findByMobile(String mobile);

    UserEntity findByEmail(String email);
}
