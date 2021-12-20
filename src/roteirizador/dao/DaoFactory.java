package roteirizador.dao;


import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.dao.memory.MemoryDaoFactory;


public abstract class DaoFactory {
	
	private static DaoFactory instance;

	public synchronized static SkuDAO getSkuDAO() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance.getSkuDAOImplementation();
	}
	
	public synchronized static FuncionarioDAO getFuncionarioDAO() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance.getFuncionarioDAOImplementation();
	}
	
	
	public synchronized static ClientsDAO getClienteDAO() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance.getClientDAOImplementation();
	}
	
	
	public synchronized static OrderDAO getOrderDAO() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance.getOrderDAOImplementation();
	}
	
	public synchronized static EmbarqueDAO getEmbarqueDAO() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance.getEmbarqueDAOImplementation();
	}


	
	private static DaoFactory createInstance() {
		return new DatabaseDaoFactory(); //alterar essa linha para mudar de memoria para DB - MemoryDaoFactory ou DatabaseDaoFactory
	}
	

	protected abstract SkuDAO getSkuDAOImplementation();

	protected abstract FuncionarioDAO getFuncionarioDAOImplementation();
	
	protected abstract ClientsDAO getClientDAOImplementation();
	
	protected abstract OrderDAO getOrderDAOImplementation();
	
	protected abstract EmbarqueDAO getEmbarqueDAOImplementation();
	


	
	



	


}
