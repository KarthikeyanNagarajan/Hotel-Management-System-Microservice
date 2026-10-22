package com.karthik.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Modifying 
    @Query("UPDATE wallet w SET w.balance = w.balance + :delta WHERE w.user.id = :userId AND w.balance + :delta >= 0")
    int adjustBalanceByUserId(@Param("userId") int userId, @Param("delta") int delta);
}
