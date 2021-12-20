package roteirizador.ui;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.validations.FormatarValores;

public class ListEmbarquesUI {
	
	
	public void showAll(List<Embarque> embarques) {
		FormatarValores format = new FormatarValores();
			
			
			if(embarques.size() == 0) {
				System.out.println("Nao existem embarques no sistema");
			} else {
				try {
					
				for (int i = 0; i<embarques.size();i++) {
								
					System.out.println("\n* Numero do embarque: " + embarques.get(i).getCodEmbarque());
					System.out.println("* Usuario: " + embarques.get(i).getRoteirizador().getNome());
					System.out.println("* Unidade: " + embarques.get(i).getUnidade());
					System.out.println("* Placa: " + embarques.get(i).getVeiculo());
					System.out.println("* Data: " + embarques.get(i).getData());
					System.out.println("* Ocupacao: ");
					format.formatarPercentual(embarques.get(i).getOcupacao());
					System.out.println("---------------------------\n---------------------------\n");
				
					
				}
				} catch (NullPointerException e) {
					e.getMessage();
					
				
				}
				
			}
			System.out.println("Contador de embarques criados: " + DaoFactory.getEmbarqueDAO().getAll().size() );
	}
	

}
