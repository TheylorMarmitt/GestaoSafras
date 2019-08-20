package br.edu.unoesc.conversor;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Arredondador {

	public String arredondar(Double valor) {
	   DecimalFormat df = new DecimalFormat("#,##0.00");
	   df.setRoundingMode(RoundingMode.HALF_UP);
	   return df.format(valor);
	}
}
