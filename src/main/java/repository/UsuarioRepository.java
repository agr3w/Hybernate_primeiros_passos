package repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import entity.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Classe responsável por realizar operações no banco de dados referente a
 * entidade Pedido: - Inserir - Atualizar - Deletar - Consultar
 */
public class UsuarioRepository {

	private Session session;

	public UsuarioRepository() {
		this.session = new Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory()
				.openSession();
	}

	/**
	 * Método responsável por inserir um usuário no banco de dados.
	 * 
	 * @param usuario dados do usuário
	 */
	public void inserir(Usuario usuario) {
		try {
			session.beginTransaction();
			session.persist(usuario);
			session.getTransaction().commit();
			System.out.println("Usuário inserido com sucesso");
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao inserir o usuário: " + e.getMessage());
		}
	}

	/**
	 * Método responsável por atualizar os dados do usuário no banco de dados.
	 * 
	 * @param usuario dados do usuário
	 */
	public void atualizar(Usuario usuario) {
		try {
			session.beginTransaction();
			session.merge(usuario);
			session.getTransaction().commit();
			System.out.println("Usuário atualizado com sucesso");
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao atualizar o usuário: " + e.getMessage());
		}
	}

	/**
	 * Método responsável por remover um usuário no banco de dados com base no seu
	 * id.
	 * 
	 * @param id identificação do usuário
	 */
	public void remover(long id) {
		try {
			session.beginTransaction();
			session.remove(id);
			session.getTransaction().commit();
			System.out.println("Usuário removido com sucesso");
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao remover o usuário: " + e.getMessage());
		}
	}

	/**
	 * Método responsável por consultar todos os usuários
	 * 
	 * @return lista de usuários
	 */
	public List<Usuario> pesquisarTodos() {
		List<Usuario> registros = new ArrayList<>();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
			criteria.from(Usuario.class);
			registros = session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			System.out.println("Ocorreu um problema ao consultar todos os usuários: " + e.getMessage());
		}
		return registros;
	}

	/**
 * Método responsável por buscar um usuário pelo CPF.
 * 
 * @param cpf CPF do usuário
 * @return usuário encontrado ou null se não existir
 */
public Usuario pesquisaPeloCpf(String cpf) {
    Usuario usuario = null;
    try {
        session.beginTransaction();
        usuario = session.createQuery("FROM Usuario WHERE cpf = :cpf", Usuario.class)
                .setParameter("cpf", cpf)
                .uniqueResult();
		
        if (usuario != null) {
            System.out.println("Usuário encontrado com o CPF: " + cpf);
            System.out.println("---------------------------------------------");
            System.out.printf("%-5s %-20s %-15s\n", "ID", "Nome", "CPF");
            System.out.println("---------------------------------------------");
            System.out.printf("%-5d %-20s %-15s\n", usuario.getId(), usuario.getNome(), usuario.getCpf());
            System.out.println("---------------------------------------------");
        } else {
            System.out.println("Nenhum usuário encontrado com o CPF: " + cpf);
        }
    } catch (Exception e) {
        session.getTransaction().rollback();
        System.out.println("Ocorreu um problema ao consultar o usuário pelo CPF: " + e.getMessage());
    }
    return usuario;
}

	public Usuario pesquisaPeloId(long id) {
		Usuario usuario = null;
		try {
			usuario = session.find(Usuario.class, id);
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao consultar o usuário pelo Id: " + e.getMessage());
		}
		return usuario;
	}

	/**
	 * Método responsável por buscar usuários cujos nomes começam com uma inicial
	 * específica.
	 * 
	 * @param inicial inicial do nome
	 * @return lista de usuários encontrados
	 */
	public List<Usuario> pesquisaPelaInicialDoNome(String inicial) {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			session.beginTransaction();
			usuarios = session.createQuery("FROM Usuario WHERE nome LIKE :inicial", Usuario.class)
					.setParameter("inicial", inicial + "%")
					.getResultList();
			session.getTransaction().commit();

			System.out.println(usuarios.size() + " usuários encontrados com a inicial: " + inicial);
			System.out.println("---------------------------------------------");
			System.out.printf("%-5s %-20s %-15s\n", "ID", "Nome", "CPF");
			System.out.println("---------------------------------------------");
			for (Usuario usuario : usuarios) {
				System.out.printf("%-5d %-20s %-15s\n", usuario.getId(), usuario.getNome(), usuario.getCpf());
			}
			System.out.println("---------------------------------------------");
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao consultar usuários pelas iniciais do nome: " + e.getMessage());
		}
		return usuarios;
	}

}
