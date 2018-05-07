# Adventure Builder 

[![Build Status](https://travis-ci.com/tecnico-softeng/es18tg_17-project.svg?token=tzyzgKHYbK1mnRs5VZbp&branch=develop)](https://travis-ci.com/tecnico-softeng/es18tg_17-project) [![codecov](https://codecov.io/gh/tecnico-softeng/es18tg_17-project/branch/develop/graph/badge.svg?token=iC1C1G5pBo)](https://codecov.io/gh/tecnico-softeng/es18tg_17-project)

To run tests execute: mvn clean install.
To see the coverage reports, go to module_name/target/site/jacoco/index.html.
To run webservices: mvn clean spring-boot:run on module and access on http://localhost:8080/banks for example.

|   Number   |          Name           |                 Email                    |   Name GitHUb   | Grupo |
| ---------- | ----------------------- | -----------------------------------------| ----------------| ----- |
| 84758      | Rafael Ribeiro          | rafaelmmribeiro@gmail.com                | RafaelRibeiro97 |   17  |
| 85069      | Francisco Barros        | francisco.teixeira.de.barros@outlook.com | FranciscoKloganB|   17  |
| 83405      | João Neves              | jmcpneves@gmail.com                      | JoaoMiguelNeves |   17  |
| 84710      | Diogo Vilela            | diogofsvilela@gmail.com                  | DiogoFSVilela   |   17  |
| 84711      | Diogo Redin             | diogo.redin@tecnico.ulisboa.pt           | diogoredin      |   17  |
| 77921      | Mafalda Gaspar          | mariamafaldag@gmail.com                  | mafsgasp        |   17  |
| 83391      | Andreia Valente         | andreia.valente@tecnico.ulisboa.pt       | AndreiaValente  |   17  |


## Tasks - Fifth Delivery

|   Number   |          Name           |   GitHub Name   |           Module        |     Task    |
| ---------- | ----------------------- | ----------------| ----------------------- | ----------- |
| 84758      | Rafael Ribeiro          | RafaelRibeiro97 |                         |Tester / Code|
| 83391      | Andreia Valente         | AndreiaValente  |                         |Tester / Code|
| 85069      | Francisco Barros        | FranciscoKloganB|                         |Tester / Code|
| 84710      | Diogo Vilela            | DiogoFSVilela   |                         |Tester / Code|
| 83405      | João Neves              | JoaoMiguelNeves |                         |Tester / Code|
| 84711      | Diogo Redin             | diogoredin      |                         |Tester / Code|
| 77921      | Mafalda Gaspar          | mafsgasp        |                         |Tester / Code|

- **Group 1:**
  - Mafalda Gaspar
  - Andreia Valente
  - Rafael Ribeiro
- **Group 2:**
  - Diogo Redin
  - Francisco Barros
- **Group 3:**
  - João Neves
  - Diogo Vilela

### Infrastructure

This project includes the persistent layer, as offered by the FénixFramework.
This part of the project requires to create databases in mysql as defined in `resources/fenix-framework.properties` of each module.

See the lab about the FénixFramework for further details.

#### Docker (Alternative to installing Mysql in your machine)

To use a containerized version of mysql, follow these stesp:

```
docker-compose -f local.dev.yml up -d
docker exec -it mysql sh
```

Once logged into the container, enter the mysql interactive console

```
mysql --password
```

And create the 6 databases for the project as specified in
the `resources/fenix-framework.properties`.

To launch a server execute in the module's top directory: mvn clean spring-boot:run

To launch all servers execute in bin directory: startservers

To stop all servers execute: bin/shutdownservers

To run jmeter (nogui) execute in project's top directory: mvn -Pjmeter verify. Results are in target/jmeter/results/, open the .jtl file in jmeter, by associating the appropriate listeners to WorkBench and opening the results file in listener context

