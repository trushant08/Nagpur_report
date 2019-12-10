/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.ServerType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class ServerTypeMapper implements RowMapper<ServerType> {

    @Override
    public ServerType mapRow(ResultSet rs, int i) throws SQLException {
        ServerType s = new ServerType();
        s.setServerTypeId(rs.getInt("SERVER_TYPE_ID"));
        s.setServerTypeDesc(rs.getString("SERVER_TYPE_DESC"));

        return s;
    }
}
