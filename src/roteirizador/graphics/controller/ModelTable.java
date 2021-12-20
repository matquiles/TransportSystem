package roteirizador.graphics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ModelTable extends AbstractTableModel {
	
	private List linhas = null;
	private String[] colunas  = null;
	
	


	public ModelTable(List lin, String[] col) {
		setLinhas(lin);
		setColunas(col);
		
		
	}
	

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

	@Override
	public Object getValueAt(int numLinha, int numCol) {
		Object[] linha =  (Object[])getLinhas().get(numLinha);
		return linha[numCol];
	}
	
	public String getColumnName(int numCol) {
		return colunas[numCol];
		
	}


	public List getLinhas() {
		return linhas;
	}


	public void setLinhas(List lin) {
		this.linhas = lin;
	}


	public String[] getColunas() {
		return colunas;
	}


	public void setColunas(String[] col) {
		this.colunas = col;
	}
	
	

}
