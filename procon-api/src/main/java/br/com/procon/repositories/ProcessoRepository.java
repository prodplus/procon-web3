package br.com.procon.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Processo;
import br.com.procon.models.enums.Situacao;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	List<Processo> findAllByDataBetween(LocalDate inicio, LocalDate fim);

	Page<Processo> findAllByAutosContaining(String autos, Pageable pageable);

	Page<Processo> findAllByConsumidoresDenominacaoContainingIgnoreCaseOrConsumidoresCadastroContaining(
			String denominacao, String cadastro, Pageable pageable);

	List<Processo> findAllByConsumidoresId(Integer idConsumidor, Sort by);

	Page<Processo> findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCaseOrFornecedoresCnpjContaining(
			String fantasia, String razao, String cnpj, Pageable pageable);

	Page<Processo> findAllByFornecedoresId(Integer idFornecedor, Pageable pageable);

	Page<Processo> findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(Situacao encerrado,
			Situacao resolvido, Situacao naoResolvido, Pageable pageable);

	Page<Processo> findAllBySituacao(Situacao situacao, Pageable pageable);

	List<Processo> findAllByFornecedoresIdAndSituacao(Integer idFornecedor, Situacao situacao);

	List<Processo> findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(Situacao resolvido,
			Situacao naoResolvido, Situacao encerrado);

	List<Processo> findAllBySituacao(Situacao situacao);

	List<Processo> findAllBySituacaoAndDataBetween(Situacao situacao, LocalDate inicio,
			LocalDate fim);

}
