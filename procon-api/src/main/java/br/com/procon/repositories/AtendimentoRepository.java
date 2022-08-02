package br.com.procon.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Atendimento;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

	Page<Atendimento> findAllByConsumidoresDenominacaoContainingIgnoreCaseOrConsumidoresCadastroContaining(
			String parametro, String parametro2, Pageable pageable);

	Page<Atendimento> findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
			String parametro, String parametro2, Pageable pageable);

	Long countByDataAfter(LocalDate of);

	List<Atendimento> findAllByDataBetween(LocalDate inicio, LocalDate fim);

}
