/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.Role;
import cc.altius.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author gaurao
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("USER_ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setActive(rs.getBoolean("ACTIVE"));
        user.setExpired(rs.getBoolean("EXPIRED"));
        user.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
        user.setExpiresOn(rs.getDate("EXPIRES_ON"));
        Role role = new Role();
        role.setRoleId(rs.getString("ROLE_ID"));
        role.setRoleName(rs.getString("ROLE_NAME"));
        user.setRole(role);
        user.setOutsideAccess(rs.getBoolean("OUTSIDE_ACCESS"));
        user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
        return user;
    }
}
