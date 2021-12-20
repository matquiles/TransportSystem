package roteirizador;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Client;
import roteirizador.domain.Funcionario;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.Roteirizador;
import roteirizador.domain.SKU;
import roteirizador.domain.Vendedor;

import java.util.Random;

public class CargaInicial {
	
	
	public CargaInicial() {
		
	}
	
	
	public void cargaPedidos() {
		
		Random r = new Random();
		
		boolean add = true;
		
		for(int i = 0; i<1000; i++) {
	
			Pedido pd = new Pedido(DatabaseDaoFactory.getClienteDAO().getClient(r.nextInt(6) + 1001));
			do {
				int qtd = r.nextInt(50)+1;
				int sku = r.nextInt(8)+1;
				ItemPedido itpd = new ItemPedido(qtd, DatabaseDaoFactory.getSkuDAO().getSKU(sku));
				pd.addItem(itpd);
				pd.setValorTotal(qtd, DatabaseDaoFactory.getSkuDAO().getSKU(sku).getValor());
				pd.setPaletizacaoTotal(qtd, DatabaseDaoFactory.getSkuDAO().getSKU(sku).getPaletizacao());
				add = r.nextBoolean();
			} while(add==true);
			
			adicionarPedido(pd);
		}
	
	}
	
	
	private static void adicionarPedido(Pedido pd) {
		DatabaseDaoFactory.getOrderDAO().save(pd);
	}
	


}

