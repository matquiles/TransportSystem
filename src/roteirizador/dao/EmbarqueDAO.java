package roteirizador.dao;

import java.util.List;

import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;

public interface EmbarqueDAO {

	void save(Embarque embarque);

	void deleteEmbarque(Embarque embarque);

	List<Embarque> getAll();

	Embarque getEmbarque(int id);
	
	int ultimoEmbarqueCriado();
	
	void savePedido(Embarque embarque, Pedido pedido);
	
	void deleteItemPedido(Embarque embarque, Pedido pedido);
	
	void atualizarEmbarque(Embarque embarque);
	
	

}