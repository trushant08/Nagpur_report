/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.UserDao;
import cc.altius.model.Password;
import cc.altius.model.Role;
import cc.altius.model.User;
import cc.altius.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shrutika
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * Method to update the login failure count from username
     *
     * @param username username is used to get the user of whom you want to
     * update the failedCount
     * @return Returns the updated login failedCount of a user by calling a
     * method from userDao
     */
    @Override
    public int incrementFailedCountForUsername(String username) {
        return this.userDao.incrementFailedCountForUsername(username);
    }

    /**
     * Method to update the user's login details
     *
     * @param userId UserId is used to get the user of which you want to update
     * the login details by calling a method from userDao
     */
    @Override
    public void loginSuccessUpdateForUserId(int userId) {
        this.userDao.loginSuccessUpdateForUserId(userId);
    }

    /**
     * Method to set the number of failed attempts from userId
     *
     * @param userId userId is used to get the user of which you want to reset
     * the failed attempts
     */
    @Override
    public void resetFailedAttempts(int userId) {
        this.userDao.resetFailedAttempts(userId);
    }

    /**
     * Method to confirm the password
     *
     * @param password
     * @return Returns true when the password has been confirmed Returns false
     * when the password does not matched
     */
    @Override
    public boolean confirmPassword(Password password) {
        return this.userDao.confirmPassword(password);
    }

    /**
     * Method to update the password for existing user
     *
     * @param password Password to get the password
     * @param offset Offset to calculate the offset date
     */
    @Override
    public void updatePassword(Password password, int offset) {
        this.userDao.updatePassword(password, offset);
    }

    /**
     * Method to get the list of Business functions that a userId has access to
     *
     * @param userId userId that you want to get the Business functions for
     * @return Returns a list of Business functions that the userId has access
     * to
     */
    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        return this.userDao.getBusinessFunctionsForUserId(userId);
    }

    @Override
    public List<Role> getRoleList() {
        return this.userDao.getRoleList();
    }

    @Override
    public boolean existUserByUsername(String username) {
        if (this.userDao.getUserByUsername(username) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int addUser(User user) {
        return this.userDao.addUser(user);
    }

    @Override
    public List<User> getUserList() {
        return this.userDao.getUserList();
    }

    @Override
    public User getUserByUserId(int userId) {
        return this.userDao.getUserByUserId(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }
}
