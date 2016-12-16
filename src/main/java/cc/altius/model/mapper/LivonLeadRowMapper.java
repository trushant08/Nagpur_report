/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model.mapper;

import cc.altius.model.MaricoLeadUpload;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class LivonLeadRowMapper implements RowMapper<MaricoLeadUpload> {

    @Override
    public MaricoLeadUpload mapRow(ResultSet rs, int i) throws SQLException {
        MaricoLeadUpload l = new MaricoLeadUpload();
        l.setLineNo(rs.getInt("LINE_NO"));
        l.setRetailerName(rs.getString("RETAILER_NAME"));
        l.setPhoneNo(rs.getString("PHONE_NO"));
        l.setUniqueId(rs.getString("UNIQUE_ID"));
        l.setBeatDescription(rs.getString("BEAT_DESC"));
        l.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
        l.setDistributorCode(rs.getString("DISTRIBUTOR_CODE"));
        l.setList_id(rs.getString("LIST_ID"));
        l.setDsr_status(rs.getString("DSR_STATUS"));
        l.setReason(rs.getString("REASON"));
        l.setStatus(rs.getBoolean("STATUS"));

        return l;
    }
}
