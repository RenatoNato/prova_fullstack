package com.prova.fullstack.repository;

import com.prova.fullstack.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByUserId(Long userId, Pageable pageable);

}
