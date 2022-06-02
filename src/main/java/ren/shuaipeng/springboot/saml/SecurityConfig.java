package ren.shuaipeng.springboot.saml;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository);
        // 启用 sp 元数据提供端点
        Saml2MetadataFilter filter = new Saml2MetadataFilter(relyingPartyRegistrationResolver, new OpenSamlMetadataResolver());

        // 定义受 saml 保护的路径
        http.saml2Login(Customizer.withDefaults())
                .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/**").authenticated();
    }
}