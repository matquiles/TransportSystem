package roteirizador.domain;

public class Roteirizador extends Funcionario {
	
	
	private String regiao;
	private double ocupacao;
	//private Autenticador autenticar;

	
	public Roteirizador(int estab, String nome, String senha, String regiao) {
		super(estab, nome, senha);
		this.setRegiao(regiao);
		
	}
	
	public Roteirizador(int matricula, int estab, String nome, String senha, String regiao) {
		super(matricula, estab, nome, senha);
		this.setRegiao(regiao);
		
	}
	
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public double getOcupacao() {
		return ocupacao;
	}
	public void setOcupacao(double ocupacao) {
		this.ocupacao = ocupacao;
	}
	

}
