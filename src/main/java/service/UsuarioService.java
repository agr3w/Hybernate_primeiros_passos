package service;

import java.util.List;

import entity.Usuario;
import repository.UsuarioRepository;

public class UsuarioService {

	private UsuarioRepository usuarioRepository = new UsuarioRepository();
	
	private void validarId(long id) throws Exception {
		if (id <= 0) {
			throw new Exception("Usuário inválido: id deve ser maior que zero!");
		}
	}
	
	private void validarCpf(String cpf) throws Exception {
		if (cpf == null) {
			throw new Exception("Usuário inválido: CPF nulo!");
		} else if (cpf.trim().equals("")) {
			throw new Exception("Usuário inválido: CPF vazio!");
		} else if (cpf.length() != 11) {
			throw new Exception("Usuário inválido: CPF deve ter 11 números!");
		}
		try {
			long cpfNumerico = Long.parseLong(cpf);
			if (cpfNumerico < 0) {
				throw new Exception("Usuário inválido: CPF negativo!");
			}
		} catch (Exception e) {
			throw new Exception("Usuário inválido: CPF deve ser numérico!");
		}
	}
	
	private void validarNome(String nome) throws Exception {
		if (nome == null) {
			throw new Exception("Usuário inválido: nome nulo!");
		} else if (nome.trim().equals("")) {
			throw new Exception("Usuário inválido: nome vazio!");		
		}
	}

	private void validarIdade(int idade) throws Exception {
		if (idade < 1) {
			throw new Exception("Usuário inválido: Idade deve ser maior que zero!");
		}
	}
	
	private void verificarIdJaCadastrado(long id) throws Exception {
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloId(id);
		if (usuarioEncontrado != null) {
			throw new Exception("Usuário inválido: ID já cadastrado!");
		}
	}

	private void verificarCpfJaCadastrado(long id, String cpf) throws Exception {
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloCpf(cpf);
		if (usuarioEncontrado != null && usuarioEncontrado.getId() != id) {
			throw new Exception("Usuário inválido: CPF já cadastrado!");
		}
	}

	public void cadastrar(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("Usuário inválido: objeto nulo");
		}
		validarId(usuario.getId());
		validarNome(usuario.getNome());
		validarCpf(usuario.getCpf());
		validarIdade(usuario.getIdade());
		
		verificarIdJaCadastrado(usuario.getId());
		verificarCpfJaCadastrado(usuario.getId(), usuario.getCpf());
		
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloCpf(usuario.getCpf());
		if (usuarioEncontrado != null) {
			throw new Exception("Usuário inválido: CPF já cadastrado!");
		}
		usuarioRepository.inserir(usuario);
	}

	public void editar(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("Usuário inválido: objeto nulo");
		}
		validarId(usuario.getId());
		validarNome(usuario.getNome());
		validarCpf(usuario.getCpf());
		validarIdade(usuario.getIdade());
		
		verificarCpfJaCadastrado(usuario.getId(), usuario.getCpf());
		usuarioRepository.atualizar(usuario);
	}
	
	public void remover(long id) throws Exception {
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloId(id);
		if (usuarioEncontrado == null) {
			throw new Exception("Usuário não encontrado! ID: " + id);
		}
		usuarioRepository.remover(id);
	}
	
	public void pesquisarTodos() {
		List<Usuario> usuariosEncontrados = usuarioRepository.pesquisarTodos();
		if (usuariosEncontrados != null && !usuariosEncontrados.isEmpty()) {
			System.out.println("Registros encontrados: " + usuariosEncontrados.size());
			for (Usuario usuario : usuariosEncontrados) {
				System.out.println(usuario.imprimir());
			}			
		} else {
			System.out.println("Nenhum usuário cadastrado na base de dados");
		}
	}
	
	public Usuario pesquisarPeloId(long id) {
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloId(id);
		if (usuarioEncontrado == null) {
			System.out.println("Nenhum usuário encontrado para o ID " + id);
		} else {
			usuarioEncontrado.imprimir();
		}
		return usuarioEncontrado;
	}
	
	public void pesquisarPeloCpf(String cpf) {
		Usuario usuarioEncontrado = usuarioRepository.pesquisaPeloCpf(cpf);
		if (usuarioEncontrado == null) {
			System.out.println("Nenhum usuário encontrado para o CPF " + cpf);
		} else {
			usuarioEncontrado.imprimir();
		}
	}
	
	public void pesquisarPelasIniciais(String iniciais) {
		List<Usuario> usuariosEncontrados = usuarioRepository.pesquisaPelaInicialDoNome(iniciais);
		if (usuariosEncontrados == null || usuariosEncontrados.isEmpty()) {
			System.out.println("Nenhum usuário encontrado para as iniciais '" + iniciais + "'");
		} else {
			for (Usuario usuario : usuariosEncontrados) {
				usuario.imprimir();
			}
		}
	}
}