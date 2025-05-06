package app;

import entity.Usuario;
import enumeration.OpcaoMenu;
import service.UsuarioService;
import util.TecladoUtil;

public class App {

	private static UsuarioService usuarioService = new UsuarioService();

	/**
	 * Método responsável por imprimir o menu
	 */
	private static void imprimirMenu() {
		System.out.println("**********************************");
		System.out.println("                MENU              ");
		System.out.println("**********************************");
		System.out.println("0 - Sair                          ");
		System.out.println("1 - Cadastrar                     ");
		System.out.println("2 - Editar                        ");
		System.out.println("3 - Deletar                       ");
		System.out.println("4 - Pesquisar todos               ");
		System.out.println("5 - Pesquisar pelo CPF            ");
		System.out.println("6 - Pesquisar pela inicial do nome");
		System.out.println("----------------------------------");
	}

	/**
	 * Método responsável por identificar qual opção do menu foi digitada
	 * @return opção do menu digitada
	 */
	private static OpcaoMenu lerOpcaoMenu() {
		int opcaoSelecionada;
		System.out.print("Informe a opção desejada.......: ");
		try {
			opcaoSelecionada = Integer.parseInt(TecladoUtil.leitura());
		} catch (Exception e) {
			opcaoSelecionada = -1;
		}
		return OpcaoMenu.obterPorCodigo(opcaoSelecionada);
	}
	
	/**
	 * Método responsável por ler números sem perder os zeros a esquerda
	 * @param mensagem mensagem a ser exibida para o usuário
	 * @return retorna o valor digitado pelo usuário
	 */
	private static String lerStringNumerica(String mensagem) {
		String valorDigitado;
		System.out.print(mensagem);
		try {
			valorDigitado = TecladoUtil.leitura();
			Long.parseLong(valorDigitado);
		} catch (Exception e) {
			System.out.println("Número inválido, digite novamente!\n");
			valorDigitado = lerStringNumerica(mensagem);
		}
		return valorDigitado;
	}
	
	/**
	 * Método responsável por ler a string digitada pelo usuário
	 * @param mensagem mensagem a ser exibida para o usuário
	 * @return retorna a string digitada pelo usuário
	 */
	private static String lerString(String mensagem) {
		System.out.print(mensagem);
		return TecladoUtil.leitura();
	}
	
	/**
	 * Método responsável por ler o número inteiro digitado pelo usuário
	 * @param mensagem mensagem a ser exibida para o usuário
	 * @return retorna o número digitado pelo usuário
	 */
	private static int lerInteiro(String mensagem) {
		int numeroDigitado;
		System.out.print(mensagem);
		try {
			numeroDigitado = Integer.parseInt(TecladoUtil.leitura());
		} catch (Exception e) {
			System.out.println("Número inválido, digite novamente!\n");
			numeroDigitado = lerInteiro(mensagem);
		}
		return numeroDigitado;
	}
	
	/**
	 * Método responsável por ler o número longo digitado pelo usuário
	 * @param mensagem mensagem a ser exibida para o usuário
	 * @return retorna o número digitado pelo usuário
	 */
	private static long lerLong(String mensagem) {
		long numeroDigitado;
		System.out.print(mensagem);
		try {
			numeroDigitado = Long.parseLong(TecladoUtil.leitura());
		} catch (Exception e) {
			System.out.println("Número inválido, digite novamente!\n");
			numeroDigitado = lerLong(mensagem);
		}
		return numeroDigitado;
	}
	
	/**
	 * Método responsável por executar a aplicação
	 * @param args argumentos
	 */
	public static void main(String[] args) {
		// Loop infinito
		for(;;) {
			imprimirMenu();
			OpcaoMenu opcaoMenu = lerOpcaoMenu();

			switch (opcaoMenu) {
				case CADASTRAR:
					cadastrar();
					break;
				case EDITAR:
					editar();
					break;
				case REMOVER:
					remover();
					break;
				case PESQUISAR_TODOS:
					pesquisarTodos();
					break;
				case PESQUISAR_PELO_CPF:
					pesquisarPeloCpf();
					break;
				case PESQUISAR_PELAS_INICIAIS:
					pesquisarPelasIniciais();
					break;
				case SAIR:
					sair();
					break;
				default:
					System.out.println("Opção inválida!\n");
			}
		}
	
	}

	/**
	 * 
	 * @param opcaoMenu
	 */
	private static void imprimirOpcaoMenu(OpcaoMenu opcaoMenu) {
		System.out.println("\n----------------------------------");
		System.out.println(opcaoMenu.getDescricao());
		System.out.println("----------------------------------");
	}
	
	private static void sair() {
		System.out.println("Obrigado por utilizar nosso sistema!");
		System.exit(0);
	}

	private static void pesquisarPelasIniciais() {
		imprimirOpcaoMenu(OpcaoMenu.PESQUISAR_PELAS_INICIAIS);
		String iniciais = lerString("Digite as iniciais do nome usuário: ");
		usuarioService.pesquisarPelasIniciais(iniciais);
	}

	private static void pesquisarPeloCpf() {
		imprimirOpcaoMenu(OpcaoMenu.PESQUISAR_PELO_CPF);
		String cpf = lerStringNumerica("Digite o CPF..: ");
		usuarioService.pesquisarPeloCpf(cpf);
	}

	private static void pesquisarTodos() {
		imprimirOpcaoMenu(OpcaoMenu.PESQUISAR_TODOS);
		usuarioService.pesquisarTodos();
	}

	private static void remover() {
		try {
			imprimirOpcaoMenu(OpcaoMenu.REMOVER);
			usuarioService.pesquisarTodos();
			long id = lerLong("Digite o id do usuário: ");
			usuarioService.remover(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void editar() {
		try {
			imprimirOpcaoMenu(OpcaoMenu.EDITAR);
			usuarioService.pesquisarTodos();
			
			long id = lerLong("Digite o id do usuário a ser editado: ");
			Usuario usuarioEncontrado = usuarioService.pesquisarPeloId(id);
			if (usuarioEncontrado == null) {
				System.out.println("Nenhum usuário encontrado ID " + id);
				return;
			}
			
			String nome = lerString("Digite o nome.: ");
			String cpf = lerStringNumerica("Digite o CPF..: ");
			int idade = lerInteiro("Digite a idade: ");
			
			usuarioEncontrado.setNome(nome);
			usuarioEncontrado.setCpf(cpf);
			usuarioEncontrado.setIdade(idade);
			
			usuarioService.editar(usuarioEncontrado);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void cadastrar() {
		try {
			imprimirOpcaoMenu(OpcaoMenu.CADASTRAR);
			
			long id = lerLong("Digite o id...: ");
			String nome = lerString("Digite o nome.: ");
			String cpf = lerStringNumerica("Digite o CPF..: ");
			int idade = lerInteiro("Digite a idade: ");
			
			Usuario usuario = new Usuario(id, cpf, nome, idade);
			usuarioService.cadastrar(usuario);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
