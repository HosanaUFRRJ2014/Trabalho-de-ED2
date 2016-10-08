package controladores;

import operadores.*;

import estruturas.Estrutura;

import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import arquivos.*;
import testes.TestesPontuais;

public class Shell {
	/**
	 * Classe responsavel por implementar o interpletador de comandos e
	 * condicoes. Faz toda a gerencia de input e aciona as funcoes indicadas
	 * pelos comandos. Todo o tratamento de erro de input reside nesta classe.
	 * 
	 * 
	 * @author William Anderson de B. Gomes
	 * @see Interpreter
	 * @see IndiceParentesesStringComando
	 * @see Tupla
	 * @see Condicao
	 * @see GerenciadorDeRelacoes
	 * @see TestesPontuais
	 */

	public static void mensagemDeErro(String mensagem) {
		System.out.println(mensagem);
		return;
	}

	public static LinkedList<Par> verificarBalanceamento(String s) {
		int i = 0, j = 0, k = 0;

		char schar[] = s.toCharArray();
		Par p;
		IndiceParentesesStringComando parenteses = new IndiceParentesesStringComando();

		while (i < schar.length) {
			if (schar[i] == '(') {
				k++;
				p = new Par(i);
				parenteses.addParentesesAberto(p, i);
			} else if (schar[i] == ')') {
				k--;
				p = parenteses.removerUltimoAberto();
				p.setIndiceFechado(i);
				parenteses.addParentesesFechado(p, p.getIndiceAberto());
			}

			if (k < 0)
				return null;

			i++;
		}

		if (k == 0)
			return parenteses.getParFechados();

		return null;
	}

