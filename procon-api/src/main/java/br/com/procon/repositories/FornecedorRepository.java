package br.com.procon.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Fornecedor;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

	Page<Fornecedor> findAllByCnpjContaining(String parametro, Pageable pageable);

	Page<Fornecedor> findAllByFantasiaContainingIgnoreCaseOrRazaoSocialContainingIgnoreCase(
			String parametro, String parametro2, Pageable pageable);

	boolean existsByFantasia(String fantasia);

	boolean existsByCnpj(String cnpj);

}
