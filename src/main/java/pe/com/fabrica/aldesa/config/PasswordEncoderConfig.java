package pe.com.fabrica.aldesa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Suministra un objeto que permite el uso de la función hash para encriptar contraseña 
 * 
 * @author Anthony Lopez
 *
 */
@Configuration
public class PasswordEncoderConfig {

	@Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
