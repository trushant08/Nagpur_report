/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service;

import cc.altius.model.Password;
import cc.altius.model.Role;
import cc.altius.model.User;
import java.util.List;

/**
 *
 * @author shrutika
 */
public interface UserService {

    public int incrementFailedCountForUsername(String username);

    public void loginSuccessUpdateForUserId(int userId);

    public void resetFailedAttempts(int userId);
    
    public boolean confirmPassword(Password password);

    public void updatePassword(Password password, int offset);
    
    public List<String> getBusinessFunctionsForUserId(int userId);
    
    public List<Role> getRoleList();
    
    public boolean existUserByUsername(String username);
    
    public int addUser(User user);
    
    public List<User> getUserList();
    
    public User getUserByUserId(int userId);
    
    public User getUserByUsername(String username);
        
    public void updateUser(User user);
}
