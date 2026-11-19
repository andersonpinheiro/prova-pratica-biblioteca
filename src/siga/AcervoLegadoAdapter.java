package siga;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Adaptador (Adapter) que traduz o formato matricial retornado pelo servico
 * legado {@link AcervoLegadoWS} para objetos de dominio {@link Exemplar}.
 *
 * Centraliza a regra de conversao em um unico ponto do sistema, tratando
 * situacoes em maiuscula ou minuscula de forma consistente.
 */
public class AcervoLegadoAdapter implements AcervoExemplares {

    private final AcervoLegadoWS legado;

    public AcervoLegadoAdapter(AcervoLegadoWS legado) {
        this.legado = Objects.requireNonNull(legado, "O servico legado de acervo nao pode ser nulo.");
    }

    @Override
    public List<Exemplar> listarExemplares() {
        List<Exemplar> exemplares = new ArrayList<>();
        for (String[] linha : legado.consultarExemplares()) {
            Exemplar item = new Exemplar(linha[0], linha[1]);
            item.setDisponivel("D".equalsIgnoreCase(linha[2]));
            exemplares.add(item);
        }
        return exemplares;
    }
}
