# Ice And Fire
This project allows you to find a *House* for each *Character* in the **_A Song of Ice and Fire_** series of books.\
The project uses **An API of Ice And Fire** to provide data about the world of Westerose.

## Getting Started

### Prerequisites
* JDK 8
* Gradle 5.2.1 or newer

### Install and run the project
Download/clone the project using URL
```
https://github.com/kazimirovandrew/gl-basecamp-spring-framework-final-project.git
```
To compile the project
```
gradlew clean build
```
To run the project
```
gradlew bootRun
```
Now the application is up and running with Tomcat on localhost:8080

## Running the tests
To execute tests 
```
gradlew test
```

## Endpoints

### Create new character
Use the URL below to create a new character with a specific **_name_**.
```
http://localhost:8080/ice-and-fire/character?name={name} [POST]
```
After a successful transaction, you will receive a character's id.
```
{
    "id": "UUID"
}
```

### Get character info
Use the URL below to get info about the character with a specific **_id_**.
```
http://localhost:8080/ice-and-fire/characters/{id} [GET]
```
After a successful transaction, you will receive a message about the character and his houses. 
```
{
    "message": "This character from those houses"
}
```

### Get characters
Use the URL below to get all created characters.
```
http://localhost:8080/ice-and-fire/characters [GET]
```
After a successful transaction, you will receive a pair: character's name and his id. 
```
{
    "nameToId": {
        "name": "UUID"
    }
}
```

## Authors
* **Andrew Kazimirov** - [github](https://github.com/kazimirovandrew)
