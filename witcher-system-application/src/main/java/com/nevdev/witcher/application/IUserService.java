package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.User;

public interface IUserService extends IService<User> {
    User find(String value);

    User findByUsernameAndPassword(String username, String password);

    User findByUsernameOrEmail(String username, String email);

    User findUserByEmail(String email);

    User findUserByResetToken(String resetToken);
}
