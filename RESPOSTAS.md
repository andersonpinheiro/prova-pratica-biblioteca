# Respostas — Sistema de Emprestimo da Biblioteca

Nome completo: Anderson Luís Pinheiro  RA: ______________

---

# Etapa 1 — Diagnóstico

## Problema 1 — a conversão do acervo central

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

A conversão dos dados retornados pelo `AcervoLegadoWS` está duplicada nas classes `CatalogoExemplares` e `RelatorioColecao`. Cada uma interpreta a matriz de textos e transforma a letra que representa a situação do exemplar em informação compreendida pelo sistema. Como as implementações são mantidas separadamente, podem apresentar regras diferentes, como ocorre com o exemplar TOM-2003, que pode ser interpretado de maneira diferente em cada classe. Quando o sistema cresce ou o formato do serviço legado muda, cada ponto de conversão precisa ser ajustado individualmente, aumentando a chance de inconsistências e retrabalho.

**Princípio SOLID violado:**

Princípio da Responsabilidade Única (SRP). As classes de catálogo e de relatório, além de suas responsabilidades principais, também assumem a responsabilidade de interpretar e converter o formato específico do serviço legado. Essa regra de conversão deveria ficar centralizada em um componente próprio.

**Classes afetadas (pelo nome):**

`CatalogoExemplares`, `RelatorioColecao` e `AcervoLegadoWS`.

**Que outra dependência indevida essas classes têm em relação ao
acervo central, e por que ela é um problema:**

Além de duplicarem a conversão, `CatalogoExemplares` e `RelatorioColecao` dependem diretamente da classe concreta `AcervoLegadoWS` e de seu formato específico de retorno (`String[][]`). Isso caracteriza acoplamento à implementação do serviço externo. Se o serviço for substituído ou seu formato mudar, as duas classes consumidoras também precisarão ser alteradas. Essa dependência dificulta testes isolados e contraria o Princípio da Inversão de Dependência (DIP), pois os consumidores deveriam depender de uma abstração, e não diretamente de uma implementação concreta.

## Problema 2 — o ciclo de vida de Emprestimo

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

A classe `Emprestimo` mantém a situação em uma variável `String` e utiliza condicionais nos métodos `emprestar()`, `restituir()` e `anular()` para verificar se cada operação é permitida. Assim, as regras de cada estado ficam concentradas e espalhadas na própria classe, em vez de estarem organizadas por fase do ciclo de vida. Quando um novo estado ou uma nova regra de transição é necessário, os métodos existentes precisam ser modificados, aumentando a complexidade e o risco de alterar comportamentos que já funcionam. Além disso, representar estados por texto facilita inconsistências e erros de digitação.

**Princípio SOLID violado:**

Princípio Aberto/Fechado (OCP). A classe `Emprestimo` não está suficientemente aberta para extensão e fechada para modificação: a inclusão de novos estados ou mudanças nas transições exige alterar os métodos condicionais da própria classe. Com o padrão State, novos comportamentos podem ser organizados em classes de estado, reduzindo a necessidade de modificar a classe principal.

**Classes afetadas (pelo nome):**

`Emprestimo`. Na solução refatorada, o comportamento passa a ser distribuído entre `EstadoEmprestimo`, `EstadoEmprestimoBase`, `EstadoReservado`, `EstadoEmprestado` e `EstadoEncerrado`.

---

# Etapa 4 — Justificativa

## Padrão aplicado ao Problema 1

**O problema que ele resolve (descreva antes de nomear o padrão):**

O sistema precisava consumir os exemplares de um serviço externo que fornece os dados em uma matriz de textos, sem modificar esse serviço. A interpretação das colunas e a conversão das letras de situação estavam duplicadas nos consumidores, causando resultados divergentes. A solução precisava concentrar essa conversão em um único componente e oferecer aos consumidores uma interface comum, independente do formato e da classe concreta do serviço legado.

**Nome do padrão:**

Adapter (Adaptador). Foi criada a interface `FonteExemplares`, que define a operação de consulta no formato utilizado pelo sistema. A classe `AcervoLegadoAdapter` implementa essa interface, consulta `AcervoLegadoWS` e converte os dados recebidos em uma lista de objetos `Exemplar`. A comparação da situação utiliza `equalsIgnoreCase`, tratando corretamente letras maiúsculas e minúsculas. Dessa forma, `CatalogoExemplares` e `RelatorioColecao` dependem da abstração `FonteExemplares`, e não diretamente do serviço legado.

**Alternativa descartada e por que ela não resolveria por completo:**

Alterar o próprio `AcervoLegadoWS` para que ele retornasse os objetos `Exemplar` ou já entregasse os dados convertidos poderia eliminar parte da conversão duplicada, mas não seria uma solução adequada porque esse serviço representa um componente externo, mantido por outra equipe, cujo código não deve ser modificado. Além disso, essa alternativa alteraria o contrato do serviço legado e manteria o sistema dependente de uma implementação externa. O Adapter permite integrar o formato existente sem modificar o serviço e isola essa dependência.

## Padrão aplicado ao Problema 2

**O problema que ele resolve (descreva antes de nomear o padrão):**

O ciclo de vida do empréstimo possuía três situações — `RESERVADO`, `EMPRESTADO` e `ENCERRADO` —, mas as permissões e transições estavam implementadas por condicionais nos métodos da classe `Emprestimo`. Isso dificultava visualizar todas as regras de cada fase e exigia modificar os métodos da classe principal quando as regras mudavam. Era necessário colocar o comportamento e as transições permitidas em estruturas próprias para cada estado, preservando as operações válidas e rejeitando as inválidas.

**Nome do padrão:**

State (Estado). Foi criada a interface `EstadoEmprestimo`, que define as operações possíveis em cada estado. As classes `EstadoReservado`, `EstadoEmprestado` e `EstadoEncerrado` implementam os comportamentos específicos de cada fase. A classe `Emprestimo` mantém uma referência ao estado atual e delega a ele as operações `emprestar()`, `restituir()` e `anular()`. Assim, cada estado determina as transições permitidas. O método `getSituacao()` continua retornando as mesmas Strings: `RESERVADO`, `EMPRESTADO` e `ENCERRADO`.

**Alternativa descartada e por que ela não resolveria por completo:**

Substituir a variável `String` por um `enum` melhoraria a representação dos estados, evitando valores textuais arbitrários e erros de digitação. Entretanto, apenas o `enum` não retiraria necessariamente as condicionais dos métodos de `Emprestimo` nem colocaria o comportamento e as regras de transição dentro de cada estado. Seria possível acabar mantendo estruturas `if` ou `switch` para decidir o que cada operação pode fazer. O padrão State resolve o problema de forma mais completa porque encapsula o comportamento e as transições em classes específicas para cada estado.