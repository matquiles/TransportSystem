package roteirizador.ui;
import java.io.FileWriter;
import java.io.IOException;	
import java.io.PrintWriter;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Pedido;


public class ExportOrderUI {


	public void export() throws IOException {
		
		System.out.println("Selecione a opcao abaixo");
		System.out.println("\n[1] Exportar Todos");
		System.out.println("\n[2] Exportar por cliente");
		System.out.println("\n[3] Arquivo para roteirizacao (pedido que ainda nao esta em embarque) ");
		
		int escolha = ScannerInput.getInstance().readInt();
		
		//FileWriter arq = new FileWriter("/home/matheus/eclipse-workspace/CloneTotvs/pedidos.txt");
		FileWriter arq = new FileWriter("/home/matheus/workspaces/estagio/CloneTotvs/pedidos.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		List<Pedido> listaDePedidos = DaoFactory.getOrderDAO().getAll();
		
		 switch(escolha) {
		 
		 case 1:
			 
			for (Pedido pedido : listaDePedidos) {
				gravarArq.print(pedido.getCodPedido()+",");
				gravarArq.print(pedido.getCliente().getCodcliente()+",");
				gravarArq.print(pedido.getPaletizacaoTotal()+"\n");
				
			}
			
				arq.close();
				System.out.println("\nArquivo exportado com sucesso");
				break;
			
		 case 2:
			 
			 System.out.print("\nInsira o codigo de cliente: ");
			 int cliente = ScannerInput.getInstance().readInt();
			 	
			try {
				 for (Pedido pedido : listaDePedidos) {
					
					 if(pedido.getCliente() == DaoFactory.getClienteDAO().getClient(cliente) ) {
						gravarArq.print(pedido.getCodPedido()+",");
						gravarArq.print(pedido.getCliente().getCodcliente()+",");
						gravarArq.print(pedido.getPaletizacaoTotal()+"\n");
					 } 
			}
				 arq.close();
				System.out.println("\nArquivo exportado com sucesso");
				break;
			} catch (NullPointerException n) {
				n.getMessage();
				System.out.println(">>>Erro ao exportar");
				break;
			}
			
		 case 3:
			 
		 	
			try {
				 for (Pedido pedido : listaDePedidos) {
					
					 if(pedido.getAlocado()== false) {
						gravarArq.print(pedido.getCodPedido()+",");
						gravarArq.print(pedido.getCliente().getCodcliente()+",");
						gravarArq.print(pedido.getPaletizacaoTotal()+"\n");
					 } 
			}
				 arq.close();
				System.out.println("\nArquivo exportado com sucesso");
				break;
			} catch (NullPointerException n) {
				n.getMessage();
				System.out.println(">>>Erro ao exportar");
				break;
			}


			
		 }
			
			
		
		
	}
	

}
