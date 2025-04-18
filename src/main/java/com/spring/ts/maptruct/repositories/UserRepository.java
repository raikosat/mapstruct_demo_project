package com.spring.ts.maptruct.repositories;

import java.util.Optional;
import com.spring.ts.maptruct.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUserId(Long userId);

}
