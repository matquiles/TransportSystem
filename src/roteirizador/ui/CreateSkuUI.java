package roteirizador.ui;

import roteirizador.domain.SKU;

public class CreateSkuUI {
	
	public SKU show() {
		System.out.println("\n---Tela de Cadastro de SKUs---\n");

		System.out.println("Insira o nome do SKU: ");
		String nomeSku = ScannerInput.getInstance().readString();

		System.out.println("Insira a paletizacao: ");
		int paletizacao = ScannerInput.getInstance().readInt();

		System.out.println("Insira o valor: ");
		double valor = ScannerInput.getInstance().readDouble();

		return new SKU(nomeSku, paletizacao, valor);
	}

}
