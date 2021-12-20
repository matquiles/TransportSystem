package roteirizador.dao;

import java.util.Date;
import java.util.List;

import roteirizador.domain.Embarque;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;

public interface OrderDAO {

	void save(Pedido pedido);

	void deletePedido(Pedido pedido);

	List<Pedido> getAll();

	Pedido getPedido(int id);
	
	int ultimoCodigoCriado();
	
	void saveItemPedido(Pedido pedido, ItemPedido itempedido);
	
	void deleteItemPedido(Pedido pedido, ItemPedido itempedido);
	
	void atualizarPedido(Pedido pedido);
	
	List<Pedido> getRange(Date datainicial, Date datafinal, int unidade);
	



}