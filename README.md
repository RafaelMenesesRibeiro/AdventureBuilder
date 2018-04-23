# Adventure Builder 

[![Build Status](https://travis-ci.com/tecnico-softeng/es18tg_17-project.svg?token=tzyzgKHYbK1mnRs5VZbp&branch=develop)](https://travis-ci.com/tecnico-softeng/es18tg_17-project) [![codecov](https://codecov.io/gh/tecnico-softeng/es18tg_17-project/branch/develop/graph/badge.svg?token=iC1C1G5pBo)](https://codecov.io/gh/tecnico-softeng/es18tg_17-project)

To run tests execute: mvn clean install.
To see the coverage reports, go to module_name/target/site/jacoco/index.html.
To run webservices: mvn clean spring-boot:run on module and access on http://localhost:8080 for example.

|   Number   |          Name           |                 Email                    |   Name GitHUb   | Grupo |
| ---------- | ----------------------- | -----------------------------------------| ----------------| ----- |
| 84758      | Rafael Ribeiro          | rafaelmmribeiro@gmail.com                | RafaelRibeiro97 |   17  |
| 85069      | Francisco Barros        | francisco.teixeira.de.barros@outlook.com | FranciscoKloganB|   17  |
| 83405      | João Neves              | jmcpneves@gmail.com                      | JoaoMiguelNeves |   17  |
| 84710      | Diogo Vilela            | diogofsvilela@gmail.com                  | DiogoFSVilela   |   17  |
| 84711      | Diogo Redin             | diogo.redin@tecnico.ulisboa.pt           | diogoredin      |   17  |
| 77921      | Mafalda Gaspar          | mariamafaldag@gmail.com                  | mafsgasp        |   17  |
| 83391      | Andreia Valente         | andreia.valente@tecnico.ulisboa.pt       | AndreiaValente  |   17  |


## Tasks - Fourth Delivery

|   Number   |          Name           |   GitHub Name   |   Module   |     Task    |
| ---------- | ----------------------- | ----------------| -----------| ----------- |
| 84758      | Rafael Ribeiro          | RafaelRibeiro97 |   Hotel    |Tester / Code|
| 83391      | Andreia Valente         | AndreiaValente  |   Bank     |Tester / Code|
| 85069      | Francisco Barros        | FranciscoKloganB|  Activity  |Tester / Code|
| 84710      | Diogo Vilela            | DiogoFSVilela   |    Car     |Tester / Code|
| 83405      | João Neves              | JoaoMiguelNeves |    Tax     |Tester / Code|
| 84711      | Diogo Redin             | diogoredin      |   Broker   |Tester / Code|
| 77921      | Mafalda Gaspar          | mafsgasp        |    Tax     |Tester / Code|

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

And create the 7 databases for the project as specified in
the `resources/fenix-framework.properties`.
