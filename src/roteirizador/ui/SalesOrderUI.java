package roteirizador.ui;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.ui.controller.MenuController;
import roteirizador.validations.FormatarValores;
import roteirizador.validations.ValidarCliente;
import roteirizador.validations.ValidarSKU;

public class SalesOrderUI {
	
	public Pedido show() {
		System.out.println("-----Digitar pedidos-----\n");
		
		ValidarCliente validarcliente = new ValidarCliente();
		ValidarSKU validarsku = new ValidarSKU();
		FormatarValores format = new FormatarValores();
		
		boolean digitar = true;
		

		
		try {
			
			System.out.print("Insira o codigo do cliente: ");
			int codcliente = ScannerInput.getInstance().readInt();
			
			if (validarcliente.validacaocliente(codcliente) == false) {
				System.out.println("cliente nao encontrado");
				return null;
			} 
			
			Pedido pd = new Pedido(DaoFactory.getClienteDAO().getClient(codcliente));
		
			while(digitar == true) {
			
				System.out.print("Insira o codigo do SKU: ");
				int sku = ScannerInput.getInstance().readInt();
				
				if (validarsku.validacaosku(sku) == false) {
					System.out.println("sku nao encontrado");
					return null;
				}
				System.out.print("Quantidade: ");
				int qtdsku = ScannerInput.getInstance().readInt();
				ItemPedido item = new ItemPedido(qtdsku, DaoFactory.getSkuDAO().getSKU(sku));
				pd.addItem(item);
				pd.setValorTotal(qtdsku, DaoFactory.getSkuDAO().getSKU(sku).getValor());
				pd.setPaletizacaoTotal(qtdsku, DaoFactory.getSkuDAO().getSKU(sku).getPaletizacao());
				
				System.out.println("Digite S para inserir novo item ou N para finalizar pedido? [S/N] " );
				String escolha = ScannerInput.getInstance().readString();
				
				if("n".equals(escolha) ||"N".equals(escolha) ) {
					digitar = false;
				} else if ("s".equals(escolha) || "S".equals(escolha)){
					digitar = true;
				} else {
					System.out.println("Escolha invalida!");
					digitar = false;
				}
				
			}
		
			System.out.println("\nPedido digitado: "+ pd.getCodPedido());
			System.out.println("Codigo do cliente: "+ pd.getCliente().getCodcliente());
			format.formatarDinheiro(pd.getValorTotal()); 
			format.formatarPaletes(pd.getPaletizacaoTotal()); 
			System.out.println("Comicao do vendedor: " + pd.getVendedor().getComicao() * pd.getValorTotal());
			pd.mostrarItens();
			System.out.println("----------------------------------------------");
			
			return pd;
			
			} catch (ClassCastException c) {
				System.out.println("\n>>>Para criar uma venda, faca login como vendedor.");
				new MenuController(new MenuUI()).launch();
		}
		return null;
	}
	
}
