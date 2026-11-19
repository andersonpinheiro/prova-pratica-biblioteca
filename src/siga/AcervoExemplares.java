package siga;

import java.util.List;

/**
 * Interface-alvo (Target do padrao Adapter) que define o contrato de acesso
 * aos exemplares no formato de dominio esperado pelos consumidores do SIGA.
 */
public interface AcervoExemplares {

    List<Exemplar> listarExemplares();
}
