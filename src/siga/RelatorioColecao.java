package siga;

import java.util.List;

/**
 * Gera o relatorio completo de exemplares da colecao, dependendo apenas
 * da abstracao {@link AcervoExemplares}.
 */
public class RelatorioColecao {

    private final AcervoExemplares acervo;

    public RelatorioColecao(AcervoExemplares acervo) {
        this.acervo = acervo;
    }

    public List<Exemplar> listarTodos() {
        return acervo.listarExemplares();
    }

    public void imprimir() {
        System.out.println("   Situacao segundo o relatorio:");
        for (Exemplar item : listarTodos()) {
            System.out.println("     " + item + " - "
                    + (item.isDisponivel() ? "disponivel" : "baixado"));
        }
    }
}
