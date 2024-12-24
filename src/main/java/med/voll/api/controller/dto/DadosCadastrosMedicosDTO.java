package med.voll.api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.medico.Especialidade;

/**
 * Classes records sao uma forma de simplificar o envio de alguns atributos evitando get e set e etc.
 * Este tipo utiliza o padrao dto - utilizado em apis para representar os dados que chegam e que devolvemos da api
 * @param nome
 * @param email
 * @param crm
 * @param especialidade
 * @param endereco
 */

//anotacoes sao nosso bean validation onde existe uma verificacao se os dados que estao chegando estao de acordo com as validacoes
public record DadosCadastrosMedicosDTO(
        @NotBlank
        String nome,

        @NotBlank // indica que um atributo do tipo String não pode ser nulo e nem vazio.
        @Email
        String email,

        @NotBlank
        String telefone,

        //notBlank verifica se o valor nao é null / valor nao pode ser uma string vazia ""/ nao pode conter espacos em branco " "
        @NotBlank()
        @Pattern(regexp = "\\d{4,6}", message = "O crm deve contre de 4 a 6 dígitos") //expressao regular //d = digito (4 a 6 digitos)
        String crm,

        @NotNull
        Especialidade especialidade,
                //valid ira validar as annotations dos atributos da classe DadosEndereco
        @NotNull @Valid DadosEndereco endereco
) {
}
