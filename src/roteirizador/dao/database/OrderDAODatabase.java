package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.OrderDAO;
import roteirizador.domain.Client;
import roteirizador.domain.Embarque;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;
import roteirizador.domain.Vendedor;
import roteirizador.validations.Sessao;


public class OrderDAODatabase implements OrderDAO{

	
	
	@Override
	public void save(Pedido pedido) {
	
//		 Integer idPedido = getNextIdPedido();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
						"INSERT INTO table_pedidos(cod_pedido, cod_cliente, valor_pedido, paletizacao_pedido, alocacao, vendedor, data_pedido) VALUES "
						+ "((select max(cod_pedido)+1 from table_pedidos),?,?,?,?,?,?)");
						
				st.setInt(1, pedido.getCliente().getCodcliente());
				st.setDouble(2, pedido.getValorTotal());
				st.setDouble(3, pedido.getPaletizacaoTotal());
				st.setBoolean(4, false);
				st.setInt(5, Sessao.getInstance().getUsuario().getMatricula());
				st.setDate(6, new java.sql.Date(new Date().getTime()));
				
				st.executeUpdate();
				
				
				for(ItemPedido item : pedido.getAll()){
					st = conn.prepareStatement(
						"INSERT INTO itens_pedidos (sql_id, id_item, id_pedido, qtd, id_sku) VALUES ((select max(sql_id)+1 from itens_pedidos),?,?,?,?)");
				
					st.setInt(1, item.getIdItem());
					st.setInt(2, pedido.getCodPedido());
					st.setInt(3, item.getQtItem());
					st.setInt(4, item.getItem().getCodSKU());
					
					st.executeUpdate();
					
				}
				

				}	
				catch (SQLException e) {
					throw new DbException(e.getMessage());
				}
				finally {
					ConexaoJDBC.closeStatement(st);
					ConexaoJDBC.closeConnectiopn();
				}  
	}

	@Override
	public void deletePedido(Pedido pedido) {
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stItem = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			stItem = conn.prepareStatement(
					"DELETE FROM itens_pedidos WHERE id_pedido = ?");
			stItem.setInt(1, pedido.getCodPedido());
			stItem.executeUpdate();
			
			
			st = conn.prepareStatement(
					"DELETE FROM table_pedidos WHERE cod_pedido = ?");

			st.setInt(1, pedido.getCodPedido());
			
			st.executeUpdate();
			
			System.out.println("\nCliente excluido do BD");
			
		} 
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		
	}

	@Override
	public List<Pedido> getAll() {
		List<Pedido> pedidos = new ArrayList <>();

		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stItem = null;
		ResultSet rs = null;
		ResultSet rsItem = null;
	

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_pedidos");
			rs = st.executeQuery();

			while(rs.next()){

					Pedido pd = new Pedido(rs.getInt("cod_pedido"), DaoFactory.getClienteDAO().getClient(rs.getInt("cod_cliente")),rs.getDouble("valor_pedido"), 
							rs.getDouble("paletizacao_pedido"), rs.getBoolean("alocacao"), (Vendedor) DaoFactory.getFuncionarioDAO().getFuncionario(rs.getInt("vendedor")), 
									rs.getDate("data_pedido"));

					pedidos.add(pd);
				
				stItem = conn.prepareStatement("SELECT * FROM itens_pedidos WHERE id_pedido = ?");
				stItem.setInt(1, pd.getCodPedido());
				rsItem = stItem.executeQuery();
			
		
				while (rsItem.next()) {
					ItemPedido itemPedido = new ItemPedido(rsItem.getInt("id_item"), pd, rsItem.getInt("qtd"), 
							DaoFactory.getSkuDAO().getSKU(rsItem.getInt("id_sku")));
					pd.addItemSQL(itemPedido);
				
				}
				ConexaoJDBC.closeStatement(stItem);
				ConexaoJDBC.closeConnectiopn();
				
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		return new ArrayList<Pedido>(pedidos);
	}

	@Override
	public Pedido getPedido(int id) {
		
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stItem = null;
		ResultSet rs = null;
		ResultSet rsItem = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_pedidos where cod_pedido = ? ");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			rs.next();

			Pedido pd = new Pedido(rs.getInt("cod_pedido"), DaoFactory.getClienteDAO().getClient(rs.getInt("cod_cliente")),rs.getDouble("valor_pedido"), 
					rs.getDouble("paletizacao_pedido"), rs.getBoolean("alocacao"), (Vendedor) DaoFactory.getFuncionarioDAO().getFuncionario(rs.getInt("vendedor")), 
					rs.getDate("data_pedido"));


			stItem = conn.prepareStatement("SELECT * FROM itens_pedidos WHERE id_pedido = ?");
			stItem.setInt(1, pd.getCodPedido());
			rsItem = stItem.executeQuery();
		
			while (rsItem.next()) {
				ItemPedido itemPedido = new ItemPedido(rsItem.getInt("id_item"), pd, rsItem.getInt("qtd"), 
						DaoFactory.getSkuDAO().getSKU(rsItem.getInt("id_sku")));
				pd.addItemSQL(itemPedido);
			
			}
			ConexaoJDBC.closeStatement(stItem);
			ConexaoJDBC.closeConnectiopn();
		
		return pd;
	} 
	catch (SQLException e) {
		e.printStackTrace();
		throw new DbIntegrityException(e.getMessage());
	}
	finally {
		ConexaoJDBC.closeStatement(st);
		ConexaoJDBC.closeConnectiopn();
	}
	}

	@Override
	public int ultimoCodigoCriado() {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int retorno = 101;
		

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("(select max(cod_pedido)+1 as contador from table_pedidos)");	
			
			rs.next();
			
			return rs.getInt("contador");
					
			
		} 
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	}

	@Override
	public void saveItemPedido(Pedido pedido,ItemPedido itempedido) {

		Connection conn = null;
		PreparedStatement st = null;


		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO itens_pedidos (sql_id, id_item, id_pedido, qtd, id_sku) VALUES ((select max(sql_id)+1 from itens_pedidos),"
					+ "(select max(id_item)+1 from itens_pedidos where id_pedido = ?),?,?,?)");

			st.setInt(1, pedido.getCodPedido());
			st.setInt(2, pedido.getCodPedido());
			st.setInt(3, itempedido.getQtItem());
			st.setInt(4, itempedido.getItem().getCodSKU());

			st.executeUpdate();
			pedido.addItem(itempedido);
			atualizarPedido(pedido);
			
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}  




	}

	@Override
	public void deleteItemPedido(Pedido pedido, ItemPedido itempedido) {
		Connection conn = null;
		PreparedStatement st = null;


		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM itens_pedidos WHERE id_item = ? AND id_pedido = ?");

			st.setInt(1, itempedido.getIdItem());
			st.setInt(2, pedido.getCodPedido());
			st.executeUpdate();
			
			pedido.removerItem(itempedido, itempedido.getItem());
			
			atualizarPedido(pedido);
			
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}  

		
	}

	@Override
	public void atualizarPedido(Pedido pedido) {
		
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"UPDATE table_pedidos SET valor_pedido = ?, paletizacao_pedido = ?,  alocacao = ? WHERE cod_pedido = ?");

			st.setDouble(1, pedido.getValorTotal());
			st.setDouble(2, pedido.getPaletizacaoTotal());
			st.setBoolean(3, pedido.getAlocado());
			st.setInt(4, pedido.getCodPedido());
			
			st.executeUpdate();

			System.out.println("\nPedido atualizado com sucesso!");
			
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}  

		
		
	
	
	}

	
	public List<Pedido> getRange(Date datainicial, Date datafinal, int unidade) {
		List<Pedido> pedidos = new ArrayList <>();

		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stItem = null;
		ResultSet rs = null;
		ResultSet rsItem = null;
	

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_pedidos INNER JOIN table_client "
					+ "ON table_pedidos.cod_cliente = table_client.codcliente "
					+ "WHERE table_client.unidade = ? "
					+ "AND table_pedidos.data_pedido >=? "
					+ "AND table_pedidos.data_pedido <=? ");
			st.setInt(1,  unidade);
			st.setDate(2, new java.sql.Date(datainicial.getTime()));
			st.setDate(3, new java.sql.Date(datafinal.getTime()));
			
			rs = st.executeQuery();

			while(rs.next()){

					Pedido pd = new Pedido(rs.getInt("cod_pedido"), DaoFactory.getClienteDAO().getClient(rs.getInt("cod_cliente")),rs.getDouble("valor_pedido"), 
							rs.getDouble("paletizacao_pedido"), rs.getBoolean("alocacao"), (Vendedor) DaoFactory.getFuncionarioDAO().getFuncionario(rs.getInt("vendedor")), 
									rs.getDate("data_pedido"));

					pedidos.add(pd);
				
				stItem = conn.prepareStatement("SELECT * FROM itens_pedidos WHERE id_pedido = ?");
				stItem.setInt(1, pd.getCodPedido());
				rsItem = stItem.executeQuery();
			
		
				while (rsItem.next()) {
					ItemPedido itemPedido = new ItemPedido(rsItem.getInt("id_item"), pd, rsItem.getInt("qtd"), 
							DaoFactory.getSkuDAO().getSKU(rsItem.getInt("id_sku")));
					pd.addItemSQL(itemPedido);
				
				}
				ConexaoJDBC.closeStatement(stItem);
				ConexaoJDBC.closeConnectiopn();
				
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		return new ArrayList<Pedido>(pedidos);
	}
	
}
