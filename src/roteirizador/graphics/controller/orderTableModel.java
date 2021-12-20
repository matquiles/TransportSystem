package roteirizador.graphics.controller;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;

@SuppressWarnings("serial")
public class orderTableModel extends AbstractTableModel {

    private final String colunas[] = {"Cod Cliente","Nome Cliente", "Cod Pedido", "Valor", 
    		"Paletizacao", "Vendedor", "Data"};
    private SelectionInList<Pedido> itens;

    private final int coluna_codCliente = 0;
    private final int coluna_nome= 1;
    private final int coluna_codPedido = 2;
    private final int coluna_valor = 3;
    private final int coluna_paletizacao = 4;
    private final int coluna_vendedor = 5;
    private final int coluna_data = 6;

    

    public orderTableModel(SelectionInList<Pedido> selectionPedido) {
        this.itens = selectionPedido;
    }
    
//    public void addItens(List<Pedido> list) {
//        this.itens = list;
//    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    

    @Override
    public int getRowCount() {
        return itens.getList().size();
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
    	Pedido pd = itens.getElementAt(rowIndex);

        switch (columnIndex) {
        case coluna_codCliente:
        	return pd.getCliente().getCodcliente();
        case coluna_nome:
        	return pd.getCliente().getNomecliente();
        case coluna_codPedido:
        	return pd.getCodPedido();
        case coluna_valor:
        	return pd.getValorTotal();	
        case coluna_paletizacao:
        	return pd.getPaletizacaoTotal();
        case coluna_vendedor:
        	return pd.getVendedor().getNome();
        case coluna_data:
        	return pd.getData();
       
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
		return this.itens.getElementAt(index);
		
	}

    
}