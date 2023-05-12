package com.chientran.application.repository;

import com.chientran.application.entity.User;
import com.chientran.application.model.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM users u WHERE u.full_name LIKE CONCAT('%',?1,'%') " +
            "AND u.phone LIKE CONCAT('%',?2,'%') " +
            "AND u.email LIKE CONCAT('%',?3,'%') ",nativeQuery = true)
    Page<User> adminListUserPages(String fullName, String phone, String email, Pageable pageable);

    @Query(value = "select * from users where email = \"admin@gmail.com\"",nativeQuery = true)
    User findByEmail(String username);
}
