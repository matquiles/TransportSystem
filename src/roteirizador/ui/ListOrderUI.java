package roteirizador.ui;

import java.util.List;

import roteirizador.domain.Pedido;
import roteirizador.validations.FormatarValores;

public class ListOrderUI {

	public void showAll(List<Pedido> pedido) {
		FormatarValores format = new FormatarValores();
		System.out.println("Pedidos digitados no sistema");
			if(pedido == null) {
				System.out.println("Nao existem pedidos digitados");
			} else {
				for (Pedido order : pedido) {
					System.out.println("\nCodigo do Pedido: "+ order.getCodPedido());
					System.out.println("Codigo do cliente: "+ order.getCliente().getCodcliente());
					format.formatarDinheiro(order.getValorTotal()); 
					format.formatarPaletes(order.getPaletizacaoTotal()); 
					System.out.println("Alocado: "+ order.getAlocado());
					System.out.println("Usuario: "+ order.getVendedor().getNome());
					System.out.print("Data da Inclusao: ");
					format.formatarData(order.getData());
					order.mostrarItens();
					
					System.out.println("---------------------------\n---------------------------\n");
					
				}
				
			}
		
	}
	
}
