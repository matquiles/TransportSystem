package roteirizador.dao.memory;

import roteirizador.dao.ClientsDAO;
import roteirizador.dao.DaoFactory;
import roteirizador.dao.EmbarqueDAO;
import roteirizador.dao.FuncionarioDAO;
import roteirizador.dao.OrderDAO;
import roteirizador.dao.SkuDAO;

public class MemoryDaoFactory extends DaoFactory {
	
	private static SkuDAO skuDAOInstance;
	private static FuncionarioDAO funcionarioDAOInstance;
	private static ClientsDAO clientsDAOInstance;
	private static OrderDAO orderDAOInstance;
	private static EmbarqueDAO embarqueDAOInstance;

	@Override
	protected SkuDAO getSkuDAOImplementation() {
		if (skuDAOInstance == null) {
			skuDAOInstance = new SkuDAOMemory();
		}
		return skuDAOInstance;
	}

	@Override
	protected FuncionarioDAO getFuncionarioDAOImplementation() {
		if (funcionarioDAOInstance == null) {
			funcionarioDAOInstance = new FuncionarioDAOMemory();
		}
		return funcionarioDAOInstance;
	}
	
	@Override
	protected ClientsDAO getClientDAOImplementation() {
		if (clientsDAOInstance == null) {
			clientsDAOInstance = new ClientsDAOMemory();
		}
		return clientsDAOInstance;
	}
	
	
	@Override
	protected OrderDAO getOrderDAOImplementation() {
		if (orderDAOInstance == null) {
			orderDAOInstance = new OrderDAOMemory();
		}
		return orderDAOInstance;
	}
	
	@Override
	protected EmbarqueDAO getEmbarqueDAOImplementation() {
		if (embarqueDAOInstance == null) {
			embarqueDAOInstance = new EmbarqueDAOMemory();
		}
		return embarqueDAOInstance;
	}

}
