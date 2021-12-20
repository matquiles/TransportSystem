package roteirizador.domain;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.validations.Sessao;


public class Autenticador {

	
	
	public Autenticador() {
	}

	

	public boolean autenticacao (int matriculaDigitada, String senhaDigitada) {
		
		List<Funcionario> listadefuncionarios = DaoFactory.getFuncionarioDAO().getAll();
		
		boolean controle = false;

		for(int i = 0; i < listadefuncionarios.size() ; i++) {
			
			
			if (matriculaDigitada == listadefuncionarios.get(i).getMatricula() && "abc".equals(senhaDigitada)) {
				controle = true;
				System.out.println("Usuario: " + listadefuncionarios.get(i).getNome() + " - " + listadefuncionarios.get(i).getClass());
				Sessao sessao = Sessao.getInstance();
				sessao.setUsuario(listadefuncionarios.get(i));
				break;
			} else {
				controle = false;
			}
		}
		return controle;
	}
}


	
