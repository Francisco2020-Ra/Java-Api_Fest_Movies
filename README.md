
# Informatorio_Fest_Movies

REST API to be able to register the information of the participants and works, of the festival of a film festival


## Objective

- Create a Rest API that can perform the Create, Read, Update, and Delete Resources (Entities) operations of our application.

- Put into practice and understand the concepts of DB, Spring Data (JPA), Rest (only basic aspects of routes, params and body to be able to interact with our Rest API).

- Apply cardinality concepts with @OneToOne, @OneToMany, @ManyToMany annotations.

- Carry out tests and checks using the POSTMAN and SQL Client tools (DBeaver, Workbench, others).


## Specs

- Of the works/films/documentaries you wish to register: Name (or Title rather), description, duration (in minutes), director, category (Documentary, Animation, etc), Registration Date (or Registration) and actors.

- Of the directors: Name, Surname, Date of Birth, DNI, Date of Registration (or Registration)

- Category: Name

- Of Actors: Name, Surname, Date of Birth, DNI.

- The works/films/documentaries only admit 1 director in the registration of the contest.

- The categories are defined by the contest. Works/films/documentaries that do not have an existing category cannot be added.

- Actors may have participated in more than 1 plays/films/documentaries entered in the current contest.
## DER

![DER](https://raw.githubusercontent.com/Francisco2020-Ra/Informatorio_Fest_Movies/main/src/main/resources/static/image/DER.webp)

