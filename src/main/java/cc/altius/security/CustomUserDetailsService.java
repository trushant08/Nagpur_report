/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.security;

import cc.altius.dao.LogDao;
import cc.altius.dao.UserDao;
import cc.altius.framework.GlobalConstants;
import cc.altius.model.CustomUserDetails;
import cc.altius.utils.IPUtils;
import cc.altius.utils.LogUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author shrutika
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LogDao logDao;
    
    @Autowired
    private UserDao userDao;
    private Set<String> allowedIpRange;

    public CustomUserDetailsService() {
        this.allowedIpRange = new HashSet<String>();
        this.allowedIpRange.addAll(Arrays.asList(GlobalConstants.ALLOWED_IP_RANGE));
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();

        try {
            CustomUserDetails user = this.userDao.getCustomUserByUsername(username);
            if (!user.isActive()) {
                this.logDao.accessLog(ipAddress, username, null, false, "Account disabled");
            } else if (!user.isAccountNonLocked()) {
                this.logDao.accessLog(ipAddress, username, null, false, "Account locked");
            } else if (!(user.isOutsideAccess() || checkIfIpIsFromAllowedRange(ipAddress))) {
                user.setActive(false);
                this.logDao.accessLog(ipAddress, username, null, false, "Outside access");
            } else {
                if (user.isPasswordExpired()) {
                    // only insert the ROLE_BF_PASSWORD_EXPIRED
                    LogUtils.debugLogger.debug("Credentials are Expired so only put in ROLE_BF_PASSWORD_EXPIRED into Authoirites");
                    List<String> businessFunctions = new LinkedList<String>();
                    businessFunctions.add("ROLE_BF_PASSWORD_EXPIRED");
                    user.setBusinessFunction(businessFunctions);
                } else {
                    user.setBusinessFunction(this.userDao.getBusinessFunctionsForUserId(user.getUserId()));
                    System.out.println(user);
                }
            }   
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }
    
    private boolean checkIfIpIsFromAllowedRange(String ipToCheck) {
        for (String curRange : this.allowedIpRange) {
            IPUtils curIpRange = new IPUtils(curRange);
            if(curIpRange.checkIP(ipToCheck)) {
                return true;
            }
        }
        return false;
    }
}
