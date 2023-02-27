package com.wbt.learnsp.user;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserAccount, Long> {
    UserAccount findByUsername(final String username);
}
