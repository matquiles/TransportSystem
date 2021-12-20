package roteirizador.ui;

public enum ControllerOptions {
	
	CREATE_SKU("Cadastro de SKUs"), 
	LIST_SKU("Consultar SKUs"),
	LIST_CLIENTS("Consultar Clientes"),
	CREATE_CLIENT("Cadastro de Clientes"),
	CREATE_SALES_ORDER("Digitar Pedido"),
	LIST_ORDER("Consultar Pedidos"),
	EDIT_ORDER("Editar Pedidos"),
	EXPORT_ORDER("Exportar Pedidos "),
	CREATE_EMBARQUE("Criar Embarque"),
	LIST_EMBARQUE("Exibir Embarques"),
	EDIT_EMBARQUE("Editar Embarques");
	

	private String label;

	ControllerOptions(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
