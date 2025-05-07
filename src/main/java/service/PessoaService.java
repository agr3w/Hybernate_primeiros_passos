package service;

import java.util.List;

import entity.Endereco;
import entity.Pessoa;
import repository.EnderecoRepository;
import repository.PessoaRepository;

public class PessoaService {

	
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
		endereco.setCep(83703080);
		
		
	}
}