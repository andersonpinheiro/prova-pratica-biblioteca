package siga;

/**
 * Interface de estado (State do padrao State) que define o contrato
 * comportamental de cada etapa do ciclo de vida de um {@link Emprestimo}.
 *
 * Cada implementacao encapsula as transicoes permitidas e recusa as
 * operacoes invalidas para aquele estado.
 */
public interface EstadoEmprestimo {

    String getNome();

    void emprestar(Emprestimo emprestimo);

    void restituir(Emprestimo emprestimo);

    void anular(Emprestimo emprestimo);
}
