package med.voll.api.controller.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedicoDTO(
        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) {
}
