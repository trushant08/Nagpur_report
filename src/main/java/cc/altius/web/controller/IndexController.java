/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author shrutika
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String getHome() {

//        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        map.addAttribute("bfList", curUser.getAuthorities());

        return "/home";
    }
}
