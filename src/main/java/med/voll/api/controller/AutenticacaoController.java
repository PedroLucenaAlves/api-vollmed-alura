package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.AutenticacaoService;
import med.voll.api.domain.usuario.DadosAutenticacaoDTO;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; //classe do SpringSecurity que permite instanciar nosso service de autenticacao

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); //convertendo do nosso dto para o dto do SpringSecurity
        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); //controller precisa devolver um token na resposta da req
    }

}
