/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.ReportType;
import cc.altius.model.ServerType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sagar
 */
public class ReportTypeMapper implements RowMapper<ReportType> {

    @Override
    public ReportType mapRow(ResultSet rs, int i) throws SQLException {
        ReportType reportType = new ReportType();
        reportType.setReportTypeId(rs.getInt("REPORT_TYPE_ID"));
        reportType.setReportTypeDesc(rs.getString("REPORT_TYPE_DESC"));
        ServerType s = new ServerType();
        s.setServerTypeId(rs.getInt("SERVER_TYPE_ID"));
        s.setServerTypeDesc(rs.getString("SERVER_TYPE_DESC"));
        reportType.setServerType(s);
        reportType.setActive(rs.getBoolean("ACTIVE"));
        return reportType;
    }
}
