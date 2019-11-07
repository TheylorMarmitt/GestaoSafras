package br.edu.unoesc.views;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.TooltipBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.tooltip.builder.YBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Navegacao;
import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.service.ColheitaService;

@PageTitle("Gestão Safras")
@Route("inicio")
@HtmlImport("frontend://styles/tema.html")
public class PaginaInicio extends VerticalLayout{

	private static final long serialVersionUID = -1810013373575489386L;
	
	private ComboBox<Colheita> comboColheita = new ComboBox<Colheita>(); 
	private Navegacao nav = new Navegacao();
	
	private ColheitaService colheitaService;
	
	@Autowired
	public PaginaInicio(ColheitaService colheitaService) {
		
		this.colheitaService = colheitaService;
		
		Div grafico = new Div();
		grafico.setWidthFull();
		
		Colheita ultima = colheitaService.ultimaColheita();
		
		if(ultima == null) {
			add(nav.menu(1), 
					new H2("Nenhuma safra finalizada"),
					new H5("Finalize uma safra para ter seus detalhes e gráfico"));
		}else {
			comboColheita.setItems(this.colheitaService.colheitas());
			comboColheita.setLabel("Colheita");
			comboColheita.setWidth("250px");
			comboColheita.setValue(ultima);
			
			grafico.add(barras(comboColheita.getValue()));
			add(nav.menu(1), 
					new H2("Detalhes"), new H5("Selecione uma colheita para ver seus detalhes"),
					comboColheita ,grafico);
			
			comboColheita.addValueChangeListener(e ->{
				grafico.removeAll();
				grafico.add(barras(e.getValue()));
			});
		}
	}
	

    public Div barras(Colheita selecionada) {
    	Div div = new Div();
    	Div d = new Div();
    	
    	d.add(new H4("Duração: "+ selecionada.duracaoDias() + " dias"));
    	d.add(new H4("Sacas/Hectare: "+ selecionada.sacasHectare()));
    	d.add(new H4("Lucros: "+ selecionada.lucros()));
    	d.add(new H4("Total gasto: "+ selecionada.getGastos()));
    	d.add(new H4("Observação: "+ selecionada.getSafra().getObservacao()));
    	d.getStyle().set("float", "left");
    	d.setMaxWidth("350px");
    	
    	Set<Procedimento> novo = selecionada.getSafra().getProcedimentos();
    	String[] legendas = new String[novo.size()];
    	Double[] valores = new Double[novo.size()];

    	int contador = 0;
    	for (Procedimento p : novo) {
    		legendas[contador] = p.getDescricao();
    		valores[contador] = p.getValorGasto();
    		contador++;
		}
    	
    	Div componente = new Div();
    	componente.setMinWidth("350px");
    	componente.setWidth("70%");
    	componente.getStyle().set("float", "left");
    	
        ApexCharts barChart = new ApexCharts()
                .withChart(ChartBuilder.get()
                        .withType(Type.bar)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(false)
                                .withColumnWidth("20%")
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false).build())
                .withStroke(StrokeBuilder.get()
                        .withShow(true)
                        .withWidth(2.0)
                        .withColors("transparent")
                        .build())
                .withSeries(new Series("Valor gasto", valores))
                .withYaxis(YAxisBuilder.get()
                        .withTitle(TitleBuilder.get()
                                .withText("$ (Reais)")
                                .build())
                        .build())
                .withXaxis(XAxisBuilder.get().withCategories(legendas).build())
                .withFill(FillBuilder.get()
                        .withOpacity(1.0).build())
                .withTooltip(TooltipBuilder.get()
                        .withY(YBuilder.get()
                                .build())
                        .build());
        
        componente.add(barChart);
        div.add(d, componente);
        return div;
    }
    

}