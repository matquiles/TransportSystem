package roteirizador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Client {
	
	private Integer codCliente;
	private String nomeCliente;
	private String endereco;
	private String cidade;
	private String estado;
	private int unidade;
	private static int total = 1000;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	public Client (int codcLient, String nome, String endereco, String cidade, String estado, int unidade) {
		this.setCodCliente(codcLient);
		this.setNomeCliente(nome);
		this.setCidade(cidade);
		this.setEndereco(endereco);
		this.setEstado(estado);
		this.setUnidade(unidade);
	}
	
	public Client ( String nome, String endereco, String cidade, String estado, int unidade) {
		
		this.setNomeCliente(nome);
		this.setCidade(cidade);
		this.setEndereco(endereco);
		this.setEstado(estado);
		this.setUnidade(unidade);
	}

	public Client() {
	}

	
	public int getCodcliente() {
		return codCliente;
	}

	public String getNomecliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		Object oldValue = this.nomeCliente;
		this.nomeCliente = nomeCliente;
		changeSupport.firePropertyChange("nomeCliente", oldValue, nomeCliente);

	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		Object oldValue = this.endereco;
		this.endereco = endereco;
		changeSupport.firePropertyChange("endereco", oldValue, endereco);

	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		Object oldValue = this.cidade;
		this.cidade = cidade;
		changeSupport.firePropertyChange("cidade", oldValue, cidade);

	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		Object oldValue = this.estado;
		this.estado = estado;
		changeSupport.firePropertyChange("estado", oldValue, estado);

	}

	public int getUnidade() {
		return unidade;
	}

	public void setUnidade(int unidade) {
		Object oldValue = this.unidade;
		this.unidade = unidade;
		changeSupport.firePropertyChange("unidade", oldValue, unidade);

	}

	public static int getTotal() {
		return total;
	}


	public void setCodCliente(Integer codCliente) {
		Object oldValue = this.codCliente;
		this.codCliente = codCliente;
		changeSupport.firePropertyChange("codCliente", oldValue, codCliente);

	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("<ul>");
			result.append(String.format("<li>Código: %d</li>", getCodcliente()));
			result.append(String.format("<li>Nome: %s</li>", getNomecliente()));
			result.append(String.format("<li>Nome: %s</li>", getCidade()));
			result.append(String.format("<li>Nome: %s</li>", getEstado()));
			result.append(String.format("<li>Nome: %d</li>", getUnidade()));
		
		result.append("</ul>");
		
		return result.toString();
	}

	

	
}
