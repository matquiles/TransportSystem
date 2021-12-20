package roteirizador.dao.memory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roteirizador.dao.OrderDAO;
import roteirizador.domain.Embarque;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;



public class OrderDAOMemory implements OrderDAO {

	private List<Pedido> pedidos;
	public static OrderDAO instance;
	
	OrderDAOMemory() {
		this.pedidos = new ArrayList <>();
			
	}
	
	@Override
	public void save(Pedido pedido) {
		pedidos.add(pedido);
		
	}
	
	@Override
	public void deletePedido(Pedido pedido) {
		pedidos.remove(pedido);
	
	}
	
	@Override
	public List<Pedido> getAll(){
		return new ArrayList<Pedido>(pedidos);
	}
	
	@Override
	public Pedido getPedido(int id) {
		for(int i = 0; i < pedidos.size(); i++) {
			if(pedidos.get(i).getCodPedido()==id) {
				return pedidos.get(i);
			}
		}
		return null;
	}

	public int ultimoCodigoCriado() {
		return 0;
	}

	@Override
	public void saveItemPedido(Pedido pedido,ItemPedido itempedido) {
		// TODO Auto-generated method stub
		
	}

	public void deleteItemPedido(Pedido pedido,int itempedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pedido> getRange(Date datainicial, Date datafinal, int unidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteItemPedido(Pedido pedido, ItemPedido itempedido) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}
