# Trylma

## Run 

### Database
```sh
cd database
docker compose up -d
```

### Server

```sh
cd server
mvn clean install
mvn spring-boot:run
```

### Client

```sh
cd client
mvn clean install
mvn javafx:run
```