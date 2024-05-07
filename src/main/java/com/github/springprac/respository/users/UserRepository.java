package com.github.springprac.respository.users;

public interface UserRepository {
    UserEntity findUserById(Integer userId);
}
