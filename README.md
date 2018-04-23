# Adventure Builder [![Build Status](https://travis-ci.com/tecnico-softeng/es18LL_NN-project.svg?token=COPY_FROM_TRAVIS&branch=develop)](https://travis-ci.com/tecnico-softeng/es18LL_NN-project) [![codecov](https://codecov.io/gh/tecnico-softeng/es18LL_NN-project/branch/master/graph/badge.svg?token=COPY_FROM_CODECOV)](https://codecov.io/gh/tecnico-softeng/es18LL_NN-project)

To run tests execute: mvn clean install

To see the coverage reports, go to module_name/target/site/jacoco/index.html.


|   Number   |          Name           |                 Email                    |   Name GitHUb   | Grupo |
| ---------- | ----------------------- | -----------------------------------------| ----------------| ----- |
| 84758      | Rafael Ribeiro          | rafaelmmribeiro@gmail.com                | RafaelRibeiro97 |   17  |
| 85069      | Francisco Barros        | francisco.teixeira.de.barros@outlook.com | FranciscoKloganB|   17  |
| 83405      | João Neves              | jmcpneves@gmail.com                      | JoaoMiguelNeves |   17  |
| 84710      | Diogo Vilela            | diogofsvilela@gmail.com                  | DiogoFSVilela   |   17  |
| 84711      | Diogo Redin             | diogo.redin@tecnico.ulisboa.pt           | diogoredin      |   17  |
| 77921      | Mafalda Gaspar          | mariamafaldag@gmail.com                  | mafsgasp        |   17  |
| 83391      | Andreia Valente         | andreia.valente@tecnico.ulisboa.pt       | AndreiaValente  |   17  |


## Tasks - Third Delivery  

|   Number   |          Name           |   GitHub Name   |           Module        |     Task    |
| ---------- | ----------------------- | ----------------| ----------------------- | ----------- |
| 84758      | Rafael Ribeiro          | RafaelRibeiro97 | Tax / Activity / Broker |Tester / Code|
| 83391      | Andreia Valente         | AndreiaValente  | Tax / Activity / Broker |Tester / Code|
| 85069      | Francisco Barros        | FranciscoKloganB|       Car / Hotel       |Tester / Code|
| 84710      | Diogo Vilela            | DiogoFSVilela   |       Car / Hotel       |Tester / Code|
| 83405      | João Neves              | JoaoMiguelNeves |       Car / Hotel       |Tester / Code|
| 84711      | Diogo Redin             | diogoredin      | Tax / Activity / Broker |Tester / Code|
| 77921      | Mafalda Gaspar          | mafsgasp        | Tax / Activity / Broker |Tester / Code|

- **Group 1:**
- **Group 2:**

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
