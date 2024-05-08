package com.github.springprac.respository.users;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJdbcTemplateDao implements UserRepository{

    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<UserEntity> userEntityRowMapper = ((rs, rowNums) ->
            new UserEntity(
                    rs.getInt("user_id"),
                    rs.getNString("user_name"),
                    rs.getNString("like_travel_place"),
                    rs.getNString("phone_num")
            ));

    @Override
    public Optional<UserEntity> findUserById(Integer userId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", userEntityRowMapper, userId));
        }
        catch (Exception e){ // 문제 발생 시 catch 에서 잡히면 , empty 취급 = null 취급 하겠다는 의미
            return Optional.empty();
        }
    }
}
