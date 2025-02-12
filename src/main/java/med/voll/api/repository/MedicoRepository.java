package med.voll.api.repository;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Um repositório fica entre as regras de negócio e a camada de persistência:
 *
 * Ele provê uma interface para as regras de negócio onde os objetos são acessados como em uma coleção;
 * Ele usa a camada de persistência para gravar e recuperar os dados necessários para persistir e recuperar os objetos de negócio.
 */

                                    //tipo da entidade e tipo da chave primaria da entidade
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

}
