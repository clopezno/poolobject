poolobject
==========
Partimos de un codigo dado que crea instancias unicas (Singleton). 
Sobre ella se pretende realizar buenas practicas de desarrollo: testing, ci y metricas. 


Autores de clases:

- Carlos Lopez Nozal
- Jesus Alonso Abad

Autores de los test:
- Carlos Venero Ortega
- Iván Fernández Pardo
- Pablo Alonso Cameselle
- Mario Flores Cano

### BADGES 
Insignias de consecución de objetivos.
[![codecov](https://codecov.io/gh/cvo0004/poolobject/branch/master/graph/badge.svg?token=8GAMWS6JRQ)](https://codecov.io/gh/cvo0004/poolobject)

# **OBJETIVOS**
El objetivo de esta actividad es que nos familiricemos con un proyecto de desarrollo avanzado de software, utilizando herramientas colaborativas como Git, pruebas automatizadas con JUnit, y la integración continua con Maven. Además, se documenta el proceso en la wiki del repositorio y el archivo README.md.
- Comprender, aplicar y analizar técnicas de medición sobre entidades de productos software relacionados con conjuntos de pruebas de software e integración continua
- Comprender, aplicar y analizar medidas relacionadas sobre entidades de proceso y recursos de prueba del software e integración continua

# **ENUNCIADO**

En la práctica simula un pequeño desarrollo de un producto software para realizar mediciones sobre él. El objetivo es establecer un caso de estudio dummy que sirva para caracterizar y evaluar tanto el producto desarrollado como el proceso colaborativo seguido. El producto software es un código de test y el proceso es el análisis de la cobertura de pruebas en el tiempo.
### (a)Descripción del caso de estudio
Dado un código de ejemplo del patrón diseño creacional Pool Object, se debe crear una batería de pruebas tal que las coberturas de sus clases sean del 100%. El código de las clases se puede obtener en el repositorio https://github.com/clopezno/poolobject. La batería de pruebas JUnit debe estar contenida en la clase test.java.ubu.gii.dass.c01.ReuseblePoolTest.java.

# **PREGUNTAS**

### ¿Se ha realizado trabajo en equipo?
Sí. Para ello se han realizado las siguientes acciones colaborativas:
 - reuniones continuas utilizando TEAMS: Comparticion de conocimientos de familiarización con el entorno. 
 - Evaluación del texto en modo colaboratívo. 
 - Uso de la herramienta GITHUB para compartición de código. 
 - Realización de test de forma colaborativa, asignando el liderazgo de cada una a un componente.
Tiempo invertido en reuniones colaborativas 10 horas. 

Enlace a la actividad del repositorio: https://github.com/cvo0004/poolobject/pulse 
 - Autores: 1 del master, 4 de test. 
 - Número de commits realizados: 17 
(dentro de los datos abría que obviar los commits anteriores al Fork)
### ¿Tiene calidad el conjunto de pruebas disponibles?
El conjunto de pruebas cuenta con una buena cobertura de código, validando el comportamiento esperado de los métodos principales de la clase ReusablePool.
- Número total de tests: 3
- Pruebas exitosas: 3
- Pruebas fallidas: 0
- Cobertura de código: 100%
Informe de cobertura: https://app.codecov.io/gh/cvo0004/poolobject

### ¿Cuál es el esfuerzo invertido en realizar la actividad?
El esfuerzo invertido se ha medido a través de las horas dedicadas por cada miembro, reflejadas en los commits y las sesiones de trabajo.

- Horas totales invertidas: 20 horas
Enlace a las actividades de cada miembro: https://github.com/cvo0004/poolobject/graphs/contributors 

### ¿Cuál es el número de fallos encontrados en el código original?
- Número total de fallos detectados: Pensamos que hay un fallo en el método releaseReusable* porque no nos pasaba un test que hicimos, pero no estábamos seguros de que fuese fallo del método en sí y no de la implementación de nuestro test. Por lo que decidimos eliminar esa parte.

 
### ¿El proceso de integración continua realizado ha sido de calidad?
El proceso de integración continua (CI) se ha configurado mediante GitHub Actions con Codecov, ejecutando las pruebas automáticamente tras cada push. Codecov ha ido incrementando su porcentaje de coverage tras cada test implementado.

- Configuración de codecovs para la CI sincronizada con GitHub: https://github.com/cvo0004/poolobject/actions/workflows/Java17CImaven.yml 

# Conclusión
El equipo ha logrado implementar pruebas unitarias y establecer un proceso efectivo de integración continua. La colaboración ha sido clave para completar la actividad de forma organizada y eficiente.

Repositorio del proyecto: https://github.com/cvo0004/poolobject 
