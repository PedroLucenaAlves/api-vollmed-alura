package med.voll.api.controller.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedicoDTO(
        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) {
}
