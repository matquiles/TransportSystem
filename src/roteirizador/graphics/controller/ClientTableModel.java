package roteirizador.graphics.controller;


import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import roteirizador.domain.Client;


public class ClientTableModel extends AbstractTableModel {


    private String colunas[] = {"Codigo", "Nome", "Endereço", "Cidade", "Estado", "Unidade"};
    private List<Client> cliente;
    private final int coluna_cod = 0;
    private final int coluna_nome = 1;
    private final int coluna_endereco = 2;
    private final int coluna_cidade = 3;
    private final int coluna_estado = 4;
    private final int coluna_unidade = 5;

    public ClientTableModel(List<Client> list) {
        this.cliente = list;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    

    @Override
    public int getRowCount() {
        return cliente.size();
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
            case coluna_nome:
                return String.class;
            case coluna_endereco:
            	return String.class;
            case coluna_cidade:
            	return String.class;
            case coluna_estado:
            	return String.class;
            case coluna_unidade:
                return Integer.class;
            default:
                return String.class;
        }
    }

    //preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client cliente = this.cliente.get(rowIndex);

        switch (columnIndex) {
            case coluna_cod:
                return cliente.getCodcliente();
            case coluna_nome:
                return cliente.getNomecliente();
            case coluna_endereco:
                return cliente.getEndereco();
            case coluna_cidade:
                return cliente.getCidade();
            case coluna_estado:
                return cliente.getEstado();
            case coluna_unidade:
                return cliente.getUnidade();
        }
        return null;
    }
    //altera o valor do objeto de acordo com a célula editada
    //e notifica a alteração da tabela, para que ela seja atualizada na tela
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //o argumento recebido pelo método é do tipo Object
        //mas como nossa tabela é de funcionários, é seguro(e até recomendável) fazer o cast de suas propriedades
        Client cliente = this.cliente.get(rowIndex);
        //de acordo com a coluna, ele preenche a célula com o valor
        //respectivo do objeto de mesmo indice na lista
        switch (columnIndex) {
            case coluna_cod:
                cliente.setCodCliente((int) aValue);
                break;
            case coluna_nome:
            	cliente.setNomeCliente(String.valueOf(aValue));
                break;
            case coluna_endereco:
            	cliente.setEndereco(String.valueOf(aValue));
                break;
            case coluna_cidade:
            	cliente.setCidade(String.valueOf(aValue));
            	break;
            case coluna_estado:
            	cliente.setEstado(String.valueOf(aValue));
            	break;
            case coluna_unidade:
            	cliente.setUnidade((int) aValue);
          
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