package siga;

/**
 * Estado final de um {@link Emprestimo}.
 *
 * Nenhuma transicao e permitida: emprestar, restituir e anular sao recusadas.
 */
public class EstadoEncerrado implements EstadoEmprestimo {

    @Override
    public String getNome() {
        return "ENCERRADO";
    }

    @Override
    public void emprestar(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel emprestar em " + getNome() + ".");
    }

    @Override
    public void restituir(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel restituir em " + getNome() + ".");
    }

    @Override
    public void anular(Emprestimo emprestimo) {
        throw new IllegalStateException("Nao e possivel anular em " + getNome() + ".");
    }
}
