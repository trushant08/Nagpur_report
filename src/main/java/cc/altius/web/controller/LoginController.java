/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Akil Mahimwala
 */
@Controller
public class LoginController {

    private @Value("#{versionProperties['version.major']}")
    String majorVersion;
    private @Value("#{versionProperties['version.minor']}")
    String minorVersion;

    @RequestMapping("/login.htm")
    public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        model.addAttribute("majorVersion", majorVersion);
        model.addAttribute("minorVersion", minorVersion);

        return "login";
    }
}
