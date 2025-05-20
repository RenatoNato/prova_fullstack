# DOCUMENTAÇÃO DO SISTEMA VIA CEP BACK-END JAVA + React + Vite

## OBJETIVO
Desenvolver uma aplicação completa de cadastro de usuários e endereços, com autenticação baseada em JWT, controle de acesso com roles, consumo de API externa ViaCEP e integração com um front-end Angular.

## BACK-END

### Linguagens e Tecnologias
- Java 17
- Spring Boot 3.3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (jjwt)
- Feign Client (consumo da API ViaCEP)
- Lombok
- Maven

### Estrutura de Diretórios Back-end
```
src/
 └── main/
     ├── java/
     │   └── com/
     │       └── prova/
     │           └── fullstack/
     │               ├── controller/
     │               │   ├── AuthController.java
     │               │   └── UserController.java
     │               ├── dto/
     │               │   ├── UserDTO.java
     │               │   ├── UserRequestDTO.java
     │               │   └── TokenRefreshRequest.java
     │               ├── entity/
     │               │   ├── User.java
     │               │   ├── Address.java
     │               │   ├── Role.java
     │               │   └── RefreshToken.java
     │               ├── repository/
     │               │   ├── UserRepository.java
     │               │   ├── AddressRepository.java
     │               │   ├── RoleRepository.java
     │               │   └── RefreshTokenRepository.java
     │               ├── security/
     │               │   ├── JwtUtil.java
     │               │   ├── JwtAuthenticationFilter.java
     │               │   ├── CustomUserDetails.java
     │               │   ├── CustomUserDetailsService.java
     │               │   ├── AuthRequest.java
     │               │   ├── AuthResponse.java
     │               │   ├── JwtAuthenticationEntryPoint.java
     │               │   └── SecurityConfig.java
     │               ├── service/
     │               │   ├── UserService.java
     │               │   ├── AddressService.java
     │               │   ├── RoleService.java
     │               │   └── RefreshTokenService.java
     │               ├── client/
     │               │   └── ViaCepClient.java
     │               └── FullstackApplication.java
     └── resources/
         ├── application.properties
         └── static/
```

### PROPÓSITO DE CADA CAMADA / CLASSE

- `controller/`: Responsável por expor os endpoints da API.
  - `AuthController`: Realiza login, registro, whoami, refresh e logout.
  - `UserController`: CRUD de usuários com controle de acesso.

- `dto/`: Objetos de transferência de dados.
  - `UserDTO`: Dados de leitura do usuário.
  - `UserRequestDTO`: Dados de escrita do usuário.
  - `TokenRefreshRequest`: Payload para requisição de refresh de token.

- `entity/`: Entidades JPA.
  - `User`: Representa o usuário no sistema.
  - `Address`: Endereço vinculado ao usuário.
  - `Role`: Perfil de acesso (ADMIN, USER).
  - `RefreshToken`: Token de atualização de sessão JWT.

- `repository/`: Repositórios JPA.
  - Interfaces responsáveis pela persistência das entidades.

- `security/`: Segurança da aplicação.
  - `JwtUtil`: Geração e validação de tokens.
  - `JwtAuthenticationFilter`: Filtro de autenticação JWT.
  - `SecurityConfig`: Configuração de segurança e filtros.
  - `AuthRequest/AuthResponse`: Representações do login.
  - `CustomUserDetailsService`: Implementação de `UserDetailsService`.

- `service/`: Regras de negócio.
  - `UserService`: Cadastro e atualização de usuários.
  - `AddressService`: Cadastro de endereços.
  - `RefreshTokenService`: Controle de refresh tokens.
  - `RoleService`: Criação e consulta de roles.

- `client/`:
  - `ViaCepClient`: Interface Feign para buscar dados do endereço pelo CEP.

## FUNCIONALIDADES DO BACKEND
- Registro de usuário com senha criptografada e role padrão.
- Login com JWT e refresh token.
- Logout com invalidação do refresh token.
- CRUD completo de usuários e endereços.
- Busca automática de endereço por CEP.
- Proteção de endpoints por roles.
- Whoami retorna dados do usuário autenticado.
- Cache e paginação avançada.

## INTEGRAÇÃO COM FRONT-END
- Front-end React + Vite rodando em http://localhost:5173
- CORS habilitado para esse domínio.
- Login armazena JWT e Refresh em cookies HttpOnly.
- Cache e paginação avançada.

## MELHORIAS FUTURAS SUGERIDAS
- Upload de avatar.
- Auditoria de alterações.
- Integração com serviços de notificação.
- Testes unitários e de integração.
