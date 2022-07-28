package br.com.procon.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Consumidor;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Integer> {

	Page<Consumidor> findAllByCadastroContaining(String parametro, Pageable pageable);

	Page<Consumidor> findAllByDenominacaoContaining(String parametro, Pageable pageable);

	boolean existsByCadastro(String cadastro);

}
