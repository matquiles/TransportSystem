package roteirizador.graphics.controller;


import java.util.List;

import javax.swing.table.AbstractTableModel;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;


@SuppressWarnings("serial")
public class showAllEmbarquesTableModel extends AbstractTableModel {

    private final String colunas[] = {"Cod Embarque","Unidade", "Veículo", "Paletizacao",
    		"Qtd Clientes", "Data",};
    private List<Embarque> itens;

    private final int coluna_codEmbarque = 0;
    private final int coluna_unidade = 1;
    private final int coluna_veiculo = 2;
    private final int coluna_paletizacao = 3;
	private final int coluna_qtdClientes = 4;
    private final int coluna_data = 5;

    

    public showAllEmbarquesTableModel(List<Embarque> list) {
        this.itens = list;
    }
    
    public void addItens(List<Embarque> list) {
        this.itens = list;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    

    @Override
    public int getRowCount() {
        return itens.size();
    }
  
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
 
    @Override
    public String getColumnName(int indice) {
        return colunas[indice];
    }


    //preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Embarque embarque = this.itens.get(rowIndex);

        switch (columnIndex) {
        case coluna_codEmbarque:
        	return embarque.getCodEmbarque();
        case coluna_unidade:
        	return embarque.getUnidade();
        case coluna_veiculo:
        	return embarque.getVeiculo();
        case coluna_paletizacao:
        	return embarque.getPaletizacaoTotal();
        case coluna_data:
        	return embarque.getData();
        case coluna_qtdClientes:
        	return embarque.getAll().size();
       
        }
        return null;
    }

	public String[] getColunas() {
		return colunas;
	}
	
	public void refreshData() {
		fireTableDataChanged();
	}
	
	public Embarque retornaPedido(int index) {
		return this.itens.get(index);
		
	}
	

    
}