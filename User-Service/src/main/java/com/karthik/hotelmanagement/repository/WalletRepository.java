package com.karthik.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

}
