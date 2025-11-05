# 🚢 Monolithic Cargo Tracker - Sistema DDD

## 📋 Descrição do Projeto

O **Monolithic Cargo Tracker** é uma aplicação monolítica desenvolvida seguindo os princípios de **Domain-Driven Design (DDD)** para gerenciamento e rastreamento de cargas. O sistema implementa múltiplos **Bounded Contexts** em uma única aplicação, demonstrando conceitos avançados de DDD em uma arquitetura coesa.

### 🎯 Objetivos do Projeto
- Demonstrar a implementação de DDD em uma aplicação monolítica
- Gerenciar o ciclo de vida completo de uma carga (booking, routing, handling, tracking)
- Utilizar padrões como Aggregates, Value Objects, Domain Events e Anti-Corruption Layers
- Fornecer uma base para estudos sobre arquitetura de software e DDD

## 🏗️ Arquitetura e Estrutura

### Bounded Contexts Implementados

#### 1. **Booking Context** 📖
- Responsável pela reserva de cargas
- **Aggregate Root**: `Cargo`
- **Value Objects**: `BookingAmount`, `RouteSpecification`, `CargoItinerary`
- **Commands**: `BookCargoCommand`, `RouteCargoCommand`

#### 2. **Routing Context** 🗺️
- Responsável pelo roteamento e definição de rotas
- **Aggregate Root**: `Voyage`
- **Entities**: `CarrierMovement`
- **Value Objects**: `Schedule`, `VoyageNumber`

#### 3. **Handling Context** 🏗️
- Responsável pelo registro de atividades de manuseio
- **Aggregate Root**: `HandlingActivity`
- **Value Objects**: `Type`, `Location`, `VoyageNumber`

#### 4. **Tracking Context** 📍
- Responsável pelo rastreamento de cargas
- **Aggregate Root**: `TrackingActivity`
- **Value Objects**: `TrackingEvent`, `TrackingLocation`

### 📁 Estrutura de Pacotes

```
src/main/java/com/br/hugo/ddd/monolithiccargotracker/
├── booking/                    # Booking Bounded Context
│   ├── application/
│   │   ├── internal/
│   │   │   ├── commandservices/
│   │   │   └── queryservices/
│   │   └── outboundservices/acl/
│   ├── domain/
│   │   ├── model/
│   │   │   ├── aggregates/
│   │   │   ├── commands/
│   │   │   ├── entities/
│   │   │   └── valueobjects/
│   ├── infrastructure/
│   │   ├── repositories/jpa/
│   │   └── services/http/
│   └── interfaces/
│       └── rest/
├── handling/                   # Handling Bounded Context
├── routing/                   # Routing Bounded Context
├── tracking/                  # Tracking Bounded Context
├── shareddomain/              # Shared Domain Objects
│   ├── events/
│   └── model/
└── config/                    # Configurações da Aplicação
```

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java EE 8** - Plataforma empresarial
- **JAX-RS** - API REST
- **JPA 2.2** - Persistência de dados
- **CDI 2.0** - Injeção de dependências
- **Bean Validation** - Validação de dados

### Banco de Dados
- **MySQL 8.0** - Banco de dados relacional
- **JPA/Hibernate** - ORM

### Containerização
- **Docker** - Containerização do MySQL
- **Docker Compose** - Orquestração de containers

### Build & Deploy
- **Maven** - Gerenciamento de dependências e build
- **OpenLiberty** - Servidor de aplicação
- **JUnit** - Testes unitários

## 📋 Pré-requisitos

### Software Necessário
- **Docker** e **Docker Compose**
- **Java 8** ou superior
- **Maven 3.6+**
- **Git**

### Portas Utilizadas
- **9080** - Aplicação Monolithic Cargo Tracker
- **3306** - MySQL Database

## 🚀 Como Executar o Projeto

Siga **rigorosamente** esta ordem de execução:

### Passo 1: Iniciar o Banco de Dados com Docker

```bash
docker-compose up -d
```

**Verifique se o MySQL está rodando:**
```bash
docker ps
```
Você deve ver o container `monolithiccargotracker-mysql` rodando.

### Passo 2: Limpar o Projeto

```bash
mvn clean
```

Este comando remove arquivos compilados anteriores e diretórios de build.

### Passo 3: Processar Recursos

```bash
mvn process-resources
```

**Este passo é CRÍTICO** - Ele copia o driver JDBC do MySQL para o diretório de configuração do Liberty.

