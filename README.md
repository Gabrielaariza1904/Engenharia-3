# 🏨 Sistema de Cadastro de Hóspedes - Engenharia de Software 3

Este projeto consiste no desenvolvimento de uma aplicação web funcional utilizando **Java** para o gerenciamento de hóspedes de um hotel. A aplicação implementa um **CRUD completo** (Create, Read, Update, Delete/Inativar) seguindo rigorosos padrões de arquitetura de software e regras de negócio.

---

## 🎯 Objetivo do Projeto

Criar uma aplicação web com interface amigável e simples para cadastro, consulta, edição e inativação de hóspedes, aplicando conceitos de:
- **MVC** (Model-View-Controller)
- **DAO** (Data Access Object)
- **Strategy** (Pattern para validações)

## 📘 Funcionalidades

### 1. Cadastro de Hóspedes
Permite o registro de novos hóspedes com as seguintes informações:
- **Nome Completo**
- **CPF** (Único no sistema e Validado com máscara)
- **Data de Nascimento**
- **Telefone** (Validado com máscara)
- **E-mail** (Validado)
- **Endereço Completo**

> **Regras de Negócio Implementadas:**
> - O CPF deve ser único no sistema.
> - Bloqueio de caracteres não numéricos em campos de CPF e Telefone.
> - Validação de formato para CPF, E-mail e Telefone.

### 2. Consulta e Listagem
- Visualização de todos os hóspedes cadastrados.
- **Filtro Dinâmico**: Permite buscar hóspedes por **Nome** ou **CPF**.

### 3. Edição
- Permite alterar os dados cadastrais.
- **Validação Inteligente**: Impede a alteração do CPF para um número já existente em *outro* cadastro (evita duplicidade).

### 4. Inativação (Exclusão Lógica)
- Permite inativar um hóspede sem apagar o registro do banco de dados, mantendo o histórico de operações.
- Status muda de "Ativo" para "Inativo" na listagem.

### 5. Auditoria (Log)
- Todas as operações de criação, atualização e inativação geram um registro de **Log de Auditoria** com data e hora da operação.

---

## 🛠️ Tecnologias Utilizadas

- **Backend**:
    - **Java 17+**
    - **Spring Boot 3** (Web Server e Dependency Injection)
    - **JDBC** (Java Database Connectivity) para implementação manual do padrão DAO.
    - **SQLite** (Banco de dados relacional leve e serverless).
    - **Lombok** (Redução de boilerplate).

- **Frontend**:
    - **HTML5 & CSS3** (Estilização customizada, sem frameworks pesados).
    - **JavaScript** (Fetch API para comunicação assíncrona com o backend).

## 🏗️ Arquitetura do Software

O projeto segue estritamente a separação de responsabilidades:

1.  **Model**: Entidades POJO (`Guest`, `AuditLog`).
2.  **View**: Páginas HTML (`register.html`, `guests.html`) servidas estaticamente.
3.  **Controller**: API REST (`GuestController`) que recebe as requisições HTTP.
4.  **Service**: Camada de negócio (`GuestService`) que coordena as validações.
5.  **DAO**: Camada de persistência (`GuestDAOImpl`) que executa SQL puro via JDBC.
6.  **Strategy**: Interfaces e implementações (`CpfValidationStrategy`, etc.) para encapsular regras de validação complexas.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java JDK 17 ou superior instalado.
- Maven instalado.

### Passos
1. **Clone o repositório**:
   ```bash
   git clone https://github.com/Gabrielaariza1904/Engenharia-3.git
   cd Engenharia-3
   ```

2. **Execute a aplicação via Maven**:
   ```bash
   mvn spring-boot:run
   ```

3. **Acesse no navegador**:
   - Aplicação: [http://localhost:8080](http://localhost:8080)
   - O banco de dados ser criado automaticamente em um arquivo chamado `hotel.db` na raiz do projeto.

---

## ✅ Verificação e Qualidade

- **Máscaras de Input**: Campos de CPF e Telefone possuem máscaras automáticas no Frontend para garantir a integridade e facilitar a digitação.
- **Tradução**: Todas as mensagens de erro e sistema estão em Português (PT-BR).

---

**Engenharia de Software 3** - Projeto Prático.
