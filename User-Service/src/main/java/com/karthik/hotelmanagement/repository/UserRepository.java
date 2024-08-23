package com.karthik.hotelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String name);

}
