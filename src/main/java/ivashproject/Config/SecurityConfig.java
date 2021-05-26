package ivashproject.Config;

import ivashproject.Config.handler.LoginSuccessHandler;
import ivashproject.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories("ivashproject.*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginSuccessHandler loginSuccessHandler;

    private final UserDetailServiceImpl userDetailService;

    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, @Qualifier("userDet") UserDetailServiceImpl userDetailService) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/*").hasAnyAuthority("user:write")
                .antMatchers("/user").authenticated()
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .and()
                .logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDao());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public DaoAuthenticationProvider getDao(){
        DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userDetailService);
        return dao;
    }
}


