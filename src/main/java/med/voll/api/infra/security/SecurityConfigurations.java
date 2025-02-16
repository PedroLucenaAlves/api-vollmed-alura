package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Concentra as configurações de segurança do Spring Security
 */

@Configuration //Aponta ao Spriong que é uma classe de segurança
@EnableWebSecurity //Indica ao Spring que personalizaremos as configs de segurança
public class SecurityConfigurations {

    @Bean //Serve para exportar uma classe para o Spring, fazendo com que ele consiga carrega-la e realize a sua injecao de dependencia em outras classes
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf.disable = desabilita proteções contra cross site, porque o proprio token ja protege contra esse ataque
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

    @Bean//metodo que ensina o Spring a instanciar nossa classe AuthenticationManager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); //metodo da classe AuthenticationConfiguration que sabe criar um objeto authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //Ensinando ao script a tratar hash de senha (nesse caso o BCrypt) que foi passado no banco
        //nao e boa pratica armazenar a senha sem criptografar no banco por questao de segurança
    }

}
