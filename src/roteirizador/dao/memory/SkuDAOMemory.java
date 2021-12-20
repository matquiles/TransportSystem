package roteirizador.dao.memory;
import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.SkuDAO;
import roteirizador.domain.SKU;

public class SkuDAOMemory implements SkuDAO {

	private final List<SKU> skus;
	
	public SkuDAOMemory() {
		this.skus = new ArrayList <>();
			
	}
	
	@Override
	public void save(SKU sku) {
		skus.add(sku);
	}
	
	@Override
	public void delete(SKU sku) {
		skus.remove(sku);
	
	}
	
	@Override
	public List<SKU> getAll(){
		return new ArrayList<SKU>(skus);
	}
	
	@Override
	public SKU getSKU(int id) {
		for(int i = 0; i < skus.size(); i++) {
			if(skus.get(i).getCodSKU()==id) {
				return skus.get(i);
			}
		}
		return null;
	}

	@Override
	public int qtdRegistros() {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
