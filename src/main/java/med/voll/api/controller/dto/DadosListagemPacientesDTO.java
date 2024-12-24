package med.voll.api.controller.dto;

import med.voll.api.endereco.Endereco;
import med.voll.api.medico.Especialidade;
import med.voll.api.medico.Medico;
import med.voll.api.pacientes.Pacientes;

public record DadosListagemPacientesDTO(

        Long id,

        String nome,

        String email,

        String cpf

) {

    public DadosListagemPacientesDTO(Pacientes pacientes){
        this(pacientes.getId(), pacientes.getNome(),pacientes.getEmail(), pacientes.getCpf());
    }

}
