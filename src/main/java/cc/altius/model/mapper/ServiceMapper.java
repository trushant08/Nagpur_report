/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.ServerType;
import cc.altius.model.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sagar
 */
public class ServiceMapper implements RowMapper<Service> {

    @Override
    public Service mapRow(ResultSet rs, int i) throws SQLException {
        Service service = new Service();
        service.setServiceId(rs.getInt("SERVICE_ID"));
        service.setServerServiceId(rs.getString("SERVER_SERVICE_ID"));
        service.setServiceName(rs.getString("SERVICE_NAME"));
        ServerType s = new ServerType();
        s.setServerTypeId(rs.getInt("SERVER_TYPE_ID"));
        s.setServerTypeDesc(rs.getString("SERVER_TYPE_DESC"));
        service.setServerType(s);
        service.setActive(rs.getBoolean("ACTIVE"));

        return service;

    }
}
