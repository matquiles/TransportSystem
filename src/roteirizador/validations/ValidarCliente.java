package roteirizador.validations;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Client;

public class ValidarCliente {
	
	public ValidarCliente() {
		
	}
	
	public boolean validacaocliente (int cod) {
		
		boolean controle = false;
		
		List<Client> clientes = DaoFactory.getClienteDAO().getAll();
		
		for(int i = 0; i<clientes.size();i++) {
			if(cod == clientes.get(i).getCodcliente()) {
				controle = true;
				System.out.println("--Codigo: " + clientes.get(i).getCodcliente());
				System.out.println("--Nome: " + clientes.get(i).getNomecliente());
				System.out.println("--Endereco: " + clientes.get(i).getEndereco());
				System.out.println("--Cidade: " + clientes.get(i).getCidade());
				System.out.println("--Estado: " + clientes.get(i).getEstado());
				System.out.println("---\n");
				break;
			} else {
				controle = false;
			}
		}
		return controle;
	}
	

}
