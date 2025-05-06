package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_Usuario")
public class Usuario {

	@Id
	@Column(name = "id_usuario")
	private long id;

	@Column(name = "cpf", length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "idade")
	private int idade;

	public Usuario() {}

	public Usuario(long id, String cpf, String nome, int idade) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String imprimir() {
		return String.format("[#%d] CPF: %s - Nome: %s - Idade: %d",
					this.id,
					this.cpf,
					this.nome,
					this.idade
		);
	}
}
