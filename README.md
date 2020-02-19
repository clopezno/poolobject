## Medir para caracterizar entidades de productos y procesos software
[![Universidad de Burgos](https://www.ubu.es/sites/all/themes/ubu_theme/images/UBUEscudo-1910.png "Universidad de Burgos")](http://www.ubu.es "Universidad de Burgos")

**Práctica 01 - Desarrollo avanzado de sistemas software**
Grado en Ingeniería informática - Universidad de burgos

Daniel Setó Rey

------------
[TOCM]

[TOC]

### Enunciado de la práctica
En la práctica se va simular un pequeño desarrollo de un producto software para realizar mediciones sobre él.
El objetivo es establecer un caso de estudio que sirva para caracterizar y evaluar tanto el producto desarrollado como el proceso seguido.
#### Descripción del producto
Dado un código de ejemplo del patrón diseño creacional Pool Object, se debe crear una batería de pruebas tal que las coberturas de sus clases sean del 100%. El código de las clases se puede obtener en el repositorio
https://github.com/clopezno/poolobject. 
La batería de pruebas JUnit debe estar contenida en la clase `ubu.gii.dass.test.c01.ReusablePoolTest.java`
#### Descripción del proceso
El proceso de desarrollo de la batería de pruebas se va a gestionar utilizando el control de versiones del sistema Git proporcionado por el repositorio de proyectos GitHub (https://github.com).
Los pasos para gestionar el procesos son los siguientes:
1. Cada miembro del equipo tiene que estar registrado en GitHub, Travis CI y Codecov.io.
2. Uno de los miembros tiene que realizar un fork del repositorio donde se encuentra el código que se quiere probar https://github.com/clopezno/poolobject. El nuevo repositorio tiene que ser público.
3. Invitar al resto de miembros del equipo para que puedan participar en el desarrollo de las pruebas.
4. Vincular el proyecto con Travis CI y Codecov.io.
5. Cada nuevo test realizado ejecutar un commit/push al repositorio del grupo. El texto del commit tiene que describir el caso de prueba añadido.
6. Verificar el resultado de las pruebas en el pipeline de integración continua y cómo la calidad del producto va mejorando con las sucesivas integraciones.

------------

### Ejecución y resultados

#### Setup y configuración
Se ha hecho un fork del repositorio https://github.com/clopezno/poolobject y se han enlazado las correspondientes cuentas de travis CI y codeconv.

Inicialmente se detectó un problema que impedía la correcta finalización del build en Travis con la configuración del repositorio. Tras realizar diferentes comprobaciones y estudiar posibles soluciones, el asunto se resuelve mediante una modificación  en travis.yml. por parte del profesor de la asignatura (https://github.com/clopezno/poolobject/commit/704d82252d75e8799a5d724d1ca119199dc2cbaa)

Realizamos finalmente un nuevo fork, correspondiente al presente proyecto (https://github.com/dsr0018/poolobject) y solicitamos un build desde Travis, comprobando que el setup inicial actúa como se espera.

En nuestro entorno de desarrollo, basado en Eclipse, clonamos el repositorio git y creamos un proyecto de Java para el desarrollo y ejecución local de test.

#### Casos de prueba y desarrollo de test
Con el objetivo de lograr una cobertura del 100% de `ubu.gii.dass.main.c01.ReusablePool.java` se diseñan varios casos de prueba basados en el *stub* proporcionado con la práctica. Algunos test se dividen en varios casos de prueba independientes. Los test se ejecutan con jUnit 4.
En la siguiente tabla resumimos los casos de prueba diseñados y su implementación:

|Método|Caso|Descripción|Test|Resultado esperado|
|---------|-----|-------------|----|-----------------------|
|getInstance()|testGetInstance|Comprueba el funcionamiento correcto del patrón Singleton|Petición consecutiva de dos instancias de ReusablePool|Se obtienen dos referencias no nulas al mismo objeto (instancia Singleton)|
|acquireReusable()|testAcquireReusable01|Test de adquisición de dos objetos reusables diferentes|Se solicitan consecutivamente dos objetos Reusable al pool ReusablePool|Se obtienen dos instancias no nulas diferentes de Reusable|
|acquireReusable()|testAcquireReusable02|Comprueba la excepción NotFreeInstanceException si se solicitan más de dos objetos reusable|Se solicitan consecutivamente tres objetos reusables mediante acquireReusable()|Se lanza la excepción NotFreeInstanceException|
|releaseReusable()|testReleaseReusable01|Comprueba que se liberan correctamente los objetos del pool y quedan a disposición de los clientes.|Se solicitan al pool dos objetos Reusable, se liberan y a continuación vuelven a solicitarse otros dos objetos Reusable.|En la segunda fase se obtienen los objetos Reusable liberados en la primera.|
|releaseReusable()|testReleaseReusable02|Comprueba que no se permite liberar un objeto ya liberado.|Se solicitan consecutivamente dos veces la liberación de un objeto Reusable.|Se lanza la excepción DuplicatedInstanceException|
#### Cuestiones planteadas
**¿Se ha realizado trabajo en equipo?**
No, dado que la práctica se ha realizado de modo individual. No obstante, se debe indicar que el setup empleado y el proceso de trabajo sería muy similar, dado que en el contexto de esta práctica no son de esperar problemas de integración continua derivados de la colaboración de varios participantes.

**¿Tiene calidad el conjunto de pruebas disponibles?**
Se considera que el conjunto de pruebas diseñado tiene un nivel elevado de calidad, debido a los siguientes motivos:
1. El código a probar consiste en una aplicación sencilla del patrón Singleton, cuyos requisitos funcionales son simples y ha permitido diseñar casos de prueba basados directamente en la utilización típica (trivial) de los métodos de la clase. En un proyecto más complejo de software puede ser mucho más complicado conseguir una buena cobertura funcional.
2. Mediante las herramientas automáticas podemos comprobar que mediante el conjunto de  test empleado se consigue una cobertura del 100% de las clases que implementan Pool Object. Según codecov: https://codecov.io/gh/dsr0018/poolobject/tree/master/src/main/ubu/gii/dass/c01


**¿Cuál es el esfuerzo invertido en realizar la actividad?**
Se estima un esfuerzo individual de aproximadamente 8 horas para la realización de la práctica, de acuerdo al desglose presentado en la siguiente tabla:

|Tarea|Horas-persona|
|------|-----------------|
|Setup y configuración inicial</br>Incluye solución de problemas con el repositorio original|4|
|Diseño y desarrollo de test|2|
|Análisis de los resultados y documentación|2|

**¿Cuál es el número de fallos encontrados en el código?**

No se han encontrado fallos en el código cubierto por el conjunto de casos diseñado, como puede comprobarse en el log de Travis correspondiente al último build del proyecto:
```shell
            61 [junit] Running ubu.gii.dass.test.c01.ReusablePoolTest
            62 [junit] Testsuite: ubu.gii.dass.test.c01.ReusablePoolTest
            63 [junit] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 sec
            64 [junit] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 sec
            65 [junit]
```
https://travis-ci.org/dsr0018/poolobject

#### Conclusiones
La práctica ha constituido una buena primera aproximación al empleo práctico de un pipeline de integración continua en Java, en relación a los conceptos teóricos de medición y calidad en el desarrollo de software. 
Destacar que el trabajo aparentemente inutil realizado inicialmente, derivado de la incorrecta configuración de Travis, ha servido al autor para estudiar bastantes posibilidades que desconocía de configuración de Ant.
Además, la práctica ha provocado una interesante reflexión acerca de la mejor manera de realizar un conjunto de test unitarios sobre instancias Singleton (un aspecto este que en opinión del autor no está resuelto de manera completamente satisfactoria)
