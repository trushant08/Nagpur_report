/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Akil Mahimwala
 */
@Controller
@RequestMapping("accessDenied.htm")
public class AccessDeniedController {

    @RequestMapping(method = RequestMethod.GET)
    public String showPage() {
        System.out.println("Inside access denied controller");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.isAuthenticated()) {
                CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_PASSWORD_EXPIRED"))) {
                    return "redirect:updateExpiredPassword.htm";
                } else {
                    return "accessDenied";
                }
            }
        }
        return "redirect:/login.htm";
    }
}