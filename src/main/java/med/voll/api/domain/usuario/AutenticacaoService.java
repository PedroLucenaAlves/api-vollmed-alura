package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe contem a logica de autenticacao do projeto
 */

@Service //Diz ao Spring que a classe e um componente do tipo servico
public class AutenticacaoService implements UserDetailsService { //interface que aponta ao spring que essa classe se trata de um servico de autenticacao

    @Autowired
    private UsuarioRepository repository;

    @Override //toda vez que o usuario fizer login no projeto, o spring ira chamar esse metodo
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

}
