# User Management REST API

Un'API REST per la gestione degli utenti, che include funzionalità di creazione, aggiornamento, eliminazione e recupero degli utenti, con autenticazione basata su token e gestione dei ruoli applicativi.

---

## Indice

- [Come iniziare](#come-iniziare)
    - [Avvio applicazione](#avvio-applicazione)
    - [Autenticazione](#Autenticazione)
- [Endpoints](#endpoints)
- [Struttura progetto](#struttura-progetto)

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

---

### Struttura progetto

La struttura del progetto segue un approccio **package by feature**, organizzando il codice in base alla funzionalità. Di seguito la disposizione dei package:

- **`presentation`**: Contiene tutte le classi che si occupano di presentare la risorsa, ovvero:
   - I **controller** per gestire le richieste HTTP.
   - I **DTO** (Data Transfer Object) per il trasferimento dei dati.

- **`business`**: Racchiude tutte le classi contenenti la **logica di business**, ovvero la logica specifica applicata ai dati o ai processi.

- **`model`**: Include tutte le classi legate alla memorizzazione delle entità nel database, tra cui:
   - Le classi **repository** per l'interazione con il database.
   - Le **entità** che rappresentano i record del database.

- **`aspect`**: Contiene tutti gli **aspetti** implementati tramite programmazione orientata agli aspetti (AOP - Aspect-Oriented Programming).

- **`eventing`**: Raccoglie tutte le classi utilizzate per gestire gli **eventi**, come gli eventi stessi e i listener.

- **`config`**: Include tutte le classi dedicate all'**inizializzazione** o alla configurazione del progetto.

- **`auth`**: Contiene tutte le classi relative all'**autenticazione**