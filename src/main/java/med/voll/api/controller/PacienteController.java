package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.controller.dto.DadosAtualizacaoPacienteDTO;
import med.voll.api.controller.dto.DadosListagemPacientesDTO;
import med.voll.api.controller.dto.DadosCadastroPacienteDTO;
import med.voll.api.pacientes.Pacientes;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroPacienteDTO dados) {
        System.out.println("dados recebido: " +dados);
        pacienteRepository.save(new Pacientes(dados));
    }

    @GetMapping
    public Page<DadosListagemPacientesDTO> listarPacientes(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao){
            return pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientesDTO::new);
    }

    @PutMapping
    @Transactional
    //jpa ao analisar que foi modificado algum campo na transacao, realiza o update sozinha
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPacienteDTO dados){

        var paciente = pacienteRepository.getReferenceById(dados.id()); //estou indo no banco e carregando um medico pelo id
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remover(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();
    }

}
