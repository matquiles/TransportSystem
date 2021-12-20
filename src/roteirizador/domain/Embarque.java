package roteirizador.domain;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.validations.FormatarValores;
import roteirizador.validations.Sessao;
import roteirizador.validations.ValidarPedidoAlocado;

public class Embarque {
	
	
	private int codEmbarque;
	private Roteirizador roteirizador;
	private int unidade;
	private List<Pedido> pedidos;
	private double paletizacaoTotal;
	private double valorTotal;
	private String veiculo;
	private double ocupacao;
	private Date data;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	
	public Embarque() {
		
		this.setCodEmbarque(DatabaseDaoFactory.getEmbarqueDAO().ultimoEmbarqueCriado());
		this.setRoteirizador(Sessao.getInstance().getUsuario());
		this.setUnidade(Sessao.getInstance().getUsuario().getEstabelecimento());
		this.setData(new Date());
		pedidos = new ArrayList<Pedido>();
	}
	
	public Embarque(int embarque, Funcionario roteirizador, int unidade, double paletizacao, double valor, String veiculo, double ocupacao, Date data) {
		pedidos = new ArrayList<Pedido>();
		this.setCodEmbarque(embarque);
		this.setRoteirizador(roteirizador);
		this.setUnidade(unidade);
		this.setPaletizacaoTotal(paletizacao);
		this.setValorTotal(valor);
		this.setVeiculo(veiculo);
		this.setOcupacao(ocupacao);
		this.setData(data);
		
	}
	
	public void addPedido(Pedido pedido) {

		pedidos.add(pedido);
		this.setPaletizacaoTotal(pedido.getPaletizacaoTotal());
		this.setValorTotal(pedido.getValorTotal());
		this.setOcupacao(this.getPaletizacaoTotal()/8);

		//DatabaseDaoFactory.getOrderDAO().atualizarPedido(pedido);
	}
	
	
	public void alocar() {
		for (Pedido pedido : pedidos) {
			pedido.setAlocado(true);
			DatabaseDaoFactory.getOrderDAO().atualizarPedido(pedido);
		}
		
	}
	
	public void desalocarTodos() {
		for (Pedido pedido : pedidos) {
			pedido.setAlocado(false);
			DatabaseDaoFactory.getOrderDAO().atualizarPedido(pedido);
		}
		
	}
	
	public void desalocar(Pedido pedido) {
			pedido.setAlocado(false);
			DatabaseDaoFactory.getOrderDAO().atualizarPedido(pedido);
	}
	
	public void addPedidoSQL(Pedido pedido) {
			pedidos.add(pedido);
		}
	
	
	public void removerPedido(Pedido pedido) {
		pedidos.remove(pedido);
		pedido.setAlocado(false);
		this.setPaletizacaoTotal(-pedido.getPaletizacaoTotal());
		this.setOcupacao(this.getPaletizacaoTotal()/8);
		this.setValorTotal(-pedido.getValorTotal());
		this.desalocar(pedido);
		DatabaseDaoFactory.getEmbarqueDAO().atualizarEmbarque(this);
	}
	
	public List<Pedido> getAll(){
		return new ArrayList<Pedido>(pedidos);
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		
		result.append("<ul>");
			FormatarValores fv = new FormatarValores();
			result.append(String.format("<li>Código do Embarque: %d</li>", getCodEmbarque()));
			result.append(String.format("<li>Unidade: %d</li>", getUnidade()));
			result.append(String.format("<li>Placa: %s</li>", getVeiculo()));
			result.append(String.format("<li>Data de Criação: %s</li>", fv.formatarData(getData())));
			result.append(String.format("<li>Ocupação: %s</li>", fv.formatarPercentual(getOcupacao())));
			result.append(String.format("<li>Valor Total: %s</li>", fv.formatarDinheiro(getValorTotal())));
	
		result.append("</ul>");
		
		return result.toString();

	}
	
	public Pedido getPedido(int id) {
		for(int i = 0; i < pedidos.size(); i++) {
			if(pedidos.get(i).getCodPedido()==id) {
				return pedidos.get(i);
			}
		}
		return null;
	}
	
	
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		Object oldValue = this.data;
		this.data = data;
		changeSupport.firePropertyChange("data", oldValue, data);

	}

	public void setPaletizacaoTotal(double paletizacaoTotal) {
		Object oldValue = this.paletizacaoTotal;
		this.paletizacaoTotal = paletizacaoTotal + this.paletizacaoTotal;
		changeSupport.firePropertyChange("paletizacaoTotal", oldValue, paletizacaoTotal);

	}
	
	public void setValorTotal(double valorTotal) {
		Object oldValue = this.valorTotal;
		this.valorTotal = valorTotal + this.valorTotal;
		changeSupport.firePropertyChange("valorTotal", oldValue, valorTotal);

	}
	
	public void setOcupacao(double ocupacao) {
		Object oldValue = this.ocupacao;
		this.ocupacao = ocupacao;
		changeSupport.firePropertyChange("ocupacao", oldValue, ocupacao);

	}
	

	public int getCodEmbarque() {
		return codEmbarque;
	}


	public void setCodEmbarque(int codEmbarque) {
		Object oldValue = this.codEmbarque;
		this.codEmbarque = codEmbarque;
		changeSupport.firePropertyChange("codEmbarque", oldValue, codEmbarque);

	}


	public Roteirizador getRoteirizador() {
		return roteirizador;
	}


	public void setRoteirizador(Funcionario funcionario) {
		Object oldValue = this.roteirizador;
		this.roteirizador = (Roteirizador) funcionario;
		changeSupport.firePropertyChange("funcionario", oldValue, funcionario);

	}


	public int getUnidade() {
		return unidade;
	}


	public void setUnidade(int unidade) {
		Object oldValue = this.unidade;
		this.unidade = unidade;
		changeSupport.firePropertyChange("unidade", oldValue, unidade);

	}


	public List<Pedido> getPedidos() {
		return pedidos;
	}


	public void setPedidos(List<Pedido> pedidos) {
		Object oldValue = this.pedidos;
		this.pedidos = pedidos;
		changeSupport.firePropertyChange("pedidos", oldValue, pedidos);

	}


	public double getPaletizacaoTotal() {
		return paletizacaoTotal;
	}

	public double getValorTotal() {
		return valorTotal;
	}


	public String getVeiculo() {
		return veiculo;
	}


	public void setVeiculo(String veiculo) {
		Object oldValue = this.veiculo;
		this.veiculo = veiculo;
		changeSupport.firePropertyChange("veiculo", oldValue, veiculo);

	}


	public double getOcupacao() {
		return ocupacao;
	}


	
	
	

}
