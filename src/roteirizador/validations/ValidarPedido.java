package roteirizador.validations;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.memory.ClientsDAOMemory;
import roteirizador.domain.Pedido;

public  class ValidarPedido {
	

	public ValidarPedido() {
		
	}
	
	public boolean ValidacaoPedido(int codigo) {
	
		FormatarValores format = new FormatarValores();
		boolean controle = false;
		
		List<Pedido> pd = DaoFactory.getOrderDAO().getAll();
		
		for (Pedido pedido : pd) {
			if(codigo == pedido.getCodPedido()) {
				controle = true;
				System.out.println("Pedido: " + pedido.getCodPedido());
				System.out.println("Cod Cliente: " + pedido.getCliente().getCodcliente());
				format.formatarDinheiro(pedido.getValorTotal());
				format.formatarPaletes(pedido.getPaletizacaoTotal());
				pedido.mostrarItens();
				System.out.println("----------------------------------------------\n\n");
				break;
			} else {
				controle = false;
			}
		}

		return controle;

	}
}
