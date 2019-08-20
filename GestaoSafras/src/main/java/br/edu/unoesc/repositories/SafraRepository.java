package br.edu.unoesc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Safra;

@Repository
public interface SafraRepository extends JpaRepository<Safra, Long> {

	List<Safra> findSafrasByUsuarioCodigo(long codigo);
	
	@Query("select s from Safra s where s.emAtividade = false and s.usuario.codigo = :codigo")
	List<Safra> findSafrasFinalizadasPorUsuario(long codigo);
	
	@Query("select c.safra from Colheita c where c.safra.usuario = ?1 and "
			+ "c.dataColheita = (select max(co.dataColheita) from Colheita co where c.codigo = co.codigo)")
	Safra findLastColhida(Long codUsuario);
	
	List<Safra> findByEmAtividadeTrue();
}
