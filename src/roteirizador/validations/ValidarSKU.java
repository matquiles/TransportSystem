package roteirizador.validations;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.SKU;

public class ValidarSKU {

	public ValidarSKU() {
	}
	
	public boolean validacaosku (int cod) {
		
		boolean controle = false;
		
		List<SKU> sku = DaoFactory.getSkuDAO().getAll();
		
		for(int i = 0; i<sku.size();i++) {
			if(cod == sku.get(i).getCodSKU()) {
				controle = true;
				System.out.println("--Codigo: " + sku.get(i).getCodSKU());
				System.out.println("--Nome: " + sku.get(i).getNomeSku());
				System.out.println("--Paletizacao: " + sku.get(i).getPaletizacao());
				System.out.println("--Valor unitario: " + sku.get(i).getValor());
				System.out.println("---\n");
				break;
			} else {
				controle = false;
			}
		}
		return controle;
	}
	
		
}
	

	
	
