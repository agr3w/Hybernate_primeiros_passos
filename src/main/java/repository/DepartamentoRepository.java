package repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import entity.Departamento;

/**
 * Classe responsável por realizar operações no banco de dados referente a
 * entidade Pedido: - Inserir - Atualizar - Deletar - Consultar
 */
public class DepartamentoRepository {

	private Session session;

	public DepartamentoRepository() {
		this.session = new Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory()
				.openSession();
	}

	/**
	 * Método responsável por inserir um usuário no banco de dados.
	 * 
	 * @param departamento dados do usuário
	 */
	public void inserir(Departamento departamento) {
		try {
			session.beginTransaction();
			session.persist(departamento); //persiste nosso objeto (salva)
			session.getTransaction().commit();
			System.out.println("Departamento inserido com sucesso");
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao inserir o usuário: " + e.getMessage());
		}
	}

	/**
	 * Método responsável por atualizar os dados do usuário no banco de dados.
	 * 
	 * @param departamento dados do usuário
	 */
	public void atualizar(Departamento departamento) {
		try {
			session.beginTransaction();
			session.merge(departamento);
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
	public List<Departamento> pesquisarTodos() {
		List<Departamento> registros = new ArrayList<>();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Departamento> criteria = builder.createQuery(Departamento.class);
			criteria.from(Departamento.class);
			registros = session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			System.out.println("Ocorreu um problema ao consultar todos os usuários: " + e.getMessage());
		}
		return registros;
	}

	
	public Departamento pesquisaPeloId(long id) {
		Departamento departamento = null;
		try {
			departamento = session.find(Departamento.class, id);
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Ocorreu um problema ao consultar o usuário pelo Id: " + e.getMessage());
		}
		return departamento;
	}

}
