package roteirizador.domain;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.DaoFactory;


public class ItemPedido {
	
	private int idItem;
	private List<ItemPedido> pedidos;
	private Integer idPedido;
	private Integer qtItem;
	private Double paletizacao;
	private Double valor;
	private Pedido pedido;
	private SKU item;
	private static ItemPedido instance;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	public ItemPedido(int idItem, Pedido idPedido, int qtd, SKU sku) {
		this.setIdItem(idItem);
		this.setQtItem(qtd);
		this.setItem(sku);
		this.setPedido(idPedido);
	}
	
	
	public ItemPedido(int qtd, SKU sku) {
		this.setQtItem(qtd);
		this.setItem(sku);
		this.setPaletizacao((double) (qtd / sku.getPaletizacao()));
		this.setValor(qtd * sku.getValor());
	}
	
	public ItemPedido(){
		this.pedidos = new ArrayList <>();
	}
	

	public synchronized static ItemPedido getInstance() {
		if (instance == null) {
			instance = new ItemPedido();
		}
		return instance;
	}
	
	public void save(ItemPedido pedido) {
		pedidos.add(pedido);

	}
	

	public void delete(ItemPedido pedido) {
		pedidos.remove(pedido);
	
	}
	
	public List<ItemPedido> getAll(){
		return new ArrayList<ItemPedido>(pedidos);
	}
	
	public ItemPedido getItemPedido(int id) {
		for(int i = 0; i < pedidos.size(); i++) {
			if(pedidos.get(i).getIdPedido()==id) {
				return pedidos.get(i);
			}
		}
		return null;
	}
	

	public List<ItemPedido> getPedidos() {
		return pedidos;
	}
	

	public Integer getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(Integer idPedido) {
		Object oldValue = this.idPedido;
		this.idPedido = idPedido;
		changeSupport.firePropertyChange("idPedido", oldValue, idPedido);

	}


	public Integer getQtItem() {
		return qtItem;
	}


	public void setQtItem(Integer qtItem) {
		Object oldValue = this.qtItem;
		this.qtItem = qtItem;
		changeSupport.firePropertyChange("qtItem", oldValue, qtItem);
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		Object oldValue = this.pedido;
		this.pedido = pedido;
		changeSupport.firePropertyChange("pedido", oldValue, pedido);
	}


	public SKU getItem() {
		return item;
	}


	public void setItem(SKU item) {
		Object oldValue = this.item;
		this.item = item;
		changeSupport.firePropertyChange("item", oldValue, item);

	}


	public static void setInstance(ItemPedido instance) {
		ItemPedido.instance = instance;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		Object oldValue = this.idItem;
		this.idItem = idItem;
		changeSupport.firePropertyChange("idItem", oldValue, idItem);

	}


	public Double getPaletizacao() {
		return paletizacao;
	}


	public void setPaletizacao(Double paletizacao) {
		Object oldValue = this.paletizacao;
		this.paletizacao = paletizacao;
		changeSupport.firePropertyChange("paletizacao", oldValue, paletizacao);

	}
	
	public Double getValor() {
		return valor;
	}


	public void setValor(Double Valor) {
		Object oldValue = this.valor;
		this.valor = Valor;
		changeSupport.firePropertyChange("valor", oldValue, Valor);

	}
	
	


}
