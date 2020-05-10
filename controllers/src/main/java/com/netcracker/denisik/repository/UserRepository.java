package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User getByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsById(long id);
}
