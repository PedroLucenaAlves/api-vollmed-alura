package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.controller.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.controller.dto.DadosCadastrosMedicosDTO;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //hashcode em cima do id
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    private String telefone;

    private String crm;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded //Indica que essa classe pode ser incorporada em outra entidade (analisar classe endereco)
    private Endereco endereco;

    public Medico(DadosCadastrosMedicosDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedicoDTO dados) {

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

    public void excluir() {
        this.ativo = false;
    }
}
