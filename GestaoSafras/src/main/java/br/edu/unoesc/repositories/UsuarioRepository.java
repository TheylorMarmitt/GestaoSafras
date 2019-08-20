package br.edu.unoesc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unoesc.model.Usuario;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByNome(String nome);
	
	Usuario findByEmail(String email);
	
}