### Passo 4: Instalar Dependências e Build

```bash
mvn install
```

Este comando:
- Baixa todas as dependências
- Compila o código fonte
- Executa testes
- Empacota a aplicação como WAR

### Passo 5: Executar a Aplicação

```bash
mvn liberty:run -X
```

**Importante:**
- A flag `-X` habilita modo debug para troubleshooting
- A aplicação estará disponível em: `http://localhost:9080/monolithiccargotracker`
- O servidor Liberty iniciará e implantará automaticamente a aplicação

## 🌐 Endpoints da API

### Booking Context
- **POST** `/serviceapi/cargobooking` - Reservar uma nova carga
- **POST** `/serviceapi/cargorouting` - Roteirizar uma carga existente

### Routing Context  
- **GET** `/serviceapi/voyageRouting/optimalRoute` - Buscar rota ótima
  - Parâmetros: `origin`, `destination`, `deadline`

### Handling Context
- **POST** `/serviceapi/cargohandling` - Registrar atividade de manuseio

### Testes e Health Checks
- **GET** `/monolithiccargotracker/servlet` - Servlet de teste
- **Health Checks** automáticos do banco de dados na inicialização

## 🔄 Fluxo de Eventos e Comunicação

### Domain Events
1. **CargoBookedEvent** - Disparado quando uma carga é reservada
2. **CargoRoutedEvent** - Disparado quando uma carga é roteada
3. **CargoHandledEvent** - Disparado quando uma carga é manipulada

### Event Handlers
- `CargoRoutedEventHandler` - Atribui tracking number quando carga é roteada
- `CargoHandledEventHandler` - Adiciona eventos de tracking quando carga é manipulada

### Anti-Corruption Layer
- `ExternalCargoRoutingService` - Adapta modelos externos para o domínio interno

## 🗄️ Configuração do Banco de Dados

### Estrutura Automática
- As tabelas são criadas automaticamente via JPA schema generation
- Configuração no `persistence.xml`: `drop-and-create`

### Conexão
- **URL**: `jdbc:mysql://localhost:3306/monolithiccargotracker`
- **Usuário**: `monolithiccargotracker`
- **Senha**: `monolithiccargotracker`

## 🐛 Troubleshooting

### Problemas Comuns

1. **Driver MySQL não encontrado**
   - Solução: Execute `mvn process-resources` antes do `mvn install`

2. **Porta 3306 já em uso**
   - Solução: Pare outros serviços MySQL ou altere a porta no `docker-compose.yml`

3. **Erro de conexão com o banco**
   - Verifique: `docker ps` para ver se o MySQL está rodando
   - Verifique: As credenciais no `server.xml` e `docker-compose.yml`

4. **Aplicação não sobe**
   - Execute com `-X` para ver logs detalhados: `mvn liberty:run -X`

### Logs Importantes
- **Database Health Check** - Verifica conexão com banco na inicialização
- **Data Initializer** - Inicializa dados básicos do sistema
- **Entity Manager** - Logs de persistência JPA

## 📚 Conceitos DDD Implementados

### ✅ Aggregates
- `Cargo` (Booking Context)
- `Voyage` (Routing Context) 
- `HandlingActivity` (Handling Context)
- `TrackingActivity` (Tracking Context)

### ✅ Value Objects
- `BookingId`, `RouteSpecification`, `CargoItinerary`
- `Location`, `VoyageNumber`, `TrackingEvent`

### ✅ Domain Events
- Sistema completo de eventos entre bounded contexts
- Handlers assíncronos para processamento de eventos

### ✅ Repositories
- `CargoRepository`, `VoyageRepository`
- `HandlingActivityRepository`, `TrackingRepository`

### ✅ Services
- Application Services para comandos e queries
- Domain Services para lógica de domínio complexa

## 🔧 Desenvolvimento

### Adicionando Novos Recursos
1. Defina os aggregates e value objects no domínio
2. Implemente commands e events
3. Crie application services
4. Expõe endpoints REST
5. Implemente event handlers quando necessário

### Padrões de Código
- Usar nomenclatura ubíqua do domínio
- Separar comandos e queries (CQRS)
- Implementar validações no domínio
- Usar exceptions específicas do domínio

## 📄 Licença

Este projeto é para fins educacionais e de demonstração de conceitos DDD.

---

**🚀 Desenvolvido com Domain-Driven Design e Arquitetura Limpa**