package siga;

/**
 * Estado que representa um {@link Emprestimo} efetivado com o exemplar em posse do leitor.
 *
 * Transicao permitida:
 * - restituir -> ENCERRADO
 *
 * Operacoes recusadas: emprestar, anular.
 */
public class EstadoEmprestado implements EstadoEmprestimo {

    @Override
    public String getNome() {
        return "EMPRESTADO";
    }

    @Override
    public void emprestar(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel emprestar em " + getNome() + ".");
    }

    @Override
    public void restituir(Emprestimo emprestimo) {
        emprestimo.alterarEstado(new EstadoEncerrado());
    }

    @Override
    public void anular(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel anular em " + getNome() + ".");
    }
}
