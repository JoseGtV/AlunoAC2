# Projeto de Cadastramento de Alunos
## Descrição
Este é um projeto de cadastramento de alunos desenvolvido em Java utilizando o Android Studio. O objetivo do projeto é permitir que os usuários registrem e gerenciem informações de alunos, como RA (Registro Acadêmico), nome, endereço (CEP, logradouro, complemento, bairro, UF, localidade) e visualizar a lista de alunos cadastrados.

## Funcionalidades
- **Cadastro de Aluno:** Permite o cadastro de novos alunos inserindo informações como RA, nome, CEP, logradouro, complemento, bairro, UF e localidade.
- **Busca de Endereço:** Busca automaticamente o endereço completo ao inserir o CEP.
- Listagem de Alunos: Visualização da lista de alunos cadastrados.
- **Exclusão de Aluno:** Permite a exclusão de alunos da lista.
## Tecnologias Utilizadas
- **Java:** Linguagem de programação utilizada.
- **Android Studio:** Ambiente de desenvolvimento integrado (IDE) utilizado para o desenvolvimento do aplicativo.
- **Retrofit:** Biblioteca para realizar requisições HTTP.
- **API ViaCEP:** API utilizada para buscar endereços a partir do CEP.
## Estrutura do Projeto
- **Activity:** Contém as atividades principais do projeto (MainActivity e ListaAlunoActivity).
- **Adapter:** Contém o adapter AlunoAdapter para o RecyclerView.
- **API:** Contém as interfaces e classes para a comunicação com a API (AlunoService e ApiAluno).
- **Models:** Contém as classes de modelo (Aluno e ViaCepResponse).
## Pré-requisitos
Android Studio instalado.
Gradle configurado.
Conexão com a internet para utilizar a API ViaCEP.