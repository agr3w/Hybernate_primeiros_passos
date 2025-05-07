package repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import entity.Pessoa;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Classe responsável por realizar operações no banco de dados referente a
 * entidade Pedido: - Inserir - Atualizar - Deletar - Consultar
 */
public class PessoaRepository {

	private Session session;

	public PessoaRepository() {
		this.session = new Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory()
				.openSession();
	}

	/**
	 * Método responsável por inserir um usuário no banco de dados.
	 * 
	 * @param Pessoa dados do usuário
	 */
	public void inserir(Pessoa pessoa) {
		try {
			session.beginTransaction();
			session.persist(pessoa); //persiste nosso objeto (salva)
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
	 * @param Pessoa dados do usuário
	 */
	public void atualizar(Pessoa pessoa) {
		try {
			session.beginTransaction();
			session.merge(pessoa);
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
	public List<Pessoa> pesquisarTodos() {
		List<Pessoa> registros = new ArrayList<>();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
			criteria.from(Pessoa.class);
			registros = session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			System.out.println("Ocorreu um problema ao consultar todos os usuários: " + e.getMessage());
		}
		return registros;
	}

	public Pessoa pesquisaPeloId(long id) {
		Pessoa pessoa = null;
		try {
			pessoa = session.find(Pessoa.class, id);
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao consultar o usuário pelo Id: " + e.getMessage());
		}
		return pessoa;
	}

}
