package br.edu.unoesc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.TipoSafra;

@Repository
public interface TipoSafraRepository extends JpaRepository<TipoSafra, Long> {

	@Query("select t from TipoSafra t where t.ativo = true and t.usuario.codigo = :codigo")
	List<TipoSafra> findByAtivos(Long codigo);
	

}
