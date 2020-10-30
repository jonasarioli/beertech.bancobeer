package com.beertech.banco.infrastructure.rest.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beertech.banco.infrastructure.repository.mysql.ContaRepository;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ContaRepository contaRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // configuraçoes de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // configurações de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/beercoins/operacao").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/operacao/*").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/transferencia").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/transferencia/*").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/reward").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/reward/*").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/login").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/validacao/token").permitAll()
                .antMatchers(HttpMethod.POST, "/beercoins/validacao/token/*").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationTokenFilter(tokenService, contaRepository), UsernamePasswordAuthenticationFilter.class)
                .cors();
    }

    // configurações de recursos estaticos(js, css, img)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}
