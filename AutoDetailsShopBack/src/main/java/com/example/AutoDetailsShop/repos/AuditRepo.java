package com.example.AutoDetailsShop.repos;

import com.example.AutoDetailsShop.domain.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepo extends JpaRepository<Audit, Long> {
}
