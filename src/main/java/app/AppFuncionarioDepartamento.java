package app;

import entity.Departamento;
import entity.Funcionario;
import repository.DepartamentoRepository;
import repository.FuncionarioRepository;

public class AppFuncionarioDepartamento {
	
	public static void main(String[] args) {
		//Testando funcionario
		FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
		Funcionario funcionario = new Funcionario();
		funcionarioRepository.inserir(funcionario);
		
		//Testando Departamento
		DepartamentoRepository departamentoRepository = new DepartamentoRepository();
		Departamento departamento = new Departamento();
		departamentoRepository.inserir(departamento);
		
		System.out.println(funcionario.getNome());
		System.out.println(departamento.getNome());

	}
}