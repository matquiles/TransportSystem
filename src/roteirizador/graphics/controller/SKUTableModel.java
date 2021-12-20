package roteirizador.graphics.controller;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import roteirizador.domain.SKU;

public class SKUTableModel extends AbstractTableModel {


    private String colunas[] = {"Cod SKU", "Descricao", "Paletizacao", "Valor"};
    private List<SKU> skus;
    private final int coluna_cod = 0;
    private final int coluna_descricao= 1;
    private final int coluna_paletizacao = 2;
    private final int coluna_valor = 3;

    public SKUTableModel(List<SKU> list) {
        this.skus = list;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    

    @Override
    public int getRowCount() {
        return skus.size();
    }
  
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
 
    @Override
    public String getColumnName(int indice) {
        return colunas[indice];
    }

    //determina o tipo de dado da coluna conforme seu indice
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case coluna_cod:
                return Integer.class;
            case coluna_descricao:
                return String.class;
            case coluna_paletizacao:
                return Integer.class;
            case coluna_valor:
                return Double.class;
            default:
                return String.class;
        }
    }

    //preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SKU sku = this.skus.get(rowIndex);

        switch (columnIndex) {
            case coluna_cod:
                return sku.getCodSKU();
            case coluna_descricao:
                return sku.getNomeSku();
            case coluna_paletizacao:
                return sku.getPaletizacao();
            case coluna_valor:
                return sku.getValor();
        }
        return null;
    }
    //altera o valor do objeto de acordo com a célula editada
    //e notifica a alteração da tabela, para que ela seja atualizada na tela
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //o argumento recebido pelo método é do tipo Object
        //mas como nossa tabela é de funcionários, é seguro(e até recomendável) fazer o cast de suas propriedades
        SKU sku = this.skus.get(rowIndex);
        //de acordo com a coluna, ele preenche a célula com o valor
        //respectivo do objeto de mesmo indice na lista
        switch (columnIndex) {
            case coluna_cod:
                sku.setCodSKU((int) aValue);
                break;
            case coluna_descricao:
                sku.setNomeSku(String.valueOf(aValue));
                break;
            case coluna_paletizacao:
                sku.setPaletizacao((int) aValue);
                break;
            case coluna_valor:
                sku.setValor((double) aValue);
          
        }
        //este método é que notifica a tabela que houve alteração de dados
        fireTableDataChanged();
    }


	public String[] getColunas() {
		return colunas;
	}


	public void setColunas(String[] colunas) {
		this.colunas = colunas;
	}


	
 
    
}