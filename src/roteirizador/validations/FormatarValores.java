package roteirizador.validations;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatarValores {
	
	Locale localeBR = new Locale("pt","BR");
	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy"); 
	
	public FormatarValores() {
		
	}
	
	public String formatarDinheiro(double valor) {
		return NumberFormat.getCurrencyInstance().format(valor);
		
	}
	
	public String formatarPaletes(double valor) {
		return NumberFormat.getNumberInstance(localeBR).format(valor);
		
	}

	public String formatarPercentual(double valor) {
		return NumberFormat.getPercentInstance(localeBR).format(valor);
	
	}
	
	public String formatarData (Date data) {
		return formatoData.format(data);
		
	}

}
