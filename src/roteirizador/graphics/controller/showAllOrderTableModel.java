package roteirizador.graphics.controller;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import roteirizador.domain.Pedido;

@SuppressWarnings("serial")
public class showAllOrderTableModel extends AbstractTableModel {

    private final String colunas[] = {"Cod Pedido","Cod Cliente", "Nome Cliente",
    		"Paletizacao", "Data", "Alocado"};
    private List<Pedido> itens;

    private final int coluna_codCliente = 1;
    private final int coluna_nome= 2;
    private final int coluna_codPedido = 0;
    private final int coluna_paletizacao = 3;
    private final int coluna_data = 4;
    private final int coluna_alocado = 5;

    

    public showAllOrderTableModel(List<Pedido> list) {
        this.itens = list;
    }
    
    public void addItens(List<Pedido> list) {
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
    	Pedido pd = this.itens.get(rowIndex);

        switch (columnIndex) {
        case coluna_codCliente:
        	return pd.getCliente().getCodcliente();
        case coluna_nome:
        	return pd.getCliente().getNomecliente();
        case coluna_codPedido:
        	return pd.getCodPedido();
        case coluna_paletizacao:
        	return pd.getPaletizacaoTotal();
        case coluna_data:
        	return pd.getData();
        case coluna_alocado:
        	return pd.getAlocado();
       
        }
        return null;
    }

	public String[] getColunas() {
		return colunas;
	}
	
	public void refreshData() {
		fireTableDataChanged();
	}
	
	public Pedido retornaPedido(int index) {
		return this.itens.get(index);
		
	}
	

    
}