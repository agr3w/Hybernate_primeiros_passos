package app;

import entity.Endereco;
import entity.Pessoa;
import repository.EnderecoRepository;
import repository.PessoaRepository;

public class AppPessoaEndereco {

	
	public static void main(String[] args) {
		//Testando pessoa
		PessoaRepository pessoaRepository = new PessoaRepository();
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Weslley");
		pessoaRepository.inserir(pessoa);
		
		
		//Testando Endereço
		EnderecoRepository enderecoRepository = new EnderecoRepository();
		Endereco endereco = new Endereco();
		endereco.setPessoa(pessoa);
		endereco.setCep(80000000);
		endereco.setCidade("Araucaria");
		endereco.setLogradouro("rua...");
		endereco.setNumero(1000);
		endereco.setUf("PR");
		enderecoRepository.inserir(endereco);
		
		System.out.println(pessoa.getNome());
		System.out.println(endereco.getCep());

	}
}