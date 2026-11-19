package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista apenas os exemplares disponiveis para emprestimo, dependendo apenas
 * da abstracao {@link AcervoExemplares}.
 */
public class CatalogoExemplares {

    private final AcervoExemplares acervo;

    public CatalogoExemplares(AcervoExemplares acervo) {
        this.acervo = acervo;
    }

    public List<Exemplar> listarDisponiveis() {
        List<Exemplar> disponiveis = new ArrayList<>();
        for (Exemplar item : acervo.listarExemplares()) {
            if (item.isDisponivel()) {
                disponiveis.add(item);
            }
        }
        return disponiveis;
    }
}
