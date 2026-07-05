package com.mentorship.food_delivery_app.auth.repository;

import com.mentorship.food_delivery_app.auth.models.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
}
