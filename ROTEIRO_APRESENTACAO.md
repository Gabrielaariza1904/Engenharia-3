# Roteiro de Apresentação Guiado - TCC

Este roteiro foi preparado para você ler durante sua apresentação. Siga passo a passo as instruções de **Ação** e **O que mostrar**, e leia o **Texto para ler** de forma natural.

---

## 1. Introdução e Arquitetura MVC

**Ação:**
Abra a pasta `src/main/java/com/hotel` no explorador de arquivos do projeto.

**O que mostrar:**
Passe o mouse sobre os pacotes `controller`, `model`, e o visual (que está em `src/main/resources/static`).

**Texto para ler:**
"Bom dia/boa noite a todos. O projeto foi estruturado seguindo o padrão MVC para garantir organização e facilidade de manutenção.
Aqui temos o **Model**, que representa os dados como o 'Hóspede'.
O **Controller**, que gerencia as requisições e conecta tudo.
E a **View**, construída com HTML, CSS e Javascript. Ela está localizada nos recursos estáticos para garantir que o visual esteja desacoplado da lógica do servidor. Isso significa que o Back-end foca apenas no processamento dos dados, enquanto o Front-end cuida exclusivamente da apresentação."

---

## 2. Banco de Dados (DAO)

**Ação:**
Abra o arquivo `src/main/java/com/hotel/dao/GuestDAOImpl.java`.

**O que mostrar:**
Role até o método `save` (linhas 35-39) ou `findAll` (linhas 57-60). Destaque a String com o comando SQL (`INSERT INTO...` ou `SELECT...`).

**Texto para ler:**
"Para a persistência dos dados, utilizamos o padrão **DAO** (Data Access Object).
O objetivo desta classe é isolar a complexidade do SQL do restante do sistema.
Como podem ver aqui, os comandos de banco, como INSERTS e UPDATES, ficam encapsulados.
Isso significa que se precisarmos mudar de banco de dados no futuro, mexemos apenas aqui, sem quebrar as regras de negócio do sistema."

---

## 3. Validação e Regras de Negócio

**Ação:**
Abra o arquivo `src/main/java/com/hotel/strategy/CpfValidationStrategy.java`.

**O que mostrar:**
Destaque o bloco `if` com a verificação de regex ou de nulos (linhas 18-20).

**Texto para ler:**
"A integridade dos dados é um ponto fundamental. Aqui temos um exemplo de regra de negócio aplicada à validação.
Antes de salvar qualquer registro, o sistema verifica, por exemplo, se o CPF está no formato correto e se possui 11 dígitos.
Essa validação centralizada impede que dados 'sujos' ou incorretos entrem no no banco, garantindo a confiabilidade da informação."

---

## 4. O Padrão Strategy (Requisito de Projeto)

**Ação:**
Abra o arquivo `src/main/java/com/hotel/service/GuestService.java`.

**O que mostrar:**
Destaque o laço `for` dentro do método `registerGuest` (linhas 30-32).

**Texto para ler:**
"Atendendo aos requisitos de arquitetura do projeto, aplicamos o padrão de projeto **Strategy** para as validações.
Observem este laço: o Serviço principal não precisa conhecer as regras específicas de CPF ou E-mail. Ele apenas percorre uma lista de estratégias e executa 'validate'.
Isso torna o sistema extremamente escalável. Se amanhã precisarmos validar 'Passaporte' ou 'Idade Mínima', basta criarmos uma nova classe de estratégia, sem precisar alterar nenhuma linha deste código principal."

---

## 5. Requisitos do Sistema

**Ação:**
Mantenha o código aberto ou volte para a tela inicial do projeto apenas para ilustrar.

**O que mostrar:**
Apenas leia os tópicos abaixo de forma clara.

**Texto para ler:**
"Resumindo as capacidades do sistema:
Nos **Requisitos Funcionais**, entregamos o ciclo completo de gerenciamento de hóspedes: Cadastro, Listagem, Atualização e buscas dinâmicas. Um ponto de destaque é a diferenciação entre **Inativação** (que mantém o histórico do cliente) e **Exclusão** (que remove permanentemente o registro), atendendo a diferentes regras de negócio.
Nos **Requisitos Não-Funcionais**, garantimos:
**Arquitetura em Camadas**, que organiza o código e facilita a manutenção;
**Extensibilidade**, permitindo que o sistema cresça sem traumas;
**Portabilidade**, usando o **SQLite** que é um banco leve e não requer instalação de servidor dedicado;
E uma **Interface Web**, acessível diretamente pelo navegador, sem necessidade de instalar o software cliente em cada máquina."

---

## 6. Front-end

**Ação:**
Abra a pasta `src/main/resources/static` e clique rapidamente em `index.html` ou `style.css`.

**O que mostrar:**
Mostre a estrutura das tags HTML ou o CSS.

**Texto para ler:**
"Por fim, na interface com o usuário:
Utilizamos **HTML** para estruturar as informações da tela, como o formulário de cadastro com campos detalhados para Endereço (CEP, Logradouro, Cidade, etc);
**CSS3** para dar o estilo, cores e layout;
E **Javascript** para criar a interatividade, incluindo máscaras automáticas para CPF e CEP, garantindo uma melhor experiência de uso e dados mais limpos.
Isso torna o sistema leve e compatível com navegadores modernos, sem a complexidade de frameworks desktop antigos."

---

## 7. Considerações Finais

**Ação:**
Volte o slide/tela para o título principal ou uma tela de 'Obrigado'.

**O que mostrar:**
Mantenha uma postura cordial.

**Texto para ler:**
"Concluindo, o desenvolvimento deste sistema permitiu aplicar na prática conceitos fundamentais de Engenharia de Software, resultando em uma aplicação funcional, organizada e pronta para evoluir.
Agradeço a atenção de todos e estou à disposição para eventuais dúvidas."
