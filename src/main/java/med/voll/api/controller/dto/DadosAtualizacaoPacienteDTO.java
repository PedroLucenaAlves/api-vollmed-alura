package med.voll.api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPacienteDTO(
        @NotNull
        Long id,

        String nome,

        String telefone,

        @Valid DadosEndereco endereco) {
}
