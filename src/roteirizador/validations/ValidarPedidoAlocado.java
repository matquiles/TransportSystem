package roteirizador.validations;

import roteirizador.domain.Pedido;

public class ValidarPedidoAlocado {

	public ValidarPedidoAlocado() {
		
	}
	
	public boolean validaAlocado(Pedido p) {
		
		boolean validacao;
		
		if(p.getAlocado() == false) {
			validacao = false;
		} else {
			validacao = true;
		}
	
		return validacao;
	}
}
