# TrilhaJusta – API (Spring Boot)

API backend oficial do projeto **TrilhaJusta**, responsável pela gestão de Usuários, Competências, Trilhas, Cursos, Vagas e Candidaturas, integrando:

- Autenticação com JWT  
- Paginação e validação  
- Cache com Caffeine  
- Mensageria com RabbitMQ  
- IA Generativa (Spring AI)  
- Ranking de compatibilidade via Oracle + PL/SQL  
- Suporte a i18n  
- Documentação Swagger  

Esta API representa o backend principal da solução global do projeto **TrilhaJusta**.

---

# 🌐 Arquitetura Geral

Controller → Service → Repository → Oracle/JPA
↘ RabbitMQ (Eventos)
↘ Spring AI (Recomendações)

yaml
Copiar código

Camadas:

| Camada | Local | Descrição |
|-------|--------|-----------|
| Controller | `controller/` | Exposição dos endpoints REST |
| Service | `service/` | Regras de negócio |
| Repository | `repository/` | Persistência via Spring Data JPA |
| Oracle | `oracle/` | Chamadas para PL/SQL com SimpleJdbcCall |
| Messaging | `messaging/` | Produtores e consumidores RabbitMQ |
| Security | `security/` | JWT, filtros e roles |
| AI | `ai/` | Uso do Spring AI / OpenAI |
| Exception | `exception/` | Tratamento de erros (ControllerAdvice) |

---

# 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA (Oracle)  
- Spring Security + JWT  
- Spring Cache + Caffeine  
- Spring AMQP (RabbitMQ)  
- Spring AI (OpenAI)  
- Bean Validation  
- Actuator / Swagger  
- Dockerfile  
- PL/SQL Oracle para ranking  

---

# 📦 Requisitos

- Java 17  
- Maven 3.9+  
- Banco Oracle (com package `PKG_MATCH`)  
- RabbitMQ  
- Variáveis de ambiente configuradas  

---

# ⚙️ Variáveis de Ambiente

| Variável | Descrição |
|---------|-----------|
| ORACLE_HOST | IP ou host do Oracle |
| ORACLE_PORT | Porta (ex.: 1521) |
| ORACLE_SERVICE | Serviço (ex.: ORCL) |
| ORACLE_USER | Username |
| ORACLE_PASSWORD | Senha |
| RABBIT_HOST | Host do RabbitMQ |
| RABBIT_PORT | Porta |
| RABBIT_USER | Usuário |
| RABBIT_PASSWORD | Senha |
| JWT_SECRET | Segredo do JWT |
| OPENAI_API_KEY | (Opcional) Para IA generativa |

---

# ▶️ Executando o Projeto

```bash
mvn clean package -DskipTests
mvn spring-boot:run
Ou:

bash
Copiar código
java -jar target/trilhajusta-api.jar
📘 Documentação Swagger
Swagger UI: http://localhost:8080/swagger-ui.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

🔐 Segurança (Spring Security + JWT)
Login gera token JWT

Roles: ROLE_USER, ROLE_ADMIN

Proteção via filtro JwtAuthFilter

Rotas públicas:

/api/v1/auth/**

GET públicos de cursos, trilhas, vagas, competências

/swagger-ui/**

/v3/api-docs/**

Rotas privadas:

CRUDs de escrita

Candidaturas

Match Oracle

🌐 Internacionalização (i18n)
Arquivos:

messages.properties

messages_pt_BR.properties

messages_en_US.properties

📡 Mensageria (RabbitMQ)
Fila padrão:

Copiar código
trilhajusta.novacandidatura
Fluxo:

Candidatura criada

Evento NovaCandidaturaEvent publicado

Listener processa e registra ações

🧮 Integração Oracle / PL-SQL
Ranking de compatibilidade via:

scss
Copiar código
PKG_MATCH.FN_SCORE_COMPATIBILIDADE(usuarioId, vagaId)
A chamada usa:

java
Copiar código
new SimpleJdbcCall(jdbcTemplate)
   .withCatalogName("PKG_MATCH")
   .withFunctionName("FN_SCORE_COMPATIBILIDADE");
🧠 IA Generativa (Spring AI)
Endpoint:

bash
Copiar código
GET /api/v1/ai/recomendar-trilhas?usuarioId=
GET /api/v1/ai/recomendar-trilhas?perfil=
Gera texto com recomendações personalizadas via OpenAI.

📚 Endpoints (v1)
🔑 Autenticação
Método	Rota	Descrição
POST	/api/v1/auth/signup	Cadastro
POST	/api/v1/auth/login	Login + JWT

👤 Usuários
Método	Rota	Descrição
GET	/api/v1/usuarios	Lista com paginação e filtro
POST	/api/v1/usuarios	Criar
PUT	/api/v1/usuarios/{id}	Atualizar
DELETE	/api/v1/usuarios/{id}	Deletar

🧩 Competências
Método	Rota
GET	/api/v1/competencias
POST	/api/v1/competencias
PUT	/api/v1/competencias/{id}
DELETE	/api/v1/competencias/{id}

🎯 Trilhas
Método	Rota
GET	/api/v1/trilhas
POST	/api/v1/trilhas
PUT	/api/v1/trilhas/{id}
DELETE	/api/v1/trilhas/{id}
GET	/api/v1/trilhas/{id}/cursos

📘 Cursos
Método	Rota
GET	/api/v1/cursos
POST	/api/v1/cursos
PUT	/api/v1/cursos/{id}
DELETE	/api/v1/cursos/{id}

💼 Vagas
Método	Rota	Observação
GET	/api/v1/vagas	Filtros: cidade, uf, competência
POST	/api/v1/vagas	
PUT	/api/v1/vagas/{id}	
DELETE	/api/v1/vagas/{id}	

📝 Candidaturas
Método	Rota	Descrição
POST	/api/v1/candidaturas?usuarioId=&vagaId=	Cria + publica evento
PATCH	/api/v1/candidaturas/{id}/status	Atualiza status
GET	/api/v1/candidaturas	Paginação

⭐ Match Oracle (Ranking)
| GET | /api/v1/match/usuarios/{id}/vagas | Ranking via PL/SQL |

🐳 Docker – Build e Run
Build
bash
Copiar código
docker build -t trilhajusta-api .
Run
bash
Copiar código
docker run -p 8080:8080 \
  -e ORACLE_HOST=... \
  -e ORACLE_PORT=1521 \
  -e ORACLE_SERVICE=ORCL \
  -e ORACLE_USER=... \
  -e ORACLE_PASSWORD=... \
  -e RABBIT_HOST=... \
  -e RABBIT_PORT=5672 \
  -e RABBIT_USER=guest \
  -e RABBIT_PASSWORD=guest \
  -e JWT_SECRET=meusegredo \
  -e OPENAI_API_KEY=chave \
  trilhajusta-api
🧪 Testes (QA)
Testes manuais feitos via:

Swagger

Postman

Testes de carga simples nas rotas paginadas

Testes de login / token / expiração

✔️ Conclusão
Este backend cumpre todos os requisitos da Global Solution:

Segurança

Persistência Oracle

PL/SQL avançado

RabbitMQ

Paginação + validação

i18n

Cache

IA Generativa

Documentação completa

yaml
Copiar código

---

