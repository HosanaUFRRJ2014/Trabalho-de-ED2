package testes;
import estruturas.Tabela;
import estruturas.Chave;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
//import java.util.function.Consumer;

import org.junit.Test;


public class TabelaTest 
{
	Tabela t;

	@Test
	public void testTabela() 
	{
		//fail("Not yet implemented");
	//	t = new Tabela(13);
		assertNotNull(t);
		
	}

	@Test
	public void testAdicionarElemento() 
	{
		//fail("Not yet implemented");
		String m = "maria";
		
		String m2 = "maria borges";
		
//		Tabela t = new Tabela(13);
		try {
			boolean b= t.adicionarElemento(m, 0);
			
			assertTrue(b);
			
			b = t.adicionarElemento(m2, 1);
			
		//	System.out.println(b);
			
			assertTrue(b);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuscarChaveRetornaChave() 
	{
		//fail("Not yet implemented");
//		Tabela t =  new Tabela(13);
		
		try {
			t.adicionarElemento("maria silva", 0);
			t.adicionarElemento("joao goulart", 3);
			t.adicionarElemento("miguel", 5);
			t.adicionarElemento("miguel araújo", 6);
			t.adicionarElemento("maria gomes silveira", 1);
			t.adicionarElemento("maria", 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		LinkedList<Chave> chaves = 	t.buscarChaveRetornaChave("miguel");
		assertNotNull(chaves);
		for(Chave c : chaves)
		{
		//	System.out.println(c.getString());
		}

	//	System.out.println("outro caso:");

		chaves = t.buscarChaveRetornaChave("maria");
		
		assertNotNull(chaves);
		for(Chave c : chaves)
		{
			System.out.println(c.getString());
		}
	
		
	}

/*	@Test
	public void testBuscarChave() 
	{
		//fail("Not yet implemented");
	}*/

	@Test
	public void testBuscarChaveRetornaPosicaoArquivo() 
	{
//		Tabela t = new Tabela(13);
		try {
			t.adicionarElemento("maria silva", 0);
			t.adicionarElemento("joao goulart", 3);
			t.adicionarElemento("miguel", 5);
			t.adicionarElemento("miguel araújo", 6);
			t.adicionarElemento("maria gomes silveira", 1);
			t.adicionarElemento("maria", 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LinkedList l = t.buscar("maria");
		
		
		assertNotNull(l);
		assertEquals(l.size(),3);
	}

	@Test
	public void testImprimirTabela()
	{
		
		
//		Tabela t = new Tabela(20);
		int count = 0;

		try 
		{
			for(int i = 0; i < 100; i++)
			{
				boolean b = t.adicionarElemento("maria " + i,i);
			//	System.out.println(i);
				if(b == true)
					count++;
				
			}
			
			t.imprimirTabela();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		assertEquals(100,count);
		
		

	}

}
