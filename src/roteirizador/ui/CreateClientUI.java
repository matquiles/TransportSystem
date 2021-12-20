package roteirizador.ui;

import roteirizador.domain.Client;


public class CreateClientUI {
	
	public Client show() {
		System.out.println("\n---Tela de Cadastro de Clientes---\n");

		System.out.print("Nome do Cliente: ");
		String nomeCliente = ScannerInput.getInstance().readString();

		System.out.print("Endereco: ");
		String enderecoCliente = ScannerInput.getInstance().readString();
		
		System.out.print("Cidade: ");
		String cidadeCliente = ScannerInput.getInstance().readString();
		
		System.out.print("Estado: ");
		String estadoCliente = ScannerInput.getInstance().readString();
		
		System.out.print("Unidade: ");
		int unidade = ScannerInput.getInstance().readInt();

		return new Client(nomeCliente, enderecoCliente, cidadeCliente, estadoCliente, unidade);
	}
	

}
