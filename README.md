# User Management REST API

Un'API REST per la gestione degli utenti, che include funzionalità di creazione, aggiornamento, eliminazione e recupero degli utenti, con autenticazione basata su token e gestione dei ruoli applicativi.

---

## Indice

- [Come iniziare](#come-iniziare)
    - [Avvio applicazione](#avvio-applicazione)
    - [Autenticazione](#Autenticazione)
- [Endpoints](#endpoints)

---

## Come iniziare

Per avviare il progetto, segui questi semplici passaggi:

### Avvio applicazione

1. **Avvia il database con Docker**  
   Il file `docker-compose.yml` si trova in `src/main/resources/docker`. Per avviare il database, esegui:
   ```bash
   docker-compose -f src/main/resources/docker/docker-compose.yml up
   ```

2. **Avvia l'applicazione Spring Boot**  
   Una volta che il database è in esecuzione, avvia il progetto Spring Boot:
   ```bash
   .\mvnw spring-boot:run
   ```

3. **Inizializzazione dei dati**  
   All'avvio, il file `DatabaseInitializer` popola automaticamente il database con i seguenti ruoli necessari:
    - **OWNER**
    - **OPERATOR**
    - **MAINTAINER**
    - **DEVELOPER**
    - **REPORTER**

---

## Autenticazione

Per autenticarsi, è necessario utilizzare l'endpoint **POST /auth/login**, passando il seguente payload JSON con uno username e una password validi:
```json
{
  "username": "user",
  "password": "password"
}
```

L'endpoint restituisce un token che può essere utilizzato per accedere alle risorse protette:
```json
{
  "token": "token_per_autenticarsi"
}
```

**Header di Autenticazione:**  
Per ogni chiamata ai servizi REST protetti, è necessario includere il token nell'header:
```bash
Authentication: Bearer [token]
```

---

## Endpoints

È possibile accedere alle risorse relative agli utenti tramite `/private/users`. Di seguito i vari servizi REST disponibili:

| Metodo | Endpoint              | Descrizione                              |
|--------|-----------------------|------------------------------------------|
| GET    | `private/users`       | Restituisce la lista di tutti gli utenti |
| GET    | `private/users/:id`   | Restituisce i dettagli di un utente      |
| POST   | `private/users`       | Crea un nuovo utente                     |
| PUT    | `private/users/:id`   | Aggiorna i dati di un utente             |
| DELETE | `private/users/:id`   | Cancella un utente                       |

### Esempio di payload per la creazione di un utente
```json
{
  "username": "mrossi",
  "email": "mario.rossi@example.com",
  "name": "Mario",
  "surname": "Rossi",
  "taxCode": "ABC123456789",
  "roles": ["OWNER", "DEVELOPER"]
}
```