package com.ClinicaOdontologica.UP.security;

import com.ClinicaOdontologica.UP.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity   // No vamos a poder usar test de integración -> se activa la seguridad.
public class WebConfigSecurity {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Desactivo las referencias cruzadas para construcción, deberia estar activado cuando salga a PROD.
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/index.html").hasRole("USER")
                        .requestMatchers("/get_pacientes.html").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()).logout(withDefaults());
        return http.build();


    }
}
