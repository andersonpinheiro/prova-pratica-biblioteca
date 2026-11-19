package siga;

/**
 * Estado inicial de um {@link Emprestimo}.
 *
 * Transicoes permitidas:
 * - emprestar -> EMPRESTADO
 * - anular    -> ENCERRADO
 *
 * Operacao recusada: restituir.
 */
public class EstadoReservado implements EstadoEmprestimo {

    @Override
    public String getNome() {
        return "RESERVADO";
    }

    @Override
    public void emprestar(Emprestimo emprestimo) {
        emprestimo.alterarEstado(new EstadoEmprestado());
    }

    @Override
    public void restituir(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel restituir em " + getNome() + ".");
    }

    @Override
    public void anular(Emprestimo emprestimo) {
        emprestimo.alterarEstado(new EstadoEncerrado());
    }
}
