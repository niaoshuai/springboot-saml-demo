package ren.shuaipeng.springboot.saml;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/principal")
    public Object getOidcUserPrincipal2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    @GetMapping("/sensitive_resource")
    public String getSensitiveResource() {
        return "success";
    }
}
