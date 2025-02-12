package med.voll.api.controller.dto;

import med.voll.api.domain.pacientes.Pacientes;

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
