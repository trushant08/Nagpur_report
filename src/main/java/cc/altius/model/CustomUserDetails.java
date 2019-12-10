/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author shrutika
 */
public class CustomUserDetails implements UserDetails {

    private int userId;
    private String username;
    private String password;
    private boolean active;
    private boolean expired;
    private Date expiresOn;
    private int failedAttempts;
    private boolean outsideAccess;
    private Role role;
    private List<SimpleGrantedAuthority> businessFunction;

    public Collection<SimpleGrantedAuthority> getBusinessFunction() {
        return businessFunction;
    }

    public void setBusinessFunction(List<String> businessFunction) {
        List<SimpleGrantedAuthority> finalBusinessFunctions = new ArrayList<SimpleGrantedAuthority>();
        for (String bf : businessFunction) {
            finalBusinessFunctions.add(new SimpleGrantedAuthority(bf));
        }
        this.businessFunction = finalBusinessFunctions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.businessFunction;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.failedAttempts >= 3) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isPasswordExpired() {
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        if (DateUtils.compareDates(DateUtils.formatDate(this.expiresOn, DateUtils.YMD), curDate) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isOutsideAccess() {
        return outsideAccess;
    }

    public void setOutsideAccess(boolean outsideAccess) {
        this.outsideAccess = outsideAccess;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" + "userId=" + userId + ", username=" + username + ", active=" + active + ", expired=" + expired + ", expiresOn=" + expiresOn + ", failedAttempts=" + failedAttempts + ", outsideAccess=" + outsideAccess + ", role=" + role + ", businessFunction=" + businessFunction + '}';
    }
}
