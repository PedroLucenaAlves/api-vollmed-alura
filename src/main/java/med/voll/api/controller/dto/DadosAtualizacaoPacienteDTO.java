package med.voll.api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPacienteDTO(
        @NotNull
        Long id,

        String nome,

        String telefone,

        @Valid DadosEndereco endereco) {
}
