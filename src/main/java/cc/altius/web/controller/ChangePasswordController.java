/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.CustomUserDetails;
import cc.altius.model.Password;
import cc.altius.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Akil Mahimwala
 */
@Controller
@RequestMapping("/changePassword.htm")
@SessionAttributes("password")
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    private String showForm(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "changePassword";
    }

    @RequestMapping(method = RequestMethod.POST)
    private String updatePassword(@ModelAttribute("password") Password password, Errors errors, HttpServletRequest request) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            password = null;
            return "redirect:/index.htm?msg=msg.actionCancelled";
        } else {
            if (!this.userService.confirmPassword(password)) {
                errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
                return "changePassword";
            } else {
                this.userService.updatePassword(password, 90);
                return "redirect:/index.htm?msg=msg.passwordUpdated";
            }
        }
    }
}
