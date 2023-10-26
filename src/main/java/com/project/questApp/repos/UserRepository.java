package com.project.questApp.repos;

import com.project.questApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

}
