package com.balloon.balloonet

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()?.authorizeRequests()
                ?.antMatchers("/", "/users/register")
                ?.permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()
                ?.withUser("user")
                ?.password("user")
                ?.roles("USER")
    }
}