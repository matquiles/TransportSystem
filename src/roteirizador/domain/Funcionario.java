package roteirizador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Funcionario {
	
	
	private Integer matricula;
	private Integer estabelecimento;
	private String nome;
	private String senha;
	private static int qtd;
	
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	
	
	public Funcionario(int estab, String nome, String senha) {
		this.setMatricula(qtd+1);
		this.setEstabelecimento(estab);
		this.setNome(nome);
		this.setSenha(senha);
		
		qtd++;
	}
	
	public Funcionario(int matricula, int estab, String nome, String senha) {
		this.setMatricula(matricula);
		this.setEstabelecimento(estab);
		this.setNome(nome);
		this.setSenha(senha);
		
		qtd++;
	}
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		Object oldValue = this.senha;
		this.senha = senha;
		changeSupport.firePropertyChange("senha", oldValue, senha);

	}

	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		Object oldValue = this.matricula;
		this.matricula = matricula;
		changeSupport.firePropertyChange("matricula", oldValue, matricula);

	}
	public int getEstabelecimento() {
		return estabelecimento;
	}
	public void setEstabelecimento(int estabelecimento) {
		Object oldValue = this.estabelecimento;
		this.estabelecimento = estabelecimento;
		changeSupport.firePropertyChange("estabelecimento", oldValue, estabelecimento);

	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		Object oldValue = this.nome;
		this.nome = nome;
		changeSupport.firePropertyChange("nome", oldValue, nome);

	}
	
	

}
