package it.alecsferra.letspartylogin.core;

import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static String getCurrentUsername(){

        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

    }

}