/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.utils;

import cc.altius.model.CustomUserDetails;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Akil Mahimwala
 */
public class LogUtils {

    @Autowired
    public static Logger systemLogger = Logger.getLogger("systemLogging");
    public static Logger debugLogger = Logger.getLogger("debugLogging");
    public static Logger accessLogger = Logger.getLogger("accessLogging");

    public static String buildStringForSystemLog(Exception e) {
        StringWriter sWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(sWriter));
        return (new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sWriter.toString()).toString());
    }
    
    public static String buildStringForAccessLog(Exception e) {
        StringWriter sWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(sWriter));
        return (new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sWriter.toString()).toString());
    }

    public static String buildStringForSystemLog(String sqlString, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Map.Entry tmpEntry : params.entrySet()) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            } else {
                sb.append(", ").append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            }
        }
        sb.append(")");
        return (sb.toString());
    }
    
    public static String buildStringForAccessLog(String sqlString, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Map.Entry tmpEntry : params.entrySet()) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            } else {
                sb.append(", ").append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            }
        }
        sb.append(")");
        return (sb.toString());
    }
    
    public static String buildStringForSystemLog(String sqlString, Object[] params) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Object tmpParam : params) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpParam);
            } else {
                sb.append(", ").append(tmpParam);
            }
        }
        sb.append(")");
        return (sb.toString());
    }
    public static String buildStringForAccessLog(String sqlString, Object[] params) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sqlString).append(" -- parameters(");
        boolean firstRun = true;
        for (Object tmpParam : params) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpParam);
            } else {
                sb.append(", ").append(tmpParam);
            }
        }
        sb.append(")");
        return (sb.toString());
    }

    public static String buildStringForSystemLog(String sqlString, List<Object[]> paramList) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(sqlString).append(" -- parameters( ");
        boolean firstRun = true;
        for (Object params[] : paramList) {
            sb.append(" (");
            firstRun = true;
            for (Object tmpParam : params) {
                if (firstRun) {
                    firstRun = false;
                    sb.append(tmpParam);
                } else {
                    sb.append(", ").append(tmpParam);
                }
            }
            sb.append(")");
        }
        sb.append(")");
        return (sb.toString());
    }
    
    public static String buildStringForSystemLog(String msg) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(msg);
        System.out.println(sb);
        return sb.toString();
    }
    
    public static String buildStringForAccessLog(String msg) {
        StringBuilder sb = new StringBuilder(getIpAddress()).append(" -- ").append(getUsername()).append("(").append(getUserId()).append(") -- ").append(msg);
        return sb.toString();
    }

    private static String getIpAddress() {
        try {
//            return ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        } catch (NullPointerException n) {
            return "0.0.0.0";
        }
    }

    private static int getUserId() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            } else {
                return 0;
            }
        } catch (NullPointerException n) {
            return 0;
        }
    }

    private static String getUsername() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            } else {
                return "";
            }
        } catch (NullPointerException n) {
            return "blank";
        }
    }
}
