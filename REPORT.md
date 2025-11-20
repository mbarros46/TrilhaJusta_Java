# Relatório de Conformidade — TrilhaJusta API

Data: 12/11/2025

Resumo
-------
Analisei o projeto em relação à especificação do exercício "JAVA ADVANCED — Backend Principal (Spring Boot)" e implementei correções para preencher lacunas encontradas. Rodei build Maven para validar compilação.

O que já estava implementado (confirmado)
- Entidades principais: `Usuario`, `Competencia`, `Trilha`, `Curso`, `Vaga`, `Candidatura` (com `CandidaturaStatus`).
- Endpoints REST v1: `/api/v1/auth`, `/api/v1/usuarios`, `/api/v1/competencias`, `/api/v1/trilhas`, `/api/v1/cursos`, `/api/v1/vagas`, `/api/v1/candidaturas`, `/api/v1/match`, `/api/v1/ai/recomendar-trilhas`.
- Spring Data JPA: projeto usa JPA e `spring-boot-starter-data-jpa`.
- Bean Validation: anotações como `@Email`, `@NotBlank` presentes e `spring-boot-starter-validation` no POM.
- Paginação: controllers e serviços usam `Pageable` / `Page<T>`.
- i18n: `messages_pt_BR.properties` e `messages_en_US.properties` presentes e `spring.messages.basename` configurado.
- Cache com Caffeine: `CacheConfig` + `@Cacheable` em serviços (`competencias`, `trilhas`, `vagas`).
- Security: JWT classes (`JwtService`, `JwtAuthFilter`) e `SecurityConfig` presentes.
- Tratamento de erros: `GlobalExceptionHandler` e `ApiError` com payload padrão.
- Mensageria: `RabbitConfig`, `NovaCandidaturaPublisher` e uso em `CandidaturaService.criar()`.
- Oracle: `ojdbc11` no POM, `MatchOracleRepository` chama função PL/SQL via `SimpleJdbcCall`.
- Spring AI: dependência declarada; `AiService` e `AiController` presentes (ajustados para robustez).

Correções aplicadas
- Adicionado repositórios Spring Data JPA faltantes em `com.trilhajusta.repositories`:
  - `UsuarioRepository`, `VagaRepository`, `CompetenciaRepository`, `TrilhaRepository`, `CursoRepository`, `CandidaturaRepository`.
- Ajustada `SecurityConfig` para restringir operações de escrita (POST/PUT/DELETE) a `ROLE_ADMIN` e permitir criação de candidatura por usuário autenticado. PATCH de status de candidatura restrito a ADMIN.
- Tornado `JwtService` tolerante a secrets não-base64 (fallback para UTF-8) para evitar falha em dev com secret em texto plano.
- `AiController` alterado para aceitar `perfil` (texto) ou `usuarioId` (monta perfil a partir do `Usuario` com competências).
- `AiService` reescrito para chamada por reflection do `ChatClient` (evita dependência de tempo de compilação estrita) e tratamento de erros claro.
- Adicionado repositório Spring Milestones no `pom.xml` para permitir resolução da dependência `spring-ai-openai-spring-boot-starter` (milestone).

Validação
- Rodei `mvn -DskipTests package -U` e a build finalizou com SUCCESS. Artefato gerado: `target/trilhajusta-api-1.0.0.jar`.

Pontos pendentes / recomendações
1. JWT secret: para produção, use uma chave HMAC256 adequada (256 bits) codificada em Base64 e configure `JWT_SECRET` no ambiente. O serviço agora aceita texto plano, mas é menos seguro.
2. Autorizações finas: se desejar regras adicionais (ex.: permitir que usuário edite ou delete apenas suas próprias candidaturas), implementar `@PreAuthorize` com checagem de ownership nos controllers/serviços.
3. Testes: adicionar testes unitários e de integração (pelo menos: create candidatura + verificação de publicação de evento; endpoint de login/signup). Posso adicionar exemplos rápidos.
4. CI/CD / Deploy: fornecer um `Dockerfile` (há `docker/Dockerfile`) e instruções de variáveis de ambiente para Oracle, RabbitMQ, JWT_SECRET e OPENAI_API_KEY. Posso preparar um `README_DEPLOY.md` com passos.
5. Monitoramento: considerar adicionar `spring-boot-starter-actuator` com endpoints protegidos.

Como rodar localmente (variáveis necessárias)
```powershell
mvn -DskipTests package
java -jar target/trilhajusta-api-1.0.0.jar
```

Variáveis de ambiente (exemplos)
- ORACLE_HOST, ORACLE_PORT, ORACLE_SERVICE, ORACLE_USER, ORACLE_PASSWORD
- RABBIT_HOST, RABBIT_PORT, RABBIT_USER, RABBIT_PASSWORD
- JWT_SECRET (Base64 recomendado)
- OPENAI_API_KEY (se usar Spring AI provider)

Próximos passos que posso executar agora
- Implementar testes unitários básicos (recomendado).
- Gerar README com instruções de deploy e variáveis de ambiente.
- Ajustar autorização granular (ownership) conforme regras desejadas.

Se desejar, eu sigo e implemento um ou mais desses próximos passos automaticamente.

FIM DO RELATÓRIO
