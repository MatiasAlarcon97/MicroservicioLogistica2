Instrucciones de uso Microservicio Logistica

Tenemos tres controller: Envio, Pedido y Proveedor
(El cuarto controller es el que recibe la venta des microservicio ventas)
En los tres controller podemos realizar los metodos: GET, POST Y DELETE

Microservicio Logistica
Controller Pedido
GET: localhost:8080/pedidos
POST: localhost:8080/pedidos
DELETE: localhost:8080/pedidos/"ID PEDIDO"

Controller Envio
GET: localhost:8080/envios
POST: localhost:8080/envios
DELETE: localhost:8080/envios/"ID ENVIO"

Controller Proveedor
GET: localhost:8080/proveedores
POST: localhost:8080/proveedores
DELETE: localhost:8080/proveedores/"ID PROVEEDOR"

Se debe usar Usar JDK 21
