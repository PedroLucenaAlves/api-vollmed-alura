package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe responsável por isolar os tratamentos de erros
 */

@RestControllerAdvice //indica ao Spring que e uma classe para esse tipo que trata erros
public class TratadorDeErrosAdvice {

    //@ExcpetionHandlrer indica Para qual exception o metodo sera chamado. Nos parenteses passamos o tipo da exception
    //Isso evita utilizar try-catch nos controlles
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var listaErros = ex.getFieldErrors(); //captura todos os erros lançados na nossa exception

        //detalhando todos os campos que devem ser preenchidos na requisição (notblank)
        return ResponseEntity.badRequest().body(listaErros.stream().map(DadosErroValidacoes::new).toList());
    }

    private record DadosErroValidacoes(String campo, String mensagem){

        public DadosErroValidacoes(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }

    }

}
