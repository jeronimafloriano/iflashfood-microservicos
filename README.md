# iflashfood
Criação de um microsserviço em Java e Spring de pedidos e pagamentos do zero, criando controlador, modelos, dto (data transfer object), repository etc, conectando a aplicação a um banco de dados MySQL, utilizando uma arquitetura com service discovery, centralizando requisições através de um API Gateway, fazendo balanceamento de carga e implementando comunicação síncrona usando ferramentas do Spring Cloud, além de projetar conceitos de circuit breaker e fallback para prevenir falhas ou inoperabilidade em algum dos microsserviços.

## Recursos utilizados:
* Uso de migrations com SQL e gerancimento com FlyWay
* Service Discovery e Service Registry com Eureka 
* Gateway e Balanceamento de carga
* Comunicação Síncrona com Spring Cloud Feign
* Tratamento de falhas em comunicação síncrona.
* CircuitBreaker com Resilience4j
