package roteirizador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;



public class Vendedor extends Funcionario {
	
	
	private int rota;
	private double comicao;
	private List<Pedido> vendas;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	
	public Vendedor(int estab, String nome, String senha, int rota, double comicao) {
		super(estab, nome, senha);
		this.setRota(rota);
		this.setComicao(comicao);
	}
	
	public Vendedor(int matricula, int estab, String nome, String senha, int rota, double comicao) {
		super(matricula, estab, nome, senha);
		this.setRota(rota);
		this.setComicao(comicao);
	}
	
	public int getRota() {
		return rota;
	}
	public void setRota(int rota) {
		Object oldValue = this.rota;
		this.rota = rota;
		changeSupport.firePropertyChange("rota", oldValue, rota);

	}
	public double getComicao() {
		return comicao;
	}
	public void setComicao(double comicao) {
		Object oldValue = this.comicao;
		this.comicao = comicao;
		changeSupport.firePropertyChange("comicao", oldValue, comicao);

	}
	
	

}
