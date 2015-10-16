/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.CustomUserDetails;
import cc.altius.model.Password;
import cc.altius.model.User;
import cc.altius.service.UserService;
import cc.altius.utils.DateUtils;
import cc.altius.utils.LogUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author shrutika
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "updateExpiredPassword.htm", method = RequestMethod.GET)
    public String showUpdatePasswordExpiredForm(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LogUtils.debugLogger.debug("Showing form for UpdateExpiredPassword");
        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "updateExpiredPassword";
    }

    @RequestMapping(value = "updateExpiredPassword.htm", method = RequestMethod.POST, params = "_update")
    public String updatePasswordExpiredOnSubmit(final @ModelAttribute("password") Password password, Errors errors, SessionStatus sessionStatus) {
        if (!this.userService.confirmPassword(password)) {
            LogUtils.debugLogger.debug("Nested path=" + errors.getNestedPath());
            errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
            CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            password.setUserId(curUser.getUserId());
            password.setUsername(curUser.getUsername());
            return "updateExpiredPassword";
        } else {
            LogUtils.debugLogger.debug("Updating user password");
            this.userService.updatePassword(password, 90);
            // all you need to do now is load the correct Authorities
            Authentication curAuthentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            curUser.setExpiresOn(DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, 90));
            curUser.setBusinessFunction(this.userService.getBusinessFunctionsForUserId(curUser.getUserId()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(curUser, curAuthentication.getCredentials(), curUser.getAuthorities());
            auth.setDetails(curAuthentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(auth);
            sessionStatus.setComplete();
            return "redirect:/index.htm?msg=msg.passwordUpdated";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "_logout")
    private String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:j_spring_security_logout";
    }

    @RequestMapping(value = "addUser.htm", method = RequestMethod.GET)
    public String showAddUserForm(ModelMap model) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = new User();
        user.setActive(true);
        model.addAttribute("user", user);
        model.addAttribute("roleList", this.userService.getRoleList());

        return "addUser";
    }

    @RequestMapping(value = "addUser.htm", method = RequestMethod.POST)
    public String onAddUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            user = null;
            
            return "redirect:userList.htm?msg=msg.actionCancelled";
        } else {
            if (this.userService.existUserByUsername(user.getUsername())) {
                errors.rejectValue("username", "msg.duplicateUser");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "addUser";
            }
            int userId = this.userService.addUser(user);
            if (userId == 0) {
                errors.rejectValue("username", "msg.userError");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "addUser";
            } else {
                return "redirect:userList.htm?msg=msg.userAddedSuccessfully";
            }
        }
    }

    @RequestMapping(value = "userList.htm", method = RequestMethod.GET)
    public String showUserListPage(ModelMap model) {
        model.addAttribute("userList", this.userService.getUserList());
        return "userList";
    }

    @RequestMapping(value = "showEditUser.htm", method = RequestMethod.POST)
    public String showEditUserForm(@RequestParam(value = "userId", required = true) int userId, ModelMap model) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = this.userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("roleList", this.userService.getRoleList());

        return "editUser";

    }

    @RequestMapping(value = "editUser.htm", method = RequestMethod.POST)
    public String onEditUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        try {
            User newUser = this.userService.getUserByUsername(user.getUsername());

            if (newUser != null && newUser.getUserId() != user.getUserId()) {
                errors.reject("username", "msg.duplicateUser");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "editUser";
            }
            try {
                this.userService.updateUser(user);
                return "redirect:userList.htm?msg=msg.userUpdatedSuccessfully";
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.systemLogger.info(e);
                errors.reject("username", "msg.userError");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "editUser";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    @RequestMapping(value = "editUser.htm", method = RequestMethod.POST, params = "_cancel")
    public String onEditUserCancel(@ModelAttribute("user") User user, ModelMap model) {
        user = null;
        return "redirect:userList.htm?msg=msg.actionCancelled";
    }
    
    @RequestMapping(value = "userFailedAttemptsReset.htm", method = RequestMethod.POST)
    public String userFailedAttemptsReset(@RequestParam(value = "userId", required = true) int userId) {
        this.userService.resetFailedAttempts(userId);
        return "redirect:userList.htm?msg=msg.failedAttemptsReset";
    }
}
