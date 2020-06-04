package com.anzhiyule.transfer.tools;

import com.anzhiyule.transfer.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    public static String getUser() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof User) {
            return ((User) o).getId();
        } else {
            return "";
        }
    }
}
