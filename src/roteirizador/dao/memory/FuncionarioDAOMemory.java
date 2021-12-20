package roteirizador.dao.memory;
import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.FuncionarioDAO;
import roteirizador.domain.Funcionario;

public class FuncionarioDAOMemory implements FuncionarioDAO {
	
	
	private final List<Funcionario> funcionarios;
	
	FuncionarioDAOMemory() {
		this.funcionarios = new ArrayList <>();
			
	}
	
	@Override
	public void save(Funcionario funcionario) {
		funcionarios.add(funcionario);
	}
	
	@Override
	public void delete(Funcionario funcionario) {
		funcionarios.remove(funcionario);
	
	}
	
	@Override
	public List<Funcionario> getAll(){
		return new ArrayList<Funcionario>(funcionarios);
	}
	
	@Override
	public Funcionario getFuncionario(int id) {
		for(int i = 0; i < funcionarios.size(); i++) {
			if(funcionarios.get(i).getMatricula()==id) {
				return funcionarios.get(i);
			}
		}
		return null;
	}
	@Override
	public Funcionario getEstab(int id) {
		for(int i = 0; i < funcionarios.size(); i++) {
			if(funcionarios.get(i).getEstabelecimento()==id) {
				return funcionarios.get(i);
			}
		}
		return null;
	}
}	
