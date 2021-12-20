package roteirizador.dao;

import java.util.List;

import roteirizador.domain.SKU;

public interface SkuDAO {

	void save(SKU sku);

	void delete(SKU sku);

	List<SKU> getAll();

	SKU getSKU(int id);

	int qtdRegistros();

}