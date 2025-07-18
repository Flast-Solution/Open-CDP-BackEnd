package vn.flast.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.flast.security.UserPrinciple;

import java.util.Optional;

@Component
public class BaseController {

    protected String getUsername() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getName).orElse(null);
    }

    protected int getUserId() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getId).orElse(0);
    }

    protected String getUserSso() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getSsoId).orElse(null);
    }

    public UserPrinciple getInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple) {
            return (UserPrinciple) principal;
        }
        return null;
    }
}
