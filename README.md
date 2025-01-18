# prueba-tecnica-java 

La prueba tecnica fue desarrollada y optimizada para desplegar todos los servicios necesarios en contenedores. 

## Levantar los servicios
Tendremos que estar en la raiz del proyecto. Luego, ejecutaremos el archivo `services.yml` con el siguiente comando:

   ```bash
   docker-compose -f services.yml up --build
   ```
La primera vez, tomará un momento mientras se decargan las imagenes para los servicios externos:
- Oracle DB
- Zookeeper
- Kafka

A la par, se construirán los artefactos JAR de los dos microservicios:
- client-service
- transaction-service

Con esto, se generarán las imagenes y posteriores contenedores para cada microservicio.

## Script para la Base de Datos
El script `BaseDatos.sql` se encuentra en los dos microservicios. Comparten la misma información de creación de tablas, columnas y relaciones; así que solo hace falta ejecutar uno.

La ubicación del script en cualquiera de los dos microservicios es:

   ```bash
    <servicio>/src/main/resources/templates/BaseDatos.sql
   ```

## Pruebas mediante Postman
Una vez se ha levantado correctamente el ambiente por medio del archivo compose `services.yml` y se ha ejecutado el script `BaseDatos.sql` para la creacion de tablas y dependencias (con el gestor o IDE de su preferencia), estará listo para ejecutar las pruebas.

En la raiz del proyecto se encuentra el JSON `ejercicio-tecnico-java.postman_collection.json` que deberá ser importado en la herramienta `POSTMAN`. Este JSON contiene de manera ordenada, todos los endpoints necesarios para probar el funcionamiento de ambos microservicios.

---

#### Desarrollado por: `Cristopher Garcia`.