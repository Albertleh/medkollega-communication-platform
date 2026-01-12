package at.ac.tuwien.isg.backend.utils;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.service.UserService;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    private ApplicationUser user;

    public UserContext(UserService userService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        this.user = userService.findApplicationUserByEmail((String) authentication.getPrincipal());
    }

}
