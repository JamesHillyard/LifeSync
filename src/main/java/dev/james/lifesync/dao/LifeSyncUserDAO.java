package dev.james.lifesync.dao;

import dev.james.lifesync.model.LifeSyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeSyncUserDAO extends JpaRepository<LifeSyncUser, Integer> {
    LifeSyncUser findByUsername(String username);
}
