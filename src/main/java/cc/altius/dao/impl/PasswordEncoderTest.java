/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author shrutika
 */
public class PasswordEncoderTest {

    public static void main(String args[]) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("pass");
        Boolean hash1 = encoder.matches("$2a$10$pF64cJIRez9dK9FcFAaMa.if3zOFFzkT3lJbIpz4xnziZfSc527.m", "pass1");

        System.out.println(hash1);
    }
}
