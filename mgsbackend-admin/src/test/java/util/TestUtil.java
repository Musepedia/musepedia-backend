package util;

import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    public static void loginAs(Long userId, String... roles) {
        List<GrantedAuthority> authorities = Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        CodeAuthenticationToken successToken = new CodeAuthenticationToken(authorities);
        successToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(successToken);
        SecurityUtil.setCurrentUserId(userId);
    }
}
