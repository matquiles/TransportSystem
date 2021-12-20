package roteirizador.graphics.controller;


import roteirizador.graphics.SearchSKUUI;

import java.text.ParseException;

import roteirizador.CargaInicial;
import roteirizador.graphics.CreateEmbarqueUI;
import roteirizador.graphics.CreateOrderUI;
import roteirizador.graphics.EditEmbarqueUI;
import roteirizador.graphics.EditOrderUI;
import roteirizador.graphics.ExportOrderUI;
import roteirizador.graphics.RegisterClientUI;
import roteirizador.graphics.RegisterSKUUI;
import roteirizador.graphics.SearchClientUI;
import roteirizador.graphics.SearchEmbarqueUI;
import roteirizador.graphics.SearchOrderUI;


public class MainMenuController {

	public void exibirCadastroCliente() {
		new RegisterClientUI();
		
	}

	public void exibirConsultaCliente() {
		new SearchClientUI();
	}

	public void exibirCadastroSKU() {
		new RegisterSKUUI();
		
	}

	public void exibirConsultaSKU() {
		new SearchSKUUI();

	}

	public void exibirDigitarPedido() {
		new CreateOrderUI();
		
	}

	public void exibirConsultarPedido() {
		new SearchOrderUI();
		
	}

	public void exibirEditarPedido() {
		new EditOrderUI();
	}

	public void exibirExportarPedido() {
		new ExportOrderUI();
		
	}

	public void exibirCriarEmbarque() {
		new CreateEmbarqueUI();
	
	}

	public void exibirExibirEmbarque() {
		new SearchEmbarqueUI();
		
	}

	public void exibirEditarEmbarque() {
		new EditEmbarqueUI();
		
	}

}
