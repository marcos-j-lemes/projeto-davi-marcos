# Sistema de Autenticação com JWT

## Visão Geral

Este sistema implementa autenticação baseada em JWT (JSON Web Token) com controle de roles (papéis/permissões) para proteger a API REST.

## Estrutura de Autenticação

### Roles Disponíveis
- **ROLE_ADMIN**: Acesso total à API
- **ROLE_USER**: Acesso padrão com permissões limitadas
- **ROLE_GUEST**: Acesso mínimo (leitura apenas)

### Fluxo de Autenticação

```
1. Usuário envia credenciais (email + senha) para /auth/login
2. Sistema valida as credenciais
3. Se válido, gera um JWT token
4. Cliente armazena o token
5. Cliente envia o token em todas as requisições (header Authorization: Bearer <token>)
6. Filtro JwtAuthFilter valida o token em cada requisição
7. Se válido, requisição é processada; se não, retorna 401 Unauthorized
```

## Endpoints da API

### 1. Login
**POST** `/auth/login`

Request:
```json
{
  "email": "usuario@example.com",
  "password": "senha123456"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "id": 1,
  "email": "usuario@example.com",
  "nome": "João Silva",
  "role": "ROLE_USER",
  "expiresIn": 86400
}
```

### 2. Validar Token
**GET** `/auth/validate`

Headers:
```
Authorization: Bearer <seu_token>
```

Response:
```json
true // ou false
```

### 3. Obter Dados do Usuário Atual
**GET** `/auth/me`

Headers:
```
Authorization: Bearer <seu_token>
```

Response:
```json
{
  "userId": 1,
  "email": "usuario@example.com",
  "role": "ROLE_USER"
}
```

## Usando a API Protegida

### Acessar Usuários
**GET** `/users` (requer autenticação)

Headers:
```
Authorization: Bearer <seu_token>
Content-Type: application/json
```

Response:
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "role": {
      "id": 2,
      "nome": "ROLE_USER"
    },
    "ativo": true
  }
]
```

### Criar Usuário
**POST** `/users` (requer autenticação)

Headers:
```
Authorization: Bearer <seu_token>
Content-Type: application/json
```

Request:
```json
{
  "nome": "Maria Santos",
  "email": "maria@example.com",
  "password": "senha123456",
  "role": {
    "id": 2
  }
}
```

## Exemplo de Uso com cURL

### 1. Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "admin123456"
  }'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "id": 1,
  "email": "admin@example.com",
  "nome": "Administrador",
  "role": "ROLE_ADMIN",
  "expiresIn": 86400
}
```

### 2. Usar o token para acessar recursos protegidos
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9..."
```

## Exemplo de Uso com JavaScript (Fetch API)

```javascript
// 1. Login
const loginResponse = await fetch('http://localhost:8080/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    email: 'usuario@example.com',
    password: 'senha123456'
  })
});

const { token } = await loginResponse.json();
localStorage.setItem('token', token);

// 2. Usar o token para acessar recurso protegido
const usersResponse = await fetch('http://localhost:8080/users', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
});

const users = await usersResponse.json();
console.log(users);
```

## Configurações de Segurança

### JWT Secret
Configure no `.env`:
```env
JWT_SECRET=sua-chave-secreta-com-minimo-32-caracteres
```

### Expiração do Token
- Padrão: 24 horas (86400000 ms)
- Configurável em `application.properties`:
```properties
app.jwt.expiration=86400000
```

## Fluxo de Autorização

### Spring Security Configurado Para:

1. **Endpoints Públicos** (sem autenticação):
   - `/auth/login`
   - `/auth/validate`

2. **Endpoints Protegidos** (requer autenticação):
   - `/users/**` - Requer role ROLE_USER ou superior

3. **Controle de Acesso** (Method-level):
   Você pode usar anotações como:
   ```java
   @PreAuthorize("hasRole('ADMIN')")
   @PreAuthorize("hasRole('USER')")
   ```

## Tratamento de Erros

### 401 Unauthorized
```json
{
  "error": "Não autorizado: JWT token ausente ou inválido"
}
```

### 403 Forbidden
```json
{
  "error": "Acesso negado: Seu role não tem permissão para este recurso"
}
```

## Checklist de Implementação

- [x] Adicionar dependências Spring Security e JWT
- [x] Criar Entity Role com relacionamento em User
- [x] Implementar JwtUtil para gerar/validar tokens
- [x] Criar AuthService para lógica de autenticação
- [x] Criar AuthController com endpoints de login
- [x] Implementar JwtAuthFilter para validar tokens
- [x] Configurar SecurityConfig com regras de autorização
- [x] Criar InitialDataLoader para roles padrão
- [x] Atualizar application.properties com JWT config

## Próximos Passos

1. Compile o projeto:
   ```bash
   gradle clean build
   ```

2. Execute a aplicação:
   ```bash
   gradle bootRun
   ```

3. Teste o login primeiro antes de chamar outros endpoints

4. Atualize o ControlUser para incluir os endpoints GET, PUT, DELETE conforme especificado anteriormente

## Segurança Importante

⚠️ **ANTES DE COLOCAR EM PRODUÇÃO:**
- Altere o `JWT_SECRET` para uma chave forte e aleatória
- Use HTTPS em produção
- Implemente refresh tokens para sessões mais longas
- Configure CORS adequadamente para seus domínios
- Adicione rate limiting para login
- Implemente logging de tentativas falhadas
