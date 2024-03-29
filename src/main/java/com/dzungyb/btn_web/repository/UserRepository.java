package com.dzungyb.btn_web.repository;

import com.dzungyb.btn_web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);

    boolean existsByEmail(String email);

}
