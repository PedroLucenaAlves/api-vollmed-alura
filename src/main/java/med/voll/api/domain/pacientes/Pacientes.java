package med.voll.api.domain.pacientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.controller.dto.DadosAtualizacaoPacienteDTO;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.controller.dto.DadosCadastroPacienteDTO;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Pacientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Pacientes(DadosCadastroPacienteDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoPacienteDTO dados) {

        //atribuindo um if para o spring nao atribuir um valor null ao atualizar dados que sao opcionais no spring
        if(dados.nome() != null) {
            this.nome = dados.nome(); //pega o nome do medico atual e substitui pelo medico passado no dto
        }

        if(dados.telefone() != null) {
            this.telefone = dados.telefone(); //pega o nome do medico atual e substitui pelo medico passado no dto
        }

        if(dados.endereco() != null) {
            this.endereco.atualizarInformaceos(dados.endereco()); //pega o nome do medico atual e substitui pelo medico passado no dto
        }

    }

    public void inativar(){
        this.ativo = false;
    }

}
