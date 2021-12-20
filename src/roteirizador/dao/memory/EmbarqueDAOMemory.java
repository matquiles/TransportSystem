package roteirizador.dao.memory;

import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.EmbarqueDAO;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;



public class EmbarqueDAOMemory implements EmbarqueDAO {

	private List<Embarque> embarques;
	public static EmbarqueDAO instance;
	
	EmbarqueDAOMemory(){
		this.embarques = new ArrayList <>();
		
	}

	@Override
	public void save(Embarque embarque) {
		embarques.add(embarque);
	}
	
	@Override
	public void deleteEmbarque(Embarque embarque) {
		embarques.remove(embarque);
	}
	
	@Override
	public List<Embarque> getAll(){
		return new ArrayList<Embarque>(embarques);
	}
	
	@Override
	public Embarque getEmbarque(int id) {
		for(int i = 0; i < embarques.size(); i++) {
			if(embarques.get(i).getCodEmbarque()==id) {
				return embarques.get(i);
			}
		}
		return null;
	}

	@Override
	public int ultimoEmbarqueCriado() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void savePedido(Embarque embarque, Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteItemPedido(Embarque embarque, Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarEmbarque(Embarque embarque) {
		// TODO Auto-generated method stub
		
	}
	
}
