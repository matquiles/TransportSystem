package roteirizador.ui;

import java.util.List;

import roteirizador.domain.SKU;

public class ListSkuUI {
	
	public void show(List<SKU> skus) {
		System.out.println("Tela de Consulta de SKUs");
		for (SKU sku : skus) {
			System.out.println("Codigo: " + sku.getCodSKU());
			System.out.println("Nome: " + sku.getNomeSku());
			System.out.println("Paletizacao: " + sku.getPaletizacao());
			System.out.println("Valor unitario: " + sku.getValor());
			System.out.println("---\n\n");
		}

	}

}
