package roteirizador.ui;


import roteirizador.dao.DaoFactory;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;


public class EditOrderUI {

	public EditOrderUI() {
		
	}
	

	public void show() {
		System.out.println("\nInsira o codigo do pedido: ");
		int pedidoDigitado = ScannerInput.getInstance().readInt();
		Pedido pd = DaoFactory.getOrderDAO().getPedido(pedidoDigitado);
		exibirPedido(pd);
		
		System.out.println("\n[1] Incluir item no pedido ");
		System.out.println("[2] Excluir item do pedido ");
		System.out.println("[3] Excluir pedido ");
		
		int escolha = ScannerInput.getInstance().readInt();
		
		switch(escolha) {
		
		case 2: 
		
			System.out.print("Digite a chave do item que deseja excluir: ");
			int excluir = ScannerInput.getInstance().readInt();
			
			ItemPedido item = pd.getAll().get(excluir-1);
			SKU sku = pd.getAll().get(excluir-1).getItem();
			
			pd.removerItem(item, sku);
			DaoFactory.getOrderDAO().deleteItemPedido(pd, item);
			
			
			exibirPedido(pd);

			break;
		
			
		case 1:
			System.out.print("Digite o SKU que deseja incluir no pedido: ");
			int incluirPedido = ScannerInput.getInstance().readInt();
			
			System.out.print("Digite a quantidade: ");
			int qtd = ScannerInput.getInstance().readInt();
			
			ItemPedido incluir = new ItemPedido(qtd, DaoFactory.getSkuDAO().getSKU(incluirPedido));
			
			try {
				pd.addItem(incluir);
				pd.setValorTotal(qtd, DaoFactory.getSkuDAO().getSKU(incluirPedido).getValor() );
				pd.setPaletizacaoTotal(qtd, DaoFactory.getSkuDAO().getSKU(incluirPedido).getPaletizacao());
				DaoFactory.getOrderDAO().saveItemPedido(pd, incluir);
				
			} catch (NullPointerException n) {
				System.out.println("\n>>>Nao foi possivel incluir o pedido. ");
			}
			
			
			break;
		case 3:
			System.out.print("Confirma a exclusao do pedido " + pd.getCodPedido() + "? ");
			
			String excluirPedido = ScannerInput.getInstance().readString();
			
			if(excluirPedido.equals("s") ||excluirPedido.equals("S")) {
				DaoFactory.getOrderDAO().deletePedido(pd);
				System.out.println("\nEmbarque excluido com sucesso!");
			}
			break;
	
		}
	}
	
	public void exibirPedido(Pedido pd) {
	
		pd.mostrarItens();
		
	}



	
}
