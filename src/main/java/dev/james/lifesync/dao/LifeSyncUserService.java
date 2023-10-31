package dev.james.lifesync.dao;

import dev.james.lifesync.model.LifeSyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LifeSyncUserService {

    private final LifeSyncUserDAO lifeSyncUserDAO;

    @Autowired
    public LifeSyncUserService(LifeSyncUserDAO lifeSyncUserDAO) {
        this.lifeSyncUserDAO = lifeSyncUserDAO;
    }

    public LifeSyncUser getUser(String username) {
        return lifeSyncUserDAO.findByUsername(username);
    }

    public void saveUser(LifeSyncUser user) {
        lifeSyncUserDAO.save(user);
    }

    public String getUserPassword(String username) {
        LifeSyncUser user = lifeSyncUserDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }
}
