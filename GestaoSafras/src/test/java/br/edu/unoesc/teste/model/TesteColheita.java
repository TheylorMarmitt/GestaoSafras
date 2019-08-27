package br.edu.unoesc.teste.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.model.Usuario;

class TesteColheita {

	@Test
	void test() {
		Set<Procedimento> procedimentos = new HashSet<Procedimento>();
		Usuario user = new Usuario(1L, "nome", "sobrenome", "email", "senha", LocalDate.of(1990, 01, 01));
		TipoSafra tp = new TipoSafra(1L, "safra1");
		tp.setUsuario(user);
		tp.setAtivo(true);
		
		Safra safra = new Safra();
		safra.setUsuario(user);
		safra.setTipo(tp);
		safra.setCodigo(1L);
		safra.setData(LocalDate.of(2019, 01, 01));
		safra.setDescricao("descricao");
		safra.setEmAtividade(true);
		safra.setObservacao("obs");
		safra.setTamanho(3.0);
		safra.setTipoSemente("tipo semente");
		
		Procedimento pr = new Procedimento();
		pr.setCodigo(1L);
		pr.setData(LocalDate.of(2019, 01, 07));
		pr.setDescricao("descricao");
		pr.setObservacao("obs");
		pr.setQuantidade(4.0);
		pr.setSafra(safra);
		pr.setTipo("tipo");
		pr.setValorGasto(30.0);
		procedimentos.add(pr);
		
		safra.setProcedimentos(procedimentos);
		
		Colheita c = new Colheita(1L, safra, LocalDate.of(2019, 01, 06) , 60.0, 10.0);
		
		assertEquals(new Long(1), c.getCodigo());
		assertEquals(LocalDate.of(2019, 01, 06), c.getDataColheita());
		assertEquals(new Double(60.0), c.getQtdColhida());
		assertEquals(new Double(10.0), c.getValorVenda());
		assertEquals(safra, c.getSafra());
		
		assertEquals("30,00", c.getGastos());
		assertEquals("570,00", c.lucros());
		assertEquals("600,00", c.valorTotalRecebido());
		assertEquals("20,00", c.sacasHectare());
		assertEquals(new Long(5), c.duracaoDias());
		assertEquals("06/01/2019", c.dataFormatada());
		assertEquals("safra1 - descricao", c.tipo());
		assertEquals("06/01/2019 - safra1", c.toString());
		
	}

}
