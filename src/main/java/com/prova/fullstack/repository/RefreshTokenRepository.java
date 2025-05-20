package com.prova.fullstack.repository;

import com.prova.fullstack.entity.RefreshToken;
import com.prova.fullstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
    void deleteByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    void deleteByExpiryDateBefore(Instant now);


}
