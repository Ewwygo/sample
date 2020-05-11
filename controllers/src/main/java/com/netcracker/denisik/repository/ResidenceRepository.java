package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.entity.Status;
import com.netcracker.denisik.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidenceRepository extends CrudRepository<Residence,Long> {

    Optional<Residence> findByUser(User user);
    boolean existsByUserAndStatus(User user, Status status);
}
