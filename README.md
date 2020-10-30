Beertech Banco application
This application has 3 Sprig boot projects: Banco, Consumer and Products

To run, it is necessary to have:
- Docker;
- Maven;
- Java 8

Steps to execute:
- Clone project to your local machine;
- Execute docker-compose up in project main folder;
- Enter in each projetc folder and execute mvn spring-boot:run

Swagger URLs:
http://localhost:8080/swagger-ui.html
http://localhost:8082/swagger-ui.html

To login as Admin:
login: admin@email.com
password: grupocolorado

To login as User:
login: user@emai.com
password: user

To import the Postman tests, it is necessary to have it installed on the machine.
Then go to File/Import.
The json file that is in the Postman folder.

It is necessary to import the Postman BDD and to do this, you must request the "Setup Postman/Install Postman BDD"
