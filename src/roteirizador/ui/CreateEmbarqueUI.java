package roteirizador.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.validations.ValidarPedido;

public class CreateEmbarqueUI {

	public CreateEmbarqueUI() {
		
	}

	public Embarque show() {
		

		
		System.out.println("-----Criar Embarque-----\n");
		System.out.println("[1] Criar embarque manualmente");
		System.out.println("[2] Criar embarque via importacao de arquivo");
		
		int escolha = ScannerInput.getInstance().readInt();

		switch(escolha) {
		
		case 1:
			return embarqueManual();
		case 2:
			embarqueAutomatico();
			break;
		}

		return null;
		}	
		
	
	private Embarque embarqueManual() {
		
		
		
		ValidarPedido vp = new ValidarPedido();
		boolean digitar = true;
		
		Embarque embarque = new Embarque();
		
		System.out.println("* Numero do embarque: " + embarque.getCodEmbarque());
		System.out.println("* Usuario: " + embarque.getRoteirizador().getNome());
		System.out.println("* Unidade: " + embarque.getUnidade()+ "\n*******\n"); 
		

			System.out.print("Insira a placa do veiculo: ");
			String placa = ScannerInput.getInstance().readString();
			
			embarque.setVeiculo(placa);
		
			while(digitar == true) {
			
				
			System.out.println("Digite codigo do pedido: ");
			
				int pedidoDigitado = ScannerInput.getInstance().readInt();
			
				if(vp.ValidacaoPedido(pedidoDigitado) == false) {
					System.out.println("Pedido invalido");
				} else {
					embarque.addPedido(DaoFactory.getOrderDAO().getPedido(pedidoDigitado));
					if(inserirNovo(embarque) == true) {
						digitar = true;
					} else {
						digitar = false;
					}	
				}
			}
		return embarque;
	}
	


	private void embarqueAutomatico() {
		
		
		//String path = "C:\\Users\\mathe\\eclipse-workspace\\CloneTotvs\\embarque.txt";
		
		String path = "/home/matheus/eclipse-workspace/CloneTotvs/embarque.txt";
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String line = br.readLine();
			String[] vect = line.split(",");
			Integer comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
			Integer pedido = Integer.parseInt(vect[1]);
			String placa = (vect[2]);
			
		
			while (line != null) {
				
				boolean validar = true;
				Embarque embarque = new Embarque();
				embarque.setVeiculo(placa);
				
				while(validar == true ) {
						String lineAbaixo = br.readLine();
						if(lineAbaixo==null && line != null) {
							//EmbarqueDAO.getInstance().save(embarque);
							//line=null;
							//break;
						}else if (lineAbaixo==null && embarque == null) {
							line=null;
							break;
							
						} else if (lineAbaixo==null && embarque!=null){
							DaoFactory.getEmbarqueDAO().save(embarque);	
							line=null;
							break;
						}
	

					System.out.println("Pedido " + pedido + " inserido no embarque\n**");
					embarque.addPedido(DaoFactory.getOrderDAO().getPedido(pedido));
					
					try {
						String[] vect2 = lineAbaixo.split(",");
					}catch(NullPointerException n) {
						DaoFactory.getEmbarqueDAO().save(embarque);
						
						line=null;
						break;
					}
						String[] vect2 = lineAbaixo.split(",");
						
					Integer comparaSeNovoEmbarqueNaLinhaAbaixo = Integer.parseInt(vect2[0]);

						
						if(comparaSeNovoEmbarque != comparaSeNovoEmbarqueNaLinhaAbaixo ||comparaSeNovoEmbarqueNaLinhaAbaixo == null ) {
							DaoFactory.getEmbarqueDAO().save(embarque);
							
							line = lineAbaixo;
							vect = line.split(",");
							pedido = Integer.parseInt(vect[1]);
							comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
							placa = (vect[2]);
							validar = false;
							break;
							
						} else if (comparaSeNovoEmbarque == comparaSeNovoEmbarqueNaLinhaAbaixo || comparaSeNovoEmbarque == null) {
							line = lineAbaixo;
							vect = line.split(",");
							pedido = Integer.parseInt(vect[1]);
							comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
							placa = (vect[2]);
						}
						
				} 
			}	
			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			
		}

	}
	
	
	private boolean inserirNovo(Embarque embarque) {
		boolean continuarDigitando;
		
		System.out.println("Digite S para inserir novo pedido ou N para finalizar o embarque?" );
		String escolha = ScannerInput.getInstance().readString();
		
		if("n".equals(escolha) ||"N".equals(escolha) ) {
			try {
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			continuarDigitando = false;
		
		} else if ("s".equals(escolha) || "S".equals(escolha)){
			continuarDigitando = true;
		} else {
			System.out.println("Escolha invalida!");
			continuarDigitando = false;
		}
		
		return continuarDigitando;
	}
	


	
}
