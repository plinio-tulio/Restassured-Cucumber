package br.com.automacao.factory;

import br.com.automacao.entity.Empregado;
import br.com.automacao.utils.FakerUtils;

public class EmpregadoFactory {
    public static Empregado obterEmpregadoValido(){
        Empregado empregado = new Empregado();
        empregado.setName(FakerUtils.generateRandomFullName());
        empregado.setAge(30);
        empregado.setSalary("223344");
        return empregado;
    }
}
