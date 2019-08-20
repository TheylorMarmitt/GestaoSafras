package br.edu.unoesc.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Colheita;

@Repository
public interface ColheitaRepository extends JpaRepository<Colheita, Long>  {

	
	@Query("select c from Colheita c where c.safra.usuario.codigo = ?1 and "
			+ "c.dataColheita = (select max(co.dataColheita) from Colheita co)")
	Colheita findLastColheita(Long codigo);
	
	Colheita findByCodigo(Long codigo);
	
	@Query("select c from Colheita c where c.safra.usuario.codigo = ?1")
	List<Colheita> findColheitas(Long codigo);
	
	@Query("select c from Colheita c where c.safra.usuario.codigo = ?1 and c.safra.tipo.nome like %?2% and "
			+ "c.dataColheita between ?3 and ?4")
	List<Colheita> findColheitasFiltradas(Long codigo, String nome, LocalDate dataInicio, LocalDate dataFim);

	@Query("select c from Colheita c where c.safra.usuario.codigo = ?1 and c.dataColheita between ?2 and ?3")
	List<Colheita> findColheitasEntreDatas(Long codigo, LocalDate value, LocalDate value2);

	@Query("select c from Colheita c where c.safra.usuario.codigo = ?1 and c.safra.tipo.nome like %?2%")
	List<Colheita> findColheitasPorTipo(Long codigo, String value);
	
	
}
