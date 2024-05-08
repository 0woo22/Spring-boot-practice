package com.github.springprac.respository.users;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findUserById(Integer userId);
    // Optional 로 바꾸면 Null 값일 수 있다는 것 의미
}
