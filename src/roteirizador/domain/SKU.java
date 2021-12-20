package roteirizador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.database.SkuDAODatabase;

public class SKU {
	
	private Integer codSKU;
	private String nomeSku;
	private Integer paletizacao;
	private Double valor;
	private static int total;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	public SKU() {
		
	}
	
	public SKU(int codSKU, String nome, int paletizacao, double valor) {
		this.setCodSKU(codSKU);
		this.setNomeSku(nome);
		this.setPaletizacao(paletizacao);
		this.setValor(valor);
	
		total++;
	}
	
	public SKU(String nome, int paletizacao, double valor) {
		this.setCodSKU(2);
		this.setNomeSku(nome);
		this.setPaletizacao(paletizacao);
		this.setValor(valor);
	
		total++;
	}

	public int getCodSKU() {
		return codSKU;
	}
	
	public void setItem(int codSKU) {
		Object oldValue = this.codSKU;
		this.codSKU = codSKU;
		changeSupport.firePropertyChange("codSKU", oldValue, codSKU);

	}
	
	public void setCodSKU(int codSKU) {
		Object oldValue = this.codSKU;
		this.codSKU = codSKU;
		changeSupport.firePropertyChange("codSKU", oldValue, codSKU);

	}
	
	public String getNomeSku() {
		return nomeSku;
	}
	
	public void setNomeSku(String nomeSku) {
		Object oldValue = this.nomeSku;
		this.nomeSku = nomeSku;
		changeSupport.firePropertyChange("nomeSku", oldValue, nomeSku);

	}
	
	public int getPaletizacao() {
		return paletizacao;
	}
	
	public void setPaletizacao(int paletizacao) {
		Object oldValue = this.paletizacao;
		this.paletizacao = paletizacao;
		changeSupport.firePropertyChange("paletizacao", oldValue, paletizacao);

	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		Object oldValue = this.valor;
		this.valor = valor;
		changeSupport.firePropertyChange("valor", oldValue, valor);

	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("<ul>");
			result.append(String.format("<li>Código: %d</li>", getCodSKU()));
			result.append(String.format("<li>Nome: %s</li>", getNomeSku()));
			result.append(String.format("<li>Valor: %f</li>", getValor()));
			result.append(String.format("<li>Paletização: %d</li>", getPaletizacao()));
	
		result.append("</ul>");
		
		return result.toString();
	}
	
	


	
	

}
