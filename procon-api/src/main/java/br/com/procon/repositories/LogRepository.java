package br.com.procon.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.procon.models.Log;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

	List<Log> findAllByDataBetween(LocalDateTime inicio, LocalDateTime fim, Sort by);

}
