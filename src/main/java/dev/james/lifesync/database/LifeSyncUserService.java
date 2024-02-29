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

    public LifeSyncUser getUser(String email) {
        return lifeSyncUserRepository.findByEmail(email);
    }

    public void saveUser(LifeSyncUser user) {
        lifeSyncUserRepository.save(user);
    }

    public String getUserPassword(String email) {
        LifeSyncUser user = lifeSyncUserRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }
}
