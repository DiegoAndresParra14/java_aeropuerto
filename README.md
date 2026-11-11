# Sistema de aeropuerto · Java

Aplicación de consola para gestionar aeropuertos, pasajeros, vuelos y reservas. Proyecto de práctica de **programación orientada a objetos y lógica de negocio con Java 17**.

## Funcionalidades

- Registro de aeropuertos, pasajeros y vuelos.
- Listado y búsqueda de vuelos por destino.
- Reservas y consulta de pasajeros por vuelo.
- Cambio de estado de vuelos.
- Consulta de estadísticas y rutas.

## Organización

| Carpeta | Responsabilidad |
| --- | --- |
| `Main/src/main/java/Models` | Entidades del dominio: aeropuerto, vuelo, persona, pasajero y piloto. |
| `Main/src/main/java/Services` | Operaciones del sistema de vuelos. |
| `Main/src/main/java/repositories` | Abstracción de repositorio. |
| `Main/src/main/java/exceptions` | Excepción de vuelo lleno. |
| `Main/src/main/java/com/mycompany/main` | Menú interactivo y punto de entrada. |

## Ejecutar

Requisitos: **JDK 17** y **Maven**.

Desde la raíz del repositorio:

```sh
cd Main
mvn compile
java -cp target/classes com.mycompany.main.Main
```

También puedes abrir `Main/pom.xml` en un IDE compatible con Maven y ejecutar la clase `com.mycompany.main.Main`.

## Recorrido sugerido

1. Registra dos aeropuertos.
2. Registra un pasajero.
3. Crea un vuelo usando los códigos de los aeropuertos.
4. Reserva el vuelo para ese pasajero.
5. Consulta los pasajeros y cambia el estado del vuelo.

## Alcance

Proyecto educativo con interfaz de consola. No expone una API HTTP. La estructura permite explorar modelado del dominio, servicios y manejo de excepciones antes de trasladar esos conceptos a un backend web.

[Volver al perfil](https://github.com/DiegoAndresParra14)
