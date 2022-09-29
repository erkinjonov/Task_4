package com.itransition.usermanagementsystem.repository;

import com.itransition.usermanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void deleteById(Long id);

    @Query(value = "select * from users order by id", nativeQuery = true)
    List<User> findAllUsersById();

//

}