	private static Condicao lerCondicao(String s, Tupla t) {
		LinkedList<Par> pares = Shell.verificarBalanceamento(s);
		if (pares != null)
		{
			int indiceParenteses1 = s.indexOf('(');
			String val = s.substring(0, indiceParenteses1).trim();
			Condicao condEsq, condDir;
			String nomeCampo, valorCampo, aux;
			String conds[] = new String[2];
			int condAtual;
			char[] temp = s.toCharArray();
			String mensagemErro;
			int indiceParenteses2 = indiceParenteses1 + 1;
			int indiceVirgula;

			if (indiceParenteses2 > temp.length) {
				mensagemDeErro("condicao sem parametro, coloque os parametros necessarios para \"" + val.trim() + "\"");
				return null;
			}
			switch (val.trim().toUpperCase()) {
			case "E":// E(condEsq(), condDir())

				while (pares.get(indiceParenteses2) == null && indiceParenteses2 < pares.size()
						&& temp[indiceParenteses2] != ',')
					indiceParenteses2++;

				/*
				 * while(temp[indiceParenteses2] != '(' && indiceParenteses2 <
				 * temp.length) { if(temp[indiceParenteses2] == ',' ||
				 * indiceParenteses2 == temp.length-1) {
				 * mensagemDeErro("nao foi possivel criar a condicao \""+ s +
				 * "\", o primeiro parametro deve ser uma condicao"); return
				 * null; } indiceParenteses2++; }
				 */
				// CONFERIR CONDICOES!!!
				// CHECAR INDICES DE SUBSTRING E DE
				// PARES.GET(INDICE).GETINDICEFECHADO()
				if (indiceParenteses2 == pares.size()) {
					mensagemDeErro("nao foi possivel criar a condicao \"" + s
							+ "\", o primeiro parametro deve ser uma condicao");
					return null;
				}
				if (pares.get(indiceParenteses2) != null) 
				{
					conds[0] = s.substring(indiceParenteses1 + 1, pares.get(indiceParenteses2).getIndiceFechado() + 1);
					indiceVirgula = pares.get(indiceParenteses2).getIndiceFechado() + 1;
					while (temp[indiceVirgula] != ',')
					{
						if (temp[indiceVirgula] == '(') 
						{
							mensagemDeErro("nao foi possivel criar a condicao, quantidade de parametros da condicao \""
									+ s + "\" esta incorreta. Para mais informações consulte o comando help()");
							return null;
						}
						indiceVirgula++;
					}
					indiceParenteses2 = indiceVirgula + 1;
					while (pares.get(indiceParenteses2) == null && indiceParenteses2 < pares.size()
							&& temp[indiceParenteses2] != ',')
						indiceParenteses2++;

					/*
					 * while(temp[indiceParenteses1] != '(') {
					 * if(temp[indiceParenteses1] == ',') {
					 * mensagemDeErro("nao foi possivel criar a condicao, quantidade de parametros da condicao E é maior que 2"
					 * ); return null; } indiceParenteses1++; }
					 */
					if (indiceParenteses2 == pares.size()) 
					{
						mensagemDeErro("nao foi possivel criar a condicao \"" + s
								+ "\", o segundo parametro deve ser uma condicao");
						return null;
					}
					if (pares.get(indiceParenteses2) != null) 
					{
						conds[1] = s.substring(indiceVirgula + 1, pares.get(indiceParenteses2).getIndiceFechado() + 1);

						condEsq = lerCondicao(conds[0].trim(), t);
						condDir = lerCondicao(conds[1].trim(), t);
						if (condEsq != null && condDir != null)
							return new CondicaoE(condEsq, condDir);

						mensagemErro = "não criou a condicao \"" + s + "\". ";
						if (condEsq == null && condDir == null)
							mensagemErro += "condicao esquerda e direita retornaram nulo";
						else {
							if (condEsq == null)
								mensagemErro += "condicao esquerda retornou nulo";
							if (condDir == null)
								mensagemErro += "condicao direita retorou nulo";
						}
						mensagemDeErro(mensagemErro);
						return null;
					}
				}
				mensagemDeErro("nao foi possivel criar a condicao, quantidade de parametros da condicao \"" + s
						+ "\" esta incorreta. Para mais informações consulte o comando help()");
				return null;

			case "OU": // OU(condEsq(), condDir())
				while (pares.get(indiceParenteses2) == null && indiceParenteses2 < pares.size()
						&& temp[indiceParenteses2] != ',')
					indiceParenteses2++;

				if (indiceParenteses2 == pares.size()) {
					mensagemDeErro("nao foi possivel criar a condicao \"" + s
							+ "\", o primeiro parametro deve ser uma condicao");
					return null;
				}

				/*
				 * while(temp[indiceParenteses2] != '(' && indiceParenteses2 <
				 * temp.length) { if(temp[indiceParenteses2] == ',') {
				 * mensagemDeErro("nao foi possivel criar a condicao \"" + s +
				 * "\", o primeiro parametro deve ser uma condicao"); return
				 * null; } indiceParenteses2++; }
				 */
				if (pares.get(indiceParenteses2) != null) 
				{
					conds[0] = s.substring(indiceParenteses1 + 1, pares.get(indiceParenteses2).getIndiceFechado() + 1);
					indiceVirgula = pares.get(indiceParenteses2).getIndiceFechado() + 1;
					while (temp[indiceVirgula] != ',') 
					{
						if (temp[indiceVirgula] == '(') 
						{
							mensagemDeErro("nao foi possivel criar a condicao " + s
									+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
							return null;
						}
						indiceVirgula++;
					}

					indiceParenteses2 = indiceVirgula + 1;
					while (pares.get(indiceParenteses2) == null && indiceParenteses2 < pares.size()
							&& temp[indiceParenteses2] != ',')
						indiceParenteses2++;

					/*
					 * while(temp[indiceParenteses1] != '(') {
					 * if(temp[indiceParenteses1] == ',') {
					 * mensagemDeErro("nao foi possivel criar a condicao, quantidade de parametros da condicao OU é maior que 2"
					 * ); return null; } indiceParenteses1++; }
					 */

					if (indiceParenteses2 == pares.size())
					{
						mensagemDeErro("nao foi possivel criar a condicao \"" + s
								+ "\", o primeiro parametro deve ser uma condicao");
						return null;
					}
					if (pares.get(indiceParenteses2) != null) 
					{
						conds[1] = s.substring(indiceVirgula + 1, pares.get(indiceParenteses1).getIndiceFechado() + 1);

						condEsq = lerCondicao(conds[0].trim(), t);
						condDir = lerCondicao(conds[1].trim(), t);
						if (condEsq != null && condDir != null)
							return new CondicaoOu(condEsq, condDir);

						mensagemErro = "não criou a condicao OU : " + s + " ";
						if (condEsq == null && condDir == null)
							mensagemErro += "condicao esquerda e direita retornaram nulo";
						else {
							if (condEsq == null)
								mensagemErro += "condicao esquerda retornou nulo";
							if (condDir == null)
								mensagemErro += "condicao direita retorou nulo";
						}
						mensagemDeErro(mensagemErro);
						return null;
					}
				}
				mensagemDeErro("nao foi possivel criar a condicao, quantidade de parametros da condicao \"" + s
						+ "\" esta incorreta. Para mais informações consulte o comando help()");
				return null;
			case "NAO": // NAO(cond())
				
				aux = s.substring(pares.get(indiceParenteses1).getIndiceAberto() + 1, pares.get(indiceParenteses1).getIndiceFechado());

				condEsq = lerCondicao(aux.trim(), t);
				if (condEsq != null)
					return new CondicaoNao(condEsq);

				mensagemDeErro("não criou a condicao \"" + s + "\" a condicao parametro retornou nulo");
				return null;
			case "IGUAL":// IGUAL(nomeCampo, valorCampo)
				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).replace('"', ' ').trim();
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado()).replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoIgual(nomeCampo, valorCampo);

			case "DIFERENTE":
				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).trim();
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado())
						.replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoDiferente(nomeCampo, valorCampo);

