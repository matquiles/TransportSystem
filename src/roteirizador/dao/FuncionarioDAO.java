package roteirizador.dao;

import java.util.List;

import roteirizador.domain.Funcionario;

public interface FuncionarioDAO {

	void save(Funcionario funcionario);

	void delete(Funcionario funcionario);

	List<Funcionario> getAll();

	Funcionario getFuncionario(int id);

	Funcionario getEstab(int id);

}