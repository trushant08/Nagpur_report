/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.CustomUserDetails;
import cc.altius.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class CustomUserDetailsRowMapper implements RowMapper<CustomUserDetails> {

    @Override
    public CustomUserDetails mapRow(ResultSet rs, int i) throws SQLException {
        CustomUserDetails user = new CustomUserDetails();
        user.setUserId(rs.getInt("USER_ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setActive(rs.getBoolean("ACTIVE"));
        user.setExpired(rs.getBoolean("EXPIRED"));
        user.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
        user.setExpiresOn(rs.getDate("EXPIRES_ON"));
        user.setOutsideAccess(rs.getBoolean("OUTSIDE_ACCESS"));
        Role r = new Role();
        r.setRoleId(rs.getString("ROLE_ID"));
        r.setRoleName(rs.getString("ROLE_NAME"));
        user.setRole(r);
        return user;
    }
}
