package roteirizador.ui;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;
import roteirizador.ui.controller.MenuController;
import roteirizador.validations.ValidarEmbarque;

public class EditEmbarqueUI {

	public EditEmbarqueUI() {

	}

	public void show() {

		boolean continuar = true;
		System.out.println("\nInsira o codigo do embarque: ");
		int embarqueDigitado = ScannerInput.getInstance().readInt();
		ValidarEmbarque ve = new ValidarEmbarque();

		while(continuar == true) {	
			try { 
				if(ve.ValidacaoEmbarque(embarqueDigitado) == false) {
					System.out.println("\nEmbarque Incorreto");
					new MenuController(new MenuUI()).launch();
				} 	


				Embarque embarque = DaoFactory.getEmbarqueDAO().getEmbarque(embarqueDigitado);


				System.out.println("\n[1] Excluir pedido do embarque ");
				System.out.println("[2] Incluir pedido no embarque ");
				System.out.println("[3] Excluir embarque ");
				System.out.println("[4] Sair ");


				int escolha = ScannerInput.getInstance().readInt();


				switch(escolha) {

				case 1: 

					System.out.print("Digite o codigo do pedido que deseja excluir: ");
					int excluir = ScannerInput.getInstance().readInt();

					try{
						Pedido pd = embarque.getPedido(excluir);
						embarque.removerPedido(pd);
						
						break;

					} catch (NullPointerException n) {
						n.getMessage();
						System.out.println("\n>>>Codigo Invalido ");
						new MenuController(new MenuUI()).launch();
						break;
					}



				case 2:
					System.out.print("Digite o codigo do pedido que deseja incluir no embarque: ");
					int incluir = ScannerInput.getInstance().readInt();

					try {
						
						embarque.addPedido(DaoFactory.getOrderDAO().getPedido(incluir));
						System.out.println("\n>>>Pedido incluido com sucesso ");
						break;
					} catch (NullPointerException n) {
						n.getMessage();
						System.out.println("\n>>>Codigo Invalido ");
						break;
					}



				case 3:

					System.out.print("Confirma a exclusao do embarque? ");

					// embarque.getCodEmbarque() 
					String excluirEmbarque = ScannerInput.getInstance().readString();

					if(excluirEmbarque.equals("s") ||excluirEmbarque.equals("S")) {
						DaoFactory.getEmbarqueDAO().deleteEmbarque(embarque);
						System.out.println("\n>>>Embarque excluido com sucesso!");
						break;
					}


				case 4:
					continuar = false;
				}


			} catch (NullPointerException n) {
				System.out.println("Opcao Invalida ");
				new MenuController(new MenuUI()).launch();
			}
		}
		new MenuController(new MenuUI()).launch();
	} 

}





	

