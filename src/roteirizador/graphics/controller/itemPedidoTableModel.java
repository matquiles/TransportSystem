package roteirizador.graphics.controller;


import java.util.List;


import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;

@SuppressWarnings("serial")
public class itemPedidoTableModel extends AbstractTableModel {

    private final String colunas[] = {"Indice", "Cod SKU", "Descricao","Quantidade", "Valor", "Paletizacao"};
    private SelectionInList<ItemPedido> itens;

    private final int coluna_indice = 0;
    private final int coluna_cod = 1;
    private final int coluna_descricao= 2;
    private final int coluna_quantidade = 3;
    private final int coluna_valor = 4;
    private final int coluna_paletizacao = 5;

    

    public itemPedidoTableModel(SelectionInList<ItemPedido> selectionItemPedido) {
        this.itens = selectionItemPedido;
    }
    
//    public void addItens(List<ItemPedido> list) {
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
        ItemPedido item = itens.getElementAt(rowIndex);

        switch (columnIndex) {
        case coluna_indice:
        	return item.getIdItem();
        case coluna_cod:
        	return item.getItem().getCodSKU();
        case coluna_descricao:
        	return item.getItem().getNomeSku();
        case coluna_quantidade:
        	return item.getQtItem();
        case coluna_paletizacao:
        	return item.getQtItem() / item.getItem().getPaletizacao();
        case coluna_valor:
        	return item.getQtItem() * item.getItem().getValor();
        }
        return null;
    }

	public String[] getColunas() {
		return colunas;
	}
	
	public void refreshData() {
		fireTableDataChanged();
	}
	
	public ItemPedido retornaPedido(int index) {
		return this.itens.getElementAt(index);
		
	}

    
}
