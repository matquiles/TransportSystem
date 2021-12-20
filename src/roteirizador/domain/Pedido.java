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



public class Pedido {

	private static int gerarCodPedido = 100;
	private Integer codPedido;
	private Client codCliente;
	private List <ItemPedido> itensPedidos;
	private static Pedido instance;
	private Double valorTotal;
	private Double paletizacaoTotal;
	private boolean alocado = false;
	private Vendedor vendedor;
	private Date data;
	
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
	
	public Pedido () {
		this.itensPedidos = new ArrayList <>();
		this.setCodPedido(DatabaseDaoFactory.getOrderDAO().ultimoCodigoCriado());
		setAlocado(false);
		//setVendedor(Sessao.getInstance().getUsuario());
		this.paletizacaoTotal = 0.0;
		this.valorTotal = 0.0;
		
	}
	
	public Pedido(int codPedido, Client cliente, double valor, double palete, boolean alocado, Funcionario vendedor, Date data) {
		this();
		this.setCodPedido(codPedido);
		this.setCliente(cliente);
		this.setValorTotalSQL(valor);
		this.setPaletizacaoTotalSQL(palete);
		this.setAlocado(alocado);
		this.setVendedor(vendedor);
		this.setData(data);
	}

	
	public Pedido(Client idCliente) {
		this.setCliente(idCliente);
		gerarCodPedido++;
		this.setCodPedido(DatabaseDaoFactory.getOrderDAO().ultimoCodigoCriado());
		this.setData(new Date());
		itensPedidos = new ArrayList<ItemPedido>();
		if(Sessao.getInstance().getUsuario()!=null) {
			this.setVendedor(Sessao.getInstance().getUsuario());
		} else{
			this.setVendedor(new Vendedor(999, "sistema", "abc", 0, 0));
		}
	}

	public void addItem(ItemPedido item) {
		itensPedidos.add(item);
		item.setPedido(this);
		item.setIdItem(itensPedidos.size());
		
	}
	
	public void addItemSQL(ItemPedido item) {
		itensPedidos.add(item);
		item.setPedido(this);
		
	}
	
	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}
	
	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		Object oldValue = this.itensPedidos;
		this.itensPedidos = itensPedidos;
		changeSupport.firePropertyChange("itensPedidos", oldValue, itensPedidos);

	}
	
	public List<ItemPedido> getAll(){
		return new ArrayList<ItemPedido>(itensPedidos);
	}
	
	public void removerItem(ItemPedido item, SKU sku) {
		itensPedidos.remove(item);
		this.setValorTotal(item.getQtItem(), -sku.getValor());
		this.setPaletizacaoTotal(item.getQtItem(), -sku.getPaletizacao());
		System.out.println("\n>>Item excluido com sucesso");
		
	}
	
	public synchronized static Pedido getInstance() {
		if (instance == null) {
			instance = new Pedido();
		}
		return instance;
	}
	
	public void setValorTotal(Integer qtd, Double valor) {
		Double total = (qtd * valor) + this.valorTotal;
		
		Object oldValue = this.valorTotal;
		this.valorTotal = total;
		changeSupport.firePropertyChange("valorTotal", oldValue, total);

	}
	
	public void setValorTotalSQL(double valor) {
		Object oldValue = this.valorTotal;
		this.valorTotal = valor;
		changeSupport.firePropertyChange("valorTotal", oldValue, valor);
	}
	
	public double getValorTotal() {
		return valorTotal;
	}
	
	public void setPaletizacaoTotal(int qtd, double paletizacao) {
		double total = (qtd / paletizacao) + this.paletizacaoTotal;
		
		Object oldValue = this.paletizacaoTotal;
		this.paletizacaoTotal = total;
		changeSupport.firePropertyChange("paletizacaoTotal", oldValue, total);
	}
	
	
	public void setPaletizacaoTotalSQL(double paletizacao) {
		Object oldValue = this.paletizacaoTotal;
		this.paletizacaoTotal = paletizacao;
		changeSupport.firePropertyChange("paletizacaoTotal", oldValue, paletizacao);
	}
	
	
	public double getPaletizacaoTotal() {
		return paletizacaoTotal;
	}


	public void mostrarItens() {
		FormatarValores formatar = new FormatarValores();
		
	
		System.out.println("\n");
		for(int i=0; i<itensPedidos.size();i++) {

			System.out.println("Chave do item: " + itensPedidos.get(i).getIdItem());
			System.out.println("Cod Sku: " + itensPedidos.get(i).getItem().getCodSKU());
			System.out.println("Nome Sku: " + itensPedidos.get(i).getItem().getNomeSku());
			System.out.println("Qtd: " + itensPedidos.get(i).getQtItem());
			formatar.formatarPaletes(itensPedidos.get(i).getQtItem() / itensPedidos.get(i).getItem().getPaletizacao());
			formatar.formatarDinheiro(itensPedidos.get(i).getQtItem() * itensPedidos.get(i).getItem().getValor());
			System.out.println("*****\n");

		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("<ul>");
			result.append(String.format("<li>Código Pedido: %d</li>", getCodPedido()));
			result.append(String.format("<li>Cliente: %d</li>", getCliente().getCodcliente()));
			result.append(String.format("<li>Nome Cliente: %s</li>", getCliente().getNomecliente()));
			result.append(String.format("<li>Valor: %f</li>", getValorTotal()));
			result.append(String.format("<li>Paletização: %f</li>", getPaletizacaoTotal()));
	
		result.append("</ul>");
		
		return result.toString();
	}

	
	public int getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(int codPedido) {
		Object oldValue = this.codPedido;
		this.codPedido = codPedido;
		changeSupport.firePropertyChange("codPedido", oldValue, codPedido);

	}
	public Client getCliente() {
		return codCliente;
	}
	public void setCliente(Client codCliente) {
		Object oldValue = this.codCliente;
		this.codCliente = codCliente;
		changeSupport.firePropertyChange("codCliente", oldValue, codCliente);

	}

	public boolean isAlocado() {
		return alocado;
	}

	public void setAlocado(boolean alocado) {
		Object oldValue = this.alocado;
		this.alocado = alocado;
		changeSupport.firePropertyChange("alocado", oldValue, alocado);

	}
	
	public boolean getAlocado() {
		return alocado;
	}


	public Vendedor getVendedor() {
		return vendedor;
	}


	public void setVendedor(Funcionario funcionario) {
		Object oldValue = this.vendedor;
		this.vendedor = (Vendedor) funcionario;
		changeSupport.firePropertyChange("vendedor", oldValue, funcionario);
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		Object oldValue = this.data;
		this.data = data;
		changeSupport.firePropertyChange("data", oldValue, data);

	}
	
	public void setCodCliente(Client codCliente) {
		Object oldValue = this.codCliente;
		this.codCliente = codCliente;
		changeSupport.firePropertyChange("codCliente", oldValue, codCliente);

	}
	
	public Client getCodCliente() {
		return codCliente;
	}
	
	


	
}
