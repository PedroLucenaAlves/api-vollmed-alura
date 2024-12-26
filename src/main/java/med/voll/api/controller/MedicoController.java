package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import med.voll.api.controller.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.controller.dto.DadosCadastrosMedicosDTO;
import med.voll.api.controller.dto.DadosListagemMedicosDTO;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.controller.dto.DadosDetalhamentoMedicoDTO;
import med.voll.api.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController //apontando ao spring que e uma classe controller
@RequestMapping("medicos") //mapeando url
public class MedicoController {

    @Autowired //spring instancia o atributo
    private MedicoRepository medicoRepository;

    private DadosEndereco endereco;


    @PostMapping
    @Transactional //metodo de inscrita exige uma transacao ativa com o banco           classe do Spring que encapsula o ip do nosso servidor (no caso o localhost)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastrosMedicosDTO dados, UriComponentsBuilder uriComponentsBuilder) { //valid ira validar o meu objeto "dados" com as anotacoes declaradas nas classes dto

        log.debug("Started POST cadastrar /medicos. DadosCadastorsMedicosDTO request: {}", dados);

        var medico = new Medico(dados);

        medicoRepository.save(medico);//converte o parametro dto para um objeto do tipo medico

        //classe Spring que encapsula o endereço da api - path nos passamos apenas o complemento do nosso endereço
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicoDTO(medico)); //retorna o json no corpo da resposta da req e o header

    }

    //metodo de leitura
    @GetMapping                                         //define os padroes de paginacao ao spring caso venha default
    public ResponseEntity<Page<DadosListagemMedicosDTO>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){

        var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicosDTO::new); //exibe apenas os medicos com status ativo = 1
        return ResponseEntity.ok(page); //devolvendo codigo 200 "ok" passando o objeto "page" junto na resposta
    }

    @PutMapping
    @Transactional
    //jpa ao analisar que foi modificado algum campo na transacao, realiza o update sozinha
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDTO dados){

        var medico = medicoRepository.getReferenceById(dados.id()); //estou indo no banco e carregando um medico pelo id
        medico.atualizarInformacoes(dados);

        //devolvendo a informação atualizada
        return ResponseEntity.ok(new DadosDetalhamentoMedicoDTO(medico));

    }

    //id vem na url {parametro dinamico}
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirRegistroMedicos(@PathVariable Long id){ // PathVariable = avisando que o id da url e o mesmo do parametro
        var medico = medicoRepository.getReferenceById(id); //estou indo no banco e carregando um medico pelo id
        medico.excluir(); //ao chamar o metodo estaremos por padrao definindo o status de ativo como false

        return ResponseEntity.noContent().build(); //chama o objeto "noContent", depois constroi o objeto "build" (retorna codigo 204)

    }


    //    @PostMapping //req do tipo post ao chamar nosso endpoint
//    public void cadastrar(@RequestBody DadosCadastrosMedicos dadosCadastrosMedicos){ //informando ao spring que o parametro esta vindo do corpo da req para nao estourar um null
//        System.out.println(dadosCadastrosMedicos);
//    }

}
