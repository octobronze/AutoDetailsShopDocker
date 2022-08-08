package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findRefreshTokenByToken(String token);

    Optional<RefreshToken> findRefreshTokenByUser_Username(String username);
}
