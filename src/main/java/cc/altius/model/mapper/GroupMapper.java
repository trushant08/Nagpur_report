/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.Group;
import cc.altius.model.ServerType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sagar
 */
public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int i) throws SQLException {
        Group group = new Group();
        group.setGroupId(rs.getInt("GROUP_ID"));
        group.setGroupDesc(rs.getString("GROUP_DESC"));
        group.setActive(rs.getBoolean("ACTIVE"));
        ServerType s = new ServerType();
        s.setServerTypeId(rs.getInt("SERVER_TYPE_ID"));
        s.setServerTypeDesc(rs.getString("SERVER_TYPE_DESC"));
        group.setServerType(s);
        return group;
    }
}
