package roteirizador.ui;
import java.util.List;
import roteirizador.domain.Client;


public class ListClientsUI {
	
	public void show(List<Client> cliente) {
		System.out.println("Tela de Consulta de Clientes");
		for (Client client : cliente) {
			System.out.println("\nCodigo: " + client.getCodcliente());
			System.out.println("Nome: " + client.getNomecliente());
			System.out.println("Endereço: " + client.getEndereco());
			System.out.println("Cidade: " + client.getCidade());
			System.out.println("Estado: " + client.getEstado());
			System.out.println("*******************");
			
		}
		
	}
}

