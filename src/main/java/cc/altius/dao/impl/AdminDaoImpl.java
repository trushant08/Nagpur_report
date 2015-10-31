/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.AdminDao;
import cc.altius.model.CustomUserDetails;
import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.ServerType;
import cc.altius.model.Service;
import cc.altius.model.mapper.GroupMapper;
import cc.altius.model.mapper.ReportTypeMapper;
import cc.altius.model.mapper.ServerTypeMapper;
import cc.altius.model.mapper.ServiceMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shrutika
 */
@Repository
public class AdminDaoImpl implements AdminDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Report:
    @Override
    public int addReportType(ReportType reportType) {
        SimpleJdbcInsert reportTypeInsert = new SimpleJdbcInsert(this.dataSource).withTableName("report_types").usingGeneratedKeyColumns("REPORT_TYPE_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("REPORT_TYPE_DESC", reportType.getReportTypeDesc());
        params.put("SERVER_TYPE_ID", reportType.getServerType().getServerTypeId());
        params.put("ACTIVE", reportType.isActive());
        int reportTypeId = reportTypeInsert.executeAndReturnKey(params).intValue();

        return reportTypeId;
    }

    @Override
    public List<ServerType> getServerTypeList() {
        String sql = "SELECT * FROM server_type";

        return this.jdbcTemplate.query(sql, new ServerTypeMapper());
    }

    @Override
    public List<ReportType> getReportTypeList() {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,rt.* FROM report_types rt"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=rt.`SERVER_TYPE_ID`";
        return this.jdbcTemplate.query(sql, new ReportTypeMapper());
    }

    @Override
    public void updateReportType(ReportType reportType) {
        String sql = "UPDATE report_types SET REPORT_TYPE_DESC =?, ACTIVE=?, SERVER_TYPE_ID=? WHERE REPORT_TYPE_ID=?";
        try {
            this.jdbcTemplate.update(sql, reportType.getReportTypeDesc(), reportType.isActive(), reportType.getServerType().getServerTypeId(), reportType.getReportTypeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReportType getReportTypeObjByReportTypeId(int reportTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,rt.* FROM report_types rt"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=rt.`SERVER_TYPE_ID`"
                + " WHERE REPORT_TYPE_ID=?";
        return this.jdbcTemplate.queryForObject(sql, new ReportTypeMapper(), reportTypeId);
    }

    //Group:
    @Override
    public int addGroup(Group group) {
        SimpleJdbcInsert groupInsert = new SimpleJdbcInsert(this.dataSource).withTableName("groups").usingGeneratedKeyColumns("GROUP_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("GROUP_DESC", group.getGroupDesc());
        params.put("ACTIVE", group.isActive());
        params.put("SERVER_TYPE_ID", group.getServerType().getServerTypeId());
        int groupId = groupInsert.executeAndReturnKey(params).intValue();

        return groupId;
    }

    @Override
    public List<Group> getGroupList() {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,g.* FROM groups g"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=g.`SERVER_TYPE_ID`";
        return this.jdbcTemplate.query(sql, new GroupMapper());
    }

    @Override
    public void updateGroup(Group group) {
        String sql = "UPDATE groups SET GROUP_DESC =?, ACTIVE=?, SERVER_TYPE_ID=? WHERE GROUP_ID=?";
        try {
            this.jdbcTemplate.update(sql, group.getGroupDesc(), group.isActive(), group.getServerType().getServerTypeId(), group.getGroupId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Group getGroupObjByGroupId(int groupId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,g.* FROM groups g"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=g.`SERVER_TYPE_ID`"
                + " WHERE GROUP_ID=?";
        return this.jdbcTemplate.queryForObject(sql, new GroupMapper(), groupId);
    }

    //Service:
    @Override
    public int addService(Service service) {
        SimpleJdbcInsert serviceInsert = new SimpleJdbcInsert(this.dataSource).withTableName("service").usingGeneratedKeyColumns("SERVICE_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SERVICE_NAME", service.getServiceName());
        params.put("SERVER_SERVICE_ID", service.getServerServiceId());
        params.put("SERVER_TYPE_ID", service.getServerType().getServerTypeId());
        params.put("ACTIVE", service.isActive());
        int serviceId = serviceInsert.executeAndReturnKey(params).intValue();

        return serviceId;
    }

    @Override
    public List<Service> getServiceList() {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,s.* FROM service s"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=s.`SERVER_TYPE_ID`";
        return this.jdbcTemplate.query(sql, new ServiceMapper());
    }

    @Override
    public void updateService(Service service) {
        String sql = "UPDATE service SET SERVICE_NAME =?,ACTIVE=?,SERVER_SERVICE_ID=?, SERVER_TYPE_ID=? WHERE SERVICE_ID=?";
        try {
            this.jdbcTemplate.update(sql, service.getServiceName(), service.isActive(), service.getServerServiceId(), service.getServerType().getServerTypeId(), service.getServiceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Service getServiceObjByServiceId(int serviceId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,s.* FROM service s"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=s.`SERVER_TYPE_ID`"
                + " WHERE SERVICE_ID=?";
        return this.jdbcTemplate.queryForObject(sql, new ServiceMapper(), serviceId);
    }

    @Override
    public List<ReportType> getReportTypeListByserverTypeId(int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,rt.* FROM report_types rt"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=rt.`SERVER_TYPE_ID`"
                + " WHERE rt.`SERVER_TYPE_ID`=? AND rt.ACTIVE =1";
        return this.jdbcTemplate.query(sql, new ReportTypeMapper(), serverTypeId);
    }

    @Override
    public List<Group> getMappedGroupListByReportType(int reportTypeId, int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,g.* FROM groups g"
                + " LEFT JOIN report_type_group rtg ON rtg.`GROUP_ID`=g.`GROUP_ID`"
                + " LEFT JOIN server_type st ON g.`SERVER_TYPE_ID`=st.`SERVER_TYPE_ID`"
                + " WHERE REPORT_TYPE_ID=? AND g.`SERVER_TYPE_ID`=? AND g.`ACTIVE`=1 ";
        return this.jdbcTemplate.query(sql, new GroupMapper(), reportTypeId, serverTypeId);
    }

    @Override
    public List<Group> getGroupListNotMapped(int reportTypeId, int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,g.* FROM groups g"
                + " LEFT JOIN server_type st ON g.`SERVER_TYPE_ID`=st.`SERVER_TYPE_ID`"
                + " WHERE GROUP_ID NOT IN"
                + " (SELECT GROUP_ID FROM report_type_group WHERE REPORT_TYPE_ID =?)"
                + "AND g.`SERVER_TYPE_ID`=? AND g.`ACTIVE`=1";
        return this.jdbcTemplate.query(sql, new GroupMapper(), reportTypeId, serverTypeId);
    }

    @Override
    @Transactional
    public int insertReportTypeGroupMapping(int reportTypeId, String[] assignGroups, int serverTypeId) {
        StringBuilder sqlBuilder = new StringBuilder();

        //Delete all assigned groups from report_type_group.
        sqlBuilder.setLength(0);
        sqlBuilder.append("DELETE FROM report_type_group WHERE REPORT_TYPE_ID=? ");
        this.jdbcTemplate.update(sqlBuilder.toString(), reportTypeId);

        //Assign default groups to report type.
        sqlBuilder.setLength(0);
        sqlBuilder.append("INSERT INTO report_type_group(REPORT_TYPE_GROUP_ID,REPORT_TYPE_ID,GROUP_ID,SERVER_TYPE_ID) VALUES (NULL,?,?,?)");
        for (String assign : assignGroups) {
            this.jdbcTemplate.update(sqlBuilder.toString(), reportTypeId, Integer.parseInt(assign), serverTypeId);
        }

        return 1;
    }

    @Override
    public List<Group> getGroupListByserverTypeId(int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,g.* FROM groups g"
                + " LEFT JOIN server_type st ON st.`SERVER_TYPE_ID`=g.`SERVER_TYPE_ID`"
                + " WHERE g.`SERVER_TYPE_ID`=? AND g.ACTIVE =1";
        return this.jdbcTemplate.query(sql, new GroupMapper(), serverTypeId);
    }

    @Override
    public List<Service> getMappedServiceListByGroup(int groupId, int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,s.* FROM service s"
                + " LEFT JOIN service_group sg ON sg.`SERVICE_ID`=s.`SERVICE_ID`"
                + " LEFT JOIN server_type st ON s.`SERVER_TYPE_ID`=st.`SERVER_TYPE_ID`"
                + " WHERE GROUP_ID=? AND s.`SERVER_TYPE_ID`=? AND s.ACTIVE =1";
        return this.jdbcTemplate.query(sql, new ServiceMapper(), groupId, serverTypeId);
    }

    @Override
    public List<Service> getServiceListNotMapped(int groupId, int serverTypeId) {
        String sql = "SELECT st.`SERVER_TYPE_DESC`,s.* FROM service s"
                + " LEFT JOIN server_type st ON s.`SERVER_TYPE_ID`=st.`SERVER_TYPE_ID`"
                + " WHERE SERVICE_ID NOT IN"
                + " (SELECT SERVICE_ID FROM service_group WHERE GROUP_ID =?)"
                + " AND s.`SERVER_TYPE_ID`=? AND s.`ACTIVE`=1";
        return this.jdbcTemplate.query(sql, new ServiceMapper(), groupId, serverTypeId);
    }

    @Override
    @Transactional
    public int insertGroupServiceMapping(int groupId, String[] assignServices, int serverTypeId) {
        StringBuilder sqlBuilder = new StringBuilder();

        //Delete all assigned services from service_group.
        sqlBuilder.setLength(0);
        sqlBuilder.append("DELETE FROM service_group WHERE GROUP_ID=? ");
        this.jdbcTemplate.update(sqlBuilder.toString(), groupId);

        //Assign default services to group.
        sqlBuilder.setLength(0);
        sqlBuilder.append("INSERT INTO service_group(SERVICE_GROUP_ID,GROUP_ID,SERVICE_ID,SERVER_TYPE_ID) VALUES (NULL,?,?,?)");
        for (String assign : assignServices) {
            this.jdbcTemplate.update(sqlBuilder.toString(), groupId, Integer.parseInt(assign), serverTypeId);
        }

        return 1;
    }
}
