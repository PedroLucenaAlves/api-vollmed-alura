package med.voll.api.repository;

import med.voll.api.domain.pacientes.Pacientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository <Pacientes, Long> {
    Page<Pacientes> findAllByAtivoTrue(Pageable paginacao);
}
