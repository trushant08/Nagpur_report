/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.UserDao;
import cc.altius.model.CustomUserDetails;
import cc.altius.model.Password;
import cc.altius.model.Role;
import cc.altius.model.User;
import cc.altius.model.mapper.CustomUserDetailsRowMapper;
import cc.altius.model.mapper.RoleRowMapper;
import cc.altius.model.mapper.UserRowMapper;
import cc.altius.utils.DateUtils;
import cc.altius.utils.LogUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shrutika
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Method to update the login failure count from username
     *
     * @param username username is used to get the user of whom you want to
     * update the failedCount
     * @return Returns the updated login failedCount of a user
     */
    @Override
    public int incrementFailedCountForUsername(String username) {
        String sqlString = "UPDATE `user` SET user.`FAILED_ATTEMPTS`=user.`FAILED_ATTEMPTS`+1 WHERE user.`USERNAME`=?";
        return this.jdbcTemplate.update(sqlString, username);
    }

    /**
     * Method to set the number of failed attempts from userId
     *
     * @param userId userId is used to get the user of which you want to reset
     * the failed attempts
     */
    @Override
    public void resetFailedAttempts(int userId) {
        String sqlString = "UPDATE `user` SET user.`FAILED_ATTEMPTS`=0 WHERE user.`USER_ID`=?";
        this.jdbcTemplate.update(sqlString, userId);
    }

    /**
     * Method to update the user's login details
     *
     * @param userId UserId is used to get the user of which you want to update
     * the login details
     */
    @Override
    public void loginSuccessUpdateForUserId(int userId) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        String sqlString = "UPDATE `user` SET user.`FAILED_ATTEMPTS`=0, user.`LAST_LOGIN_DATE`=? WHERE user.`USER_ID`=?";
        this.jdbcTemplate.update(sqlString, curDt, userId);
    }

    /**
     * Method to get Customer object from username
     *
     * @param username Username used to login
     * @return Returns the Customer object, null if no object could be found
     */
    @Override
    public CustomUserDetails getCustomUserByUsername(String username) {
        try {
            String sqlString = "SELECT user.*, role.* FROM user left join user_role on user.USER_ID=user_role.USER_ID left join role on role.ROLE_ID=user_role.ROLE_ID WHERE USERNAME=?";
            return this.jdbcTemplate.queryForObject(sqlString, new CustomUserDetailsRowMapper(), username);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
            return null;
        }
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
        String sqlString = "SELECT BUSINESS_FUNCTION_ID FROM user_role LEFT JOIN role_business_function ON user_role.ROLE_ID=role_business_function.ROLE_ID WHERE user_role.USER_ID=?";
        return this.jdbcTemplate.queryForList(sqlString, String.class, userId);
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
        String sqlString = "SELECT user.PASSWORD FROM user WHERE user.USER_ID=?";
        String hash = this.jdbcTemplate.queryForObject(sqlString, String.class, password.getUserId());
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password.getOldPassword(), hash)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to update the password for existing user
     *
     * @param password Password to get the password
     * @param offset Offset to calculate the offset date
     */
    @Override
    public void updatePassword(Password password, int offset) {
        Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, offset);
        String sqlString = "UPDATE user SET PASSWORD=?, EXPIRES_ON=? WHERE user.USER_ID=?";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password.getNewPassword());
        Object params[] = new Object[]{hash, offsetDate, password.getUserId()};
        this.jdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Role> getRoleList() {
        return this.jdbcTemplate.query("SELECT * FROM role", new RoleRowMapper());
    }

    @Override
    public User getUserByUsername(String username) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM `user` "
                + "LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + "LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID "
                + "WHERE user.USERNAME=?";
        Object params[] = new Object[]{username};
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
            return this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog("No User found with username"));
            return null;
        }
    }

    @Override
    @Transactional
    public int addUser(User user) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert userInsert = new SimpleJdbcInsert(this.dataSource).withTableName("user").usingGeneratedKeyColumns("USER_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPass = encoder.encode(user.getPassword());
        params.put("USERNAME", user.getUsername());
        params.put("PASSWORD", hashPass);
        params.put("ACTIVE", user.isActive());
        params.put("EXPIRED", false);
        params.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1));
        params.put("FAILED_ATTEMPTS", 0);
        params.put("OUTSIDE_ACCESS", user.isOutsideAccess());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int userId = userInsert.executeAndReturnKey(params).intValue();
        params.clear();
        String sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
        this.jdbcTemplate.update(sqlString, userId, user.getRole().getRoleId());
        return userId;
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM `user` "
                + "LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + "LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID";
        return this.jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getUserByUserId(int userId) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM `user` "
                + "LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + "LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID "
                + "WHERE user.USER_ID=?";

        Object params[] = new Object[]{userId};
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
            return this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog("No User found with userId"));
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        if (user.getPassword().isEmpty()) {
            sqlString = "UPDATE user SET USERNAME=?, ACTIVE=?, OUTSIDE_ACCESS=?,LAST_MODIFIED_BY=?,LAST_MODIFIED_DATE=?  WHERE USER_ID=?";
            params = new Object[]{user.getUsername(), user.isActive(), user.isOutsideAccess(), curUser, curDate, user.getUserId()};
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
            this.jdbcTemplate.update(sqlString, params);
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(user.getPassword());
            sqlString = "UPDATE user SET USERNAME=?, PASSWORD=?, ACTIVE=?, OUTSIDE_ACCESS=?,LAST_MODIFIED_BY=?,LAST_MODIFIED_DATE=?  WHERE USER_ID=?";
            params = new Object[]{user.getUsername(), hash, user.isActive(), user.isOutsideAccess(), curUser, curDate, user.getUserId()};
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
            this.jdbcTemplate.update(sqlString, params);
        }

        sqlString = "DELETE FROM user_role WHERE USER_ID=?";
        params = new Object[]{user.getUserId()};
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
        this.jdbcTemplate.update(sqlString, user.getUserId());

        sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";
        params = new Object[]{user.getUserId(), user.getRole().getRoleId()};
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sqlString, params));
        this.jdbcTemplate.update(sqlString, params);
    }
}
