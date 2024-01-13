package dev.james.lifesync.database;

import dev.james.lifesync.entity.LifeSyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeSyncUserRepository extends JpaRepository<LifeSyncUser, Integer> {
    LifeSyncUser findByUsername(String username);
}
