package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.controller.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.controller.dto.DadosCadastrosMedicosDTO;
import med.voll.api.controller.dto.DadosListagemMedicosDTO;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController //apontando ao spring que e uma classe controller
@RequestMapping("medicos") //mapeando url
public class MedicoController {

    @Autowired //spring instancia o atributo
    private MedicoRepository medicoRepository;

    private DadosEndereco endereco;


    @PostMapping
    @Transactional //metodo de inscrita exige uma transacao ativa com o banco
    public void cadastrar(@RequestBody @Valid DadosCadastrosMedicosDTO dados) { //valid ira validar o meu objeto "dados" com as anotacoes declaradas nas classes dto

        medicoRepository.save(new Medico(dados));//converte o parametro dto para um objeto do tipo medico
    }

    //metodo de leitura
    @GetMapping                                         //define os padroes de paginacao ao spring caso venha default
    public Page<DadosListagemMedicosDTO> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){

        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicosDTO::new); //exibe apenas os medicos com status ativo = 1
    }

    @PutMapping
    @Transactional
    //jpa ao analisar que foi modificado algum campo na transacao, realiza o update sozinha
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDTO dados){

        var medico = medicoRepository.getReferenceById(dados.id()); //estou indo no banco e carregando um medico pelo id
        medico.atualizarInformacoes(dados);
    }

    //id vem na url {parametro dinamico}
    @DeleteMapping("/{id}")
    @Transactional
    public void excluirRegistroMedicos(@PathVariable Long id){ // PathVariable = avisando que o id da url e o mesmo do parametro
        var medico = medicoRepository.getReferenceById(id); //estou indo no banco e carregando um medico pelo id
        medico.excluir(); //ao chamar o metodo estaremos por padrao definindo o status de ativo como false
        //medicoRepository.deleteById(id); se acionarmos esse trecho ele ira apagar do banco de dados o id marcado e a ideia e apenas definir como inativo
    }


    //    @PostMapping //req do tipo post ao chamar nosso endpoint
//    public void cadastrar(@RequestBody DadosCadastrosMedicos dadosCadastrosMedicos){ //informando ao spring que o parametro esta vindo do corpo da req para nao estourar um null
//        System.out.println(dadosCadastrosMedicos);
//    }

}
