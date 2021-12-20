package roteirizador.validations;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.memory.ClientsDAOMemory;
import roteirizador.dao.memory.OrderDAOMemory;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;

public  class ValidarEmbarque {
	

	public ValidarEmbarque() {
		
	}
	
	public boolean ValidacaoEmbarque(int codigo) {
	
	
		boolean controle = false;
		
		List<Embarque> embarque = DaoFactory.getEmbarqueDAO().getAll();
		FormatarValores format = new FormatarValores();
		
		for (Embarque emb : embarque) {
			if(codigo == emb.getCodEmbarque()) {
				List<Pedido> pd = emb.getAll();
				controle = true;
				
					
					for(Pedido order : pd) {
						System.out.println("\nCodigo do Pedido: "+ order.getCodPedido());
						System.out.println("Codigo do cliente: "+ order.getCliente().getCodcliente());
						format.formatarDinheiro(order.getValorTotal()); 
						format.formatarPaletes(order.getPaletizacaoTotal()); 
						System.out.println("Alocado: "+ order.getAlocado());
						order.mostrarItens();
					}
				
				System.out.println("----------------------------------------------\n\n");
				break;
			} else {
				controle = false;
			}
		}

		return controle;

	}
}
