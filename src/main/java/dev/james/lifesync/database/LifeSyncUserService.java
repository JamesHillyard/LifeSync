package dev.james.lifesync.database;

import dev.james.lifesync.entity.LifeSyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LifeSyncUserService {

    private final LifeSyncUserRepository lifeSyncUserRepository;

    @Autowired
    public LifeSyncUserService(LifeSyncUserRepository lifeSyncUserRepository) {
        this.lifeSyncUserRepository = lifeSyncUserRepository;
    }

    public LifeSyncUser getUser(String username) {
        return lifeSyncUserRepository.findByUsername(username);
    }

    public void saveUser(LifeSyncUser user) {
        lifeSyncUserRepository.save(user);
    }

    public String getUserPassword(String username) {
        LifeSyncUser user = lifeSyncUserRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }
}