			case "MAIOR":

				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).trim();
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado())
						.replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoMaior(nomeCampo, valorCampo);

			case "MENOR":
				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).trim();
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado())
						.replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoMenor(nomeCampo, valorCampo);

			case "MAIORIGUAL":
				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).replace('"', ' ').trim();
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado())
						.replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoMaiorIgual(nomeCampo, valorCampo);
			case "MENORIGUAL":
				while (temp[indiceParenteses2] != ',') {
					if (temp[indiceParenteses2] == '(' || temp[indiceParenteses2] == ')') {
						mensagemDeErro("nao foi possivel criar a condicao " + s
								+ "\", a quantidade de parametros da condicao esta incorreta. Para mais informações consulte o comando help()");
						return null;
					}
					indiceParenteses2++;
				}

				nomeCampo = s.substring(indiceParenteses1 + 1, indiceParenteses2).replace('"', ' ');
				valorCampo = s.substring(indiceParenteses2 + 1, pares.get(indiceParenteses1).getIndiceFechado())
						.replace('"', ' ').trim();

				if (t.retornaIndiceCampo(nomeCampo) == -1) {
					mensagemDeErro("nao foi possivel criar a condicao : o campo informado na condicao \"" + s
							+ "\" não existe na relacao informada");
					return null;
				}

				return new CondicaoMenorIgual(nomeCampo, valorCampo);
			default:
				mensagemDeErro("nao foi possivel criar a condicao : a condicao \"" + val.trim() + "\" não existe");
			}
		}
		mensagemDeErro(
				"A quantidade de parenteses da condicao não esta balanceada, verifique a quantidade de parenteses e digite sua consulta novamente.");
		return null;
	}

	private static String tirarParentesesFinal(String s, String val, LinkedList<Par> pares) {
		char[] temp;
		temp = s.toCharArray();
		int i = s.indexOf(val) + val.length();
		while (temp[i] != '(') {
			i++;
		}
		temp[pares.get(i).getIndiceFechado()] = ' ';
		s = String.valueOf(temp).trim();
		return s;
	}

	// a busca sequencial é um caso especifico da selecao
	public static Interpreter lerString(String s) {
		LinkedList<Par> pares = Shell.verificarBalanceamento(s);
		if(s.indexOf('(') == -1)
		{
			mensagemDeErro("Comando incorreto, use parenteses. Para mais informações consulte o comando help()");
			return null;
		}
		
		char[] temp;
		String s1;
		String s2;
		int indiceAS1;
		int indiceFS1;
		int indiceIS2;
		Interpreter relacao;
		Interpreter relacao1;
		Interpreter relacao2;
		String[] parametros;
		String[] campos;
		String[] valores;
		String[] aux;
		String nomeCampo;
		String valorCampo;
		String sProximo;
		Interpreter relacaoEsq;
		Interpreter relacaoDir;
		String mensagemErro = "";
		Interpreter arquivo;
		String pseudonimo;
		String diretorio;
		Interpreter arqIndice;

		if (pares != null) {
			String sarray[] = s.trim().split(Pattern.quote("("));
			String val = sarray[0].trim();

			switch (val.toLowerCase()) {
			case "listar": // listar(<nomerelacao>) || listar()
				String nomeRelacao = sarray[1].replace(')', ' ');
				nomeRelacao = nomeRelacao.replace('"', ' ').trim();
				if (nomeRelacao.equals(""))
					GerenciadorDeRelacoes.imprimirRelacoes();
				else
					GerenciadorDeRelacoes.imprimirCamposRelacao(nomeRelacao);

				return null;
			case "renomeacao":// renomeacao("campovelho1 campovelho2 ...",
								// "camponovo1 camponovo2 ...", relacao())
				parametros = sarray[1].split(",");
				if (parametros.length != 3) {
					mensagemDeErro(
							"nao foi possivel criar a renomeacao, a quantidade de parametros nao esta correta [precisa ter 3 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				String camposVelhos[] = parametros[0].replace('"', ' ').trim().split(" ");
				String camposNovos[] = parametros[1].replace('"', ' ').trim().split(" ");

				if (camposVelhos.length != camposNovos.length) {
					mensagemDeErro(
							"nao foi possivel criar o operador Renomeacao : a quantidade de campos informados nos 2 primeiros parametros é diferente digite a mesma quantidade de campos em cada parametro. Use o comando help() para mais informações.");
					return null;
				}

				s = tirarParentesesFinal(s, val, pares);

				sProximo = s.substring(s.indexOf(parametros[2].trim()));
				relacao = lerString(sProximo);

				if (relacao != null) {
					Tupla t = (Tupla) relacao.open().next();
					t.open();
					for (int i = 0; i < camposVelhos.length; i++) {
						if (t.retornaIndiceCampo(camposVelhos[i]) == -1) {
							mensagemDeErro("Nao foi possível criar o operador renomeacao : o campo \"" + camposVelhos[i]
									+ "\" não existe na relacao especificada digite um dos campos abaixo no lugar :");
							t.imprimirNomeColunasTupla();
							t.close();
							relacao.close();
							return null;
						}
					}

					t.open();
					relacao.open();

					return new Renomeacao(camposVelhos, camposNovos, relacao);
				}

				mensagemDeErro("nao foi possivel criar o operador renomeacao : a relacao retornou nulo");
				return null;
			/*
			 * case "buscaarvoreb"://buscaArvoreB(campoBusca, valorBusca,
			 * indiceArqvoreB()) break; case "buscahash"://buscaHash(campoBusca,
			 * valorBusca, indiceHash()) break;
			 */
			case "selecaoporindice":// selecaoPorIndice(campoBusca, valorBusca,
									// Indice(pseudonimo))
				parametros = sarray[1].split(",");
				if (parametros.length < 3) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " a quantidade de parametros deve ser 3. Para mais informações consulte o operador help()");
					return null;
				}

				nomeCampo = parametros[0].replace('"', ' ').trim();
				valorCampo = parametros[1].replace('"', ' ').trim();

				s = tirarParentesesFinal(s, val, pares);

				if (parametros[2].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o terceiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				sProximo = s.substring(s.indexOf(sarray[1]) + sarray[1].length() - parametros[2].length());
				relacao = lerString(sProximo);

				if (relacao == null) {
					mensagemDeErro(
							"nao foi possivel criar o operador " + val.trim() + " o arquivo retornado foi nulo ");
					return null;
				}

				if (relacao instanceof ArquivoEstrutura) {
					ArquivoEstrutura a = (ArquivoEstrutura) relacao;
					Estrutura est;
					if (a instanceof ArquivoArvoreB)
						est = ((ArquivoArvoreB) a).getArvoreB();
					else
						est = ((ArquivoHashTable) a).getTabela();

					if (!nomeCampo.equalsIgnoreCase(est.getNomeCampoChave())) {
						mensagemDeErro("nao foi possivel criar o operador " + val.trim()
								+ " o campo chave do indice não corresponde ao campo chave inserido");
						return null;
					}
					pseudonimo = est.getPeseudonimoArquivoDadoOrigem();
					return new SelecaoPorEstrutura(est, valorCampo,
							(ArquivoDado) GerenciadorDeRelacoes.retornaArquivoDado(pseudonimo));
				}
				return null;
			case "hashjoin":// hashjoin(campoJuncao, relacaoEsq(<parametros>),
							// relacaoDir(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um campo de juncao existente na relacao informada. Consulte o comando help() para mais informacoes");
					return null;
				}

				parametros = sarray[1].split(",");

				nomeCampo = parametros[0].replace('"', ' ').trim();

				s1 = parametros[1].trim();
				if (s1.equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o segundo parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				s = tirarParentesesFinal(s, val, pares);

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoEsq = lerString(s1);
				relacaoDir = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					Tupla t1 = (Tupla) relacaoEsq.open().next();
					Tupla t2 = (Tupla) relacaoDir.open().next();
					if (t1.retornaIndiceCampo(nomeCampo) == -1) {
						mensagemDeErro(
								"Nao foi possivel criar o operador HashJoin : a relacao esquerda nao contem o campo de juncao \""
										+ nomeCampo);
						return null;
					}
					if (t2.retornaIndiceCampo(nomeCampo) == -1) {
						mensagemDeErro(
								"Nao foi possivel criar o operador HashJoin : a relacao direita nao contem o campo de juncao \""
										+ nomeCampo);
						return null;
					}
					t1.open();
					t2.open();
					relacaoEsq.close();
					relacaoDir.close();
					return new HashJoin(nomeCampo, relacaoEsq, relacaoDir);
				}

				mensagemErro = "nao foi possivel criar o operador HashJoin: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;
			case "ordenacao":// oredenacao(campoOrdenacao, tipoOrdenacao,
								// relacao())
				parametros = sarray[1].split(",");
				if (parametros.length != 3) {
					mensagemDeErro(
							"nao foi possivel criar a ordenacao, a quantidade de parametros nao esta correta [precisa ter 3 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				String campoOrdenacao = parametros[0].replace('"', ' ').trim();
				String tipoOrdenacao = parametros[1].replace('"', ' ').trim();

				s = tirarParentesesFinal(s, val, pares);

				if (parametros[2].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				sProximo = s.substring(s.indexOf(parametros[2].trim()));
				relacao = lerString(sProximo);

				if (relacao != null) {
					Tupla t = (Tupla) relacao.open().next();
					t.open();

					if (t.retornaIndiceCampo(campoOrdenacao) == -1) {
						mensagemDeErro("nao foi possivel criar o operador Ordenacao : campo de ordenacao "
								+ campoOrdenacao + " nao existe na relacao especificada");
						return null;
					}

					t.open();
					relacao.open();

					switch (tipoOrdenacao.toLowerCase()) {
					case "crescente":
						return new Ordenacao(campoOrdenacao, true, relacao);
					case "decrescente":
						return new Ordenacao(campoOrdenacao, false, relacao);
					default:
						mensagemDeErro("nao foi possivel criar o operador ordenacao : tipo de oredenacao "
								+ tipoOrdenacao
								+ " nao é válido. O segundo parametro deve ser  \"crescente\" ou \"decrescente\" ");
					}
				}

				mensagemDeErro("nao foi possivel criar o operador ordenacao : a relacao retornou nulo");
				return null;

			case "criararquivo": // criararquivo("diretorio/arquivo.extensao",
									// pseudonimo)
				parametros = sarray[1].split(",");
				if (parametros.length != 2) {
					mensagemDeErro(
							"nao foi possivel criar o Arquivo, a quantidade de parametros nao esta correta [precisa ter 2 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				diretorio = parametros[0].replace('"', ' ').trim();
				pseudonimo = parametros[1].replace(')', ' ').trim();

				arquivo = (Interpreter) GerenciadorDeRelacoes.criarArquivoDado(diretorio, pseudonimo);

				if (arquivo != null)
					return arquivo;

				mensagemDeErro("nao foi possivel criar o arquivo : o arquivo retornado foi nulo");
				return null;

			case "arquivo": // arquivo(pseudonimo)
				pseudonimo = sarray[1].replace(')', ' ').trim();
				if (sarray[1].split(",").length != 1) {
					mensagemDeErro(
							"Nao foi possivel buscar o Arquivo, a quantidade de parametros nao esta correta [precisa ter 1 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				arquivo = GerenciadorDeRelacoes.retornaArquivoDado(pseudonimo);
				if (arquivo != null)
					return arquivo;

				mensagemDeErro(
						"arquivo nao encontrado, por favor digite o nome de um arquivo ja criado ou crie um novo arquivo com o nome de "
								+ pseudonimo);
				return null;

			case "criarindice": // criarindice(<tipoIndice>, tamanho(hash) ou
								// ordem(arvoreb),pseudonimoIndice, nome campo
								// chave, arquivo(<parametros>))
				parametros = sarray[1].split(",");
				if (parametros.length != 5) {
					mensagemDeErro(
							"Nao foi possivel criar o Indice, a quantidade de parametros nao esta correta [precisa ter 4 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}

				String tipoIndice = parametros[0].replace('"', ' ').trim();
				String chave = parametros[3].trim();
				pseudonimo = parametros[2].trim();
				boolean hash;
				if (tipoIndice.toLowerCase().equals("arvoreb"))
					hash = false;
				else if (tipoIndice.toLowerCase().equals("hash") || tipoIndice.toLowerCase().equals("hashtable"))
					hash = true;
				else {
					mensagemDeErro("nao foi possivel criar o indice : o tipo \"" + tipoIndice + "\" nao existe");
					return null;
				}

				s = tirarParentesesFinal(s, val, pares);

				if (parametros[4].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o indice " + pseudonimo
							+ " o quinto parametro deve ser um comando de arquivo. Consulte o comando help() para mais informacoes");
					return null;
				}
				sProximo = s.substring(s.indexOf(parametros[4]));
				relacao = lerString(sProximo);

				if (relacao instanceof ArquivoDado) {
					ArquivoDado a = (ArquivoDado) relacao;
					boolean nTemCampo = true;
					for (int i = 0; i < a.getNumColunas(); i++) {
						if (a.getNomeCampo()[i].equals(chave)) {
							nTemCampo = false;
							break;
						}
					}
					if (nTemCampo) {
						mensagemDeErro("O arquivo para indice nao possui o campo " + chave
								+ "informe o arquivo com este campo ou selecione um dos campos abaixo :");
						for (int i = 0; i < a.getNumColunas(); i++) {
							mensagemDeErro(a.getNomeCampo()[i]);
						}
						mensagemDeErro("");
						return null;
					}
					int tamanho;
					try {
						tamanho = Integer.parseInt(parametros[1].trim());
						if (tamanho < 2)
							throw new NumberFormatException();
					} catch (NumberFormatException e) {

						if (hash)
							tamanho = a.getNumLinhas() / 5;
						else
							tamanho = 2;

						mensagemDeErro(
								"falha ao pegar o parametro de tamanho, a execucao continua com valor padrão igual a: "
										+ tamanho);

					}
					arqIndice = null;
					try {
						arqIndice = GerenciadorDeRelacoes.criarIndice(a, pseudonimo, chave, hash, tamanho);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (arqIndice != null)
						return arqIndice;
					else
						mensagemDeErro(
								"nao foi possivel criar o indice " + tipoIndice + " : o indice retornado foi nulo");
				} else {
					if (relacao == null)
						mensagemDeErro(
								"nao foi possivel criar o indice " + tipoIndice + " : o arquivo retornado foi nulo");
					else
						mensagemDeErro("nao foi possivel criar o indice " + tipoIndice
								+ " : a relacao passada nao foi um arquivo");
				}

				return null;

			case "indice": // indice(pseudonimo)
				pseudonimo = sarray[1].replace(')', ' ').trim();
				if (sarray[1].split(",").length != 1) {
					mensagemDeErro(
							"Nao foi possivel buscar o Indice, a quantidade de parametros nao esta correta [precisa ter 1 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				arqIndice = GerenciadorDeRelacoes.retornaIndice(pseudonimo);
				if (arqIndice != null)
					return arqIndice;

				mensagemDeErro(
						"indice nao encontrado, por favor digite o nome de um indice ja criado ou crie um novo indice com o nome de \""
								+ pseudonimo + "\"");
				return null;

			case "juncaonatural":// juncaonatural(relacaoDir(<parametros>),
									// relacaoEsq(<parametros>))
				// parametros = sarray[1].split(",");
				// aux = parametros[0].replace('"', ' ').trim().split("=");;
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				s1 = sarray[1].trim();
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				s = tirarParentesesFinal(s, val, pares);
				indiceIS2++;
				s2 = s.substring(indiceIS2).trim();
				relacaoEsq = lerString(s1);
				relacaoDir = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null)
					return new JuncaoNatural(relacaoEsq, relacaoDir);
				mensagemErro = "nao foi possivel criar o operador Juncao Natural: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;

			case "nlj": // nlj("campoJuncao", relacaoDir(<parametros>),
						// relacaoEsq(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um campo de juncao existente na relacao informada. Consulte o comando help() para mais informacoes");
					return null;
				}

				parametros = sarray[1].split(",");

				nomeCampo = parametros[0].replace('"', ' ').trim();

				s1 = parametros[1].trim();
				if (s1.equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o segundo parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;

				s = tirarParentesesFinal(s, val, pares);

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoEsq = lerString(s1);
				relacaoDir = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					Tupla t1 = (Tupla) relacaoEsq.open().next();
					Tupla t2 = (Tupla) relacaoDir.open().next();
					if (t1.retornaIndiceCampo(nomeCampo) == -1) {
						mensagemDeErro(
								"Nao foi possivel criar o operador NLJ : a relacao esquerda nao contem o campo de juncao \""
										+ nomeCampo + "\"");
						return null;
					}
					if (t2.retornaIndiceCampo(nomeCampo) == -1) {
						mensagemDeErro(
								"Nao foi possivel criar o operador NLJ : a relacao direita nao contem o campo de juncao \""
										+ nomeCampo + "\"");
						return null;
					}
					t1.open();
					t2.open();
					relacaoEsq.close();
					relacaoDir.close();
					return new NLJ(nomeCampo, relacaoEsq, relacaoDir);
				}

				mensagemErro = "nao foi possivel criar o operador NLJ: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;

			case "projecao": // projetar("campo1 campo2...", relacao())
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser uma string com campos existente separados por espacos. Consulte o comando help() para mais informacoes");
					return null;
				}
				parametros = sarray[1].split(",");
				if (parametros.length != 2) {
					mensagemDeErro(
							"Nao foi possivel criar o operador Projecao, a quantidade de parametros nao esta correta [precisa ter 2 parametros -- para mais informacoes consultar o comando help() ]");
					return null;
				}
				campos = parametros[0].replace('"', ' ').trim().split(" ");

				// remove o parenteses fechado correspondente a essa operacao
				// (testar)
				s = tirarParentesesFinal(s, val, pares);

				sProximo = s.trim().substring(s.trim().indexOf(parametros[1].trim()));
				relacao = lerString(sProximo);
				if (relacao != null) {
					Tupla t = (Tupla) relacao.open().next();
					boolean temCampos = true;
					relacao.close();
					int i;
					for (i = 0; i < campos.length; i++) {
						if (t.retornaIndiceCampo(campos[i]) == -1) {
							temCampos = false;
							break;
						}
					}

					if (!temCampos) {
						mensagemDeErro("nao foi possivel criar o operador Projecao : o campo \"" + campos[i]
								+ "\" nao existe na relacao especificada");
						return null;
					}
					return new Projecao(campos, relacao);
				}

				mensagemDeErro("nao foi possivel criar o operador Projecao : a relacao retornou nulo");
				return null;

			case "selecao": // selecao(condicao(<>), relacao(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser uma CONDICAO. Consulte o comando hel() para mais informacoes");
					return null;
				}
				
				int i = s.indexOf(sarray[1]) + sarray[1].length();
				temp = s.toCharArray();
				while(temp[i] != '(')
				{
					if(temp[i] == ')' || temp[i] == ',' || i+1 == temp.length)
					{
						mensagemDeErro("Erro na sintaxe do comando : \"" + s +"\". Para mais informacoes consulte o comando help()");
						return null;
					}
					
					i++;
				}
						
				i =	pares.get(i).getIndiceFechado();
				String sCondicao = s.substring(s.indexOf(sarray[1]), i + 1);
				i++;

				temp = s.toCharArray();
				while (temp[i] != ',') {
					if (temp[i] != ' ') {
						mensagemDeErro(
								"nao foi possivel criar o operador Selecao : sintaxe incorreta nos parametros. para mais informacoes consulte o comando help()");
						return null;
					}
					i++;
				}

				s = tirarParentesesFinal(s, val, pares);
				
				
				sProximo = s.substring(i + 1).trim();

				relacao = lerString(sProximo);

				if (relacao != null) {
					Tupla t = (Tupla) relacao.open().next();
					relacao.close();
					Condicao cond = lerCondicao(sCondicao, t);
					if (cond == null) {
						mensagemDeErro("nao foi possivel criar o operador Selecao : a condicao retornada foi nula");
						return null;
					}
					
					return new Selecao(cond, relacao);
				}

				mensagemDeErro("nao foi possivel criar o operador Selecao : a relacao retornou nulo");
				return null;

			// 0 1
			case "uniao": // uniao(relacaoDir(<parametros>),relacaoEsq(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				s1 = sarray[1].trim();
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				temp[pares.get(s.indexOf(val) + val.length()).getIndiceFechado()] = ' ';
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				
				s = String.valueOf(temp).trim();

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoDir = lerString(s1);
				relacaoEsq = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					Tupla te = (Tupla) relacaoEsq.open().next();
					Tupla td = (Tupla) relacaoDir.open().next();
					relacaoEsq.close();
					relacaoDir.close();

					if (te.getTamanhoTupla() != td.getTamanhoTupla()) {
						te.open();
						td.open();
						mensagemDeErro(
								"nao foi possível criar o operador Uniao : as tuplas nao possuem os mesmos campos");
						return null;
					}
					te.open();
					td.open();
					return new Uniao(relacaoDir, relacaoEsq);
				}

				mensagemErro = "nao foi possivel criar o operador Uniao: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);

				return null;
			// identico ao anterior
			case "diferenca": // diferenca(relacaoDir(<parametros>),relacaoEsq(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				s1 = sarray[1].trim();
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				s = tirarParentesesFinal(s, val, pares);

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoDir = lerString(s1);
				relacaoEsq = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					Tupla te = (Tupla) relacaoEsq.open().next();
					Tupla td = (Tupla) relacaoDir.open().next();
					relacaoEsq.close();
					relacaoDir.close();

					if (te.getTamanhoTupla() != td.getTamanhoTupla()) {
						te.open();
						td.open();
						mensagemDeErro(
								"nao foi possível criar o operador Diferenca : as tuplas nao possuem os mesmos campos");
						return null;
					}
					te.open();
					td.open();
					return new Diferenca(relacaoDir, relacaoEsq);
				}

				mensagemErro = "nao foi possivel criar o operador Diferenca: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;
			// identico ao anterior
			case "interseccao": // interseccao(relacaoDir(<parametros>),relacaoEsq(<parametros>))
				if (sarray[1].trim().equals("")) {
					mensagemDeErro("nao foi possivel criar o operador " + val.trim()
							+ " o primeiro parametro deve ser um comando de relacao. Consulte o comando help() para mais informacoes");
					return null;
				}
				s1 = sarray[1].trim();
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				s = tirarParentesesFinal(s, val, pares);

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoDir = lerString(s1);
				relacaoEsq = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					Tupla te = (Tupla) relacaoEsq.open().next();
					Tupla td = (Tupla) relacaoDir.open().next();
					relacaoEsq.close();
					relacaoDir.close();

					if (te.getTamanhoTupla() != td.getTamanhoTupla()) {
						te.open();
						td.open();
						mensagemDeErro(
								"nao foi possível criar o operador Interseccao : as tuplas nao possuem os mesmos campos");
						return null;
					}
					te.open();
					td.open();
					return new Interseccao(relacaoDir, relacaoEsq);
				}
				mensagemErro = "nao foi possivel criar o operador Intersecao: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;
			case "produtocartesiano":// produtocartesiano(relacaoEsq(<parametros>),
										// relacaoDir(<oarametros>))
				s1 = sarray[1].trim();
				indiceAS1 = s.indexOf(s1) + s1.length();
				temp = s.toCharArray();
				while (temp[indiceAS1] != '(' && temp[indiceAS1] < temp.length)
					indiceAS1++;
				indiceFS1 = pares.get(indiceAS1).getIndiceFechado();
				s1 += s.substring(indiceAS1, indiceFS1 + 1);// inclui o inicial
															// e exclui o final
				indiceIS2 = indiceFS1 + 1;

				temp = s.toCharArray();
				while (temp[indiceIS2] != ',')
					indiceIS2++;
				s = tirarParentesesFinal(s, val, pares);

				s2 = s.substring(indiceIS2 + 1).trim();

				relacaoEsq = lerString(s1);
				relacaoDir = lerString(s2);
				if (relacaoDir != null && relacaoEsq != null) {
					return new ProdutoCartesiano(relacaoEsq, relacaoDir);
				}

				mensagemErro = "nao foi possivel criar o operador ProdutoCartesiano: ";

				if (relacaoEsq == null) {
					if (relacaoDir == null) {
						mensagemErro += "relacao direita e relacao esquerda retornaram nulo";
					} else
						mensagemErro += "relacao esquerda retornou nulo";
				} else if (relacaoDir == null)
					mensagemErro += "relacao direita retornou nulo";

				mensagemDeErro(mensagemErro);
				return null;
			case "help":
				mensagemDeErro("COMANDOS PARA RELACOES OPERADORES :");
				// adcionar os que ainda nao foram criados depois
				mensagemDeErro(
						"	Renomeacao(\"<nomeAntigoCampo1> <nomeAntigoCampo2>...\", \"<nomeNovoCampo1> <nomeNovoCampo2>...\", relacao())");
				mensagemDeErro("	Ordenacao(<nome do campo de ordenacao>, <crescente ou decrescente>, relacao())");
				mensagemDeErro("	JuncaoNatural(relacaoDireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro(
						"	NLJ(\"<campo de juncao>\", relacaoDireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro(
						"	HashJoin(\"<campo de juncao>\", relacaoDireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro("	Projecao(\"campo1 campo2...campok\", relacao()");
				mensagemDeErro(
						"	Selecao(CONDICAO(<parametros>), relacao(<parametros>)) -- onde CONDICAO(<parametros>) é um dos comandos válidos de condicao");
				mensagemDeErro(
						"	SelecaoPorIndice(nomeCampoChave, valorChaveBuscada, Indice(<parametros>) ou CriarIndice(<parametros>))");
				mensagemDeErro("	Uniao(relacaoDirireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro("	Diferenca(relacaoDirireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro("	Interseccao(relacaoDirireita(<parametros>), relacaoEsquerda(<parametros>))");
				mensagemDeErro("	ProdutoCartesiano(relacaoDirireita(<parametros>), relacaoEsquerda(<parametros>))");

				mensagemDeErro("");
				mensagemDeErro("COMANDOS RELACOES DADOS :");
				mensagemDeErro(
						"	CriarArquivo(\"<diretorioDoArquivo>/arquivo.txt\", <nome para se referir ao arquivo>)");
				mensagemDeErro("	Arquivo(<nome especificado para se referir ao arquivo>)");
				mensagemDeErro(
						"	CriarIndice(<ArvoreB ou Hash>, tamanho(hash) ou ordem(arvoreb), <nome para se referir ao indice>, <campo chave do indice>, <um dos dois comandos acima>)");
				mensagemDeErro(
						"	//--- se um valor invalido for inserido o tamanho,o padrao para hash e a quantidade de linhas do arquivo divido por 5, e 2 para arvore b");
				mensagemDeErro("	Indice(<nome especificado para se referir ao indice>)");
				mensagemDeErro("	Listar()   //--- lista os arquivos e os indices ja criados");
				mensagemDeErro(
						"	Listar(<nome especificado para se referir ao arquivo>)   //--- lista os campos de um ARQUIVO ja criado");

				mensagemDeErro("");
				mensagemDeErro("COMANDOS DE CONDICAO :");
				mensagemDeErro(
						"	IGUAL(<nome do campo que se quer comparar>, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro(
						"	DIFERENTE(<nome do campo que se quer comparar, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro(
						"	MAIOR(<nome do campo que se quer comparar, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro(
						"	MAIORIGUAL(<nome do campo que se quer comparar, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro(
						"	MENOR(<nome do campo que se quer comparar, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro(
						"	MENORIGUAL(<nome do campo que se quer comparar, <valor do campo que se quer definir para comparacao>) ");
				mensagemDeErro("	E(CONDICAOesq(<parametros), CONDICAOdir(<paranetros>))");
				mensagemDeErro("	OU(CONDICAOesq(<parametros), CONDICAOdir(<paranetros>))");
				mensagemDeErro("	NAO(CONDICAO(<parametros))");

				return null;
			default:
				mensagemDeErro("O comando \"" + val + "\" nao consta na lista de comandos ou esta escrito incorreto");
			}
		}
		mensagemDeErro(
				"A quantidade de parenteses do comando não está balanceada, verifique a quantidade de parenteses e digite sua consulta novamente.");
		return null;
	}

}
