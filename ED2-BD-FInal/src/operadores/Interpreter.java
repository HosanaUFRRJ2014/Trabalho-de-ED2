package operadores;

public interface Interpreter
{	
	/**
	 * Interface responsavel pelo comportamento polimorfico dos operadores de algebra relacional
	 * e relacoes de dados e seus indices.
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Tupla
	 * @see Shell
	 * @see TestesPontuais
	 */
	
	public abstract Interpreter open();
	public abstract Interpreter next();
	public abstract Interpreter close();

}
