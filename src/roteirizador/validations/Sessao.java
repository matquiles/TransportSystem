package roteirizador.validations;

import roteirizador.domain.Funcionario;

public class Sessao {
	
	 private static Sessao instance = null;
	 private Funcionario funcionario;
	
	  private Sessao(){
	   }

	   public void setUsuario(Funcionario fun){
	      this.funcionario = fun;
	   }

	   public Funcionario getUsuario(){
	       return funcionario;
	   }
	   
	   public static Sessao getInstance(){
	         if(instance == null){
	               instance = new Sessao();
	         }
	        return instance;
	   }
	}
	  
	  

