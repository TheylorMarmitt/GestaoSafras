package br.edu.unoesc.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.service.ColheitaService;

@PageTitle("Gestão de Safra")
@Route("historico-procedimentos")
@HtmlImport("frontend://styles/tema.html")
public class HistoricoProcedimentos extends VerticalLayout  implements HasUrlParameter<Long>{

	private static final long serialVersionUID = -8065565972521990164L;

	private Grid<Procedimento> grid = new Grid<>();
	private Button voltar = new Botoes().voltar();
	
	private ColheitaService colheitaService;
	private Colheita colheita;
	
	@Autowired
	public HistoricoProcedimentos(ColheitaService colheitaService){
		this.colheitaService = colheitaService;
	}
	
	private void criar() {
		grid.setItems(colheita.getSafra().getProcedimentos());
		grid.addColumn(Procedimento::getDescricao).setHeader("Descrição");
		grid.addColumn(Procedimento::getTipo).setHeader("Tipo do procedimento");
		grid.addColumn(Procedimento::dataFormatada).setHeader("Data");
		grid.addColumn(Procedimento::getValorGastoFormatado).setHeader("Valor gasto");
		grid.addColumn(Procedimento::getQuantidade).setHeader("Quantidade");
		grid.addColumn(Procedimento::getObservacao).setHeader("Obsevação");
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.getColumns().forEach(column -> column.setAutoWidth(true));
		grid.recalculateColumnWidths();
		
		voltar.setThemeName("primary");
		
		voltar.addClickListener(e -> {
			voltar.getUI().ifPresent(ui -> ui.navigate("historico"));
		});
	}
	
	private Div mostrarTotal() {
		Div div = new Div();
		div.setWidthFull();
		div.getStyle().set("text-align", "right");

		H3 gasto = new H3("Total gasto: R$ " + colheita.getGastos());
		gasto.getStyle().set("color", "red");
		div.add(gasto);
		return div;
	}

	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		this.colheita = this.colheitaService.buscarPorCodigo(parameter);
		criar();
		add(new H2("Procedimentos por safra"), grid, mostrarTotal(), voltar);
	}
}
