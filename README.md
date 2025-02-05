# Codex Naturalis - Software Engineering Project
Progetto Ingegneria del Software 2024

<img src="src/main/resources/Codex_scatola.png" width=192px height=192px align="right"  alt="Codex Naturalis Logo"/>

Codex Naturalis is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"**
held at Politecnico di Milano (2023/2024). <br />

**Professor**: [Gianpaolo Cugola](https://cugola.faculty.polimi.it/)

**Group**: GC-29

**The team**:
- Schifone Gianmarco
- Viani Alessia Beatrice
- Vicentini Leonardo
- Zhang Andrea

## Project info
The project consists of a Java version of the board game *Codex Naturalis*.

The project includes:
- High level UML Class Diagram;
- Complete UML Class Diagram;
- UML documentation of the communication protocol between client and server;
- Peer Reviews of model and network of group GC-39;
- Source code of the game implementation;
- Source code of junit tests;
- Compiled JAR file;

## Testing

Almost all `model` and `controller` classes have a class and method coverage of 100%.

**Coverage**: code lines covered.

| Package   | Class              | Coverage        |
|-----------|--------------------|-----------------|
| Model     | Entire Package     | 96% (1095/1133) |
| Controller | ServerImpl         | 86% (155/180)   |
| Controller | GameControllerImpl | 89% (235/263)   | 

---

## Requirements met
We have implemented, in addiction to the `game-specific` and `game-agnostic` requirements,  the following requirements:
- Complete rules
- TUI
- GUI
- RMI
- Socket
- 3 Advanced Features

### Advanced Features
| Feature                      | Implemented  |
|:-                            |:-            |
|Multiple games                |✔️            |
|Persistence                   |❌            |
|Resilience to disconnections  |✔️            |
|Chat                          |✔️            |









## Getting Started

Follow these instructions to install and run the project.

### Installation

1. **Download the [JAR files](https://github.com/leonardovicentini/ing-sw-2024-schifone-viani-vicentini-zhang/tree/master/deliverables/Jar%20files).**



### Playing locally

To play locally, follow these steps:

#### Step 1: Start the Server

Start the server on your PC using the following command:

```sh
java -jar server.jar
```

> In case of `Error: Invalid or corrupt jarfile server.jar` we suggest to clone the repository instead of downloading the .zip file.

#### Step 2: Start the Clients

##### On Windows

1. Set the code page to UTF-8:

    ```sh
    chcp 65001
    ```

2. Start the client:
   - **RMI Client:**
     ```sh
     java -jar client.jar RMI localhost
     ```
   - **Socket Client:**
     ```sh
     java -jar client.jar socket localhost
     ```

##### On Other Operating Systems

- **RMI Client:**
     ```sh
     java -jar client.jar RMI localhost
     ```

- **Socket Client:**
  ```sh
  java -jar client.jar socket localhost
  ```




### Playing in LAN

To play in a LAN environment, follow these steps:

#### Step 1: Connect to the Same LAN

Ensure all PCs are connected to the same local area network (LAN).


#### Step 2: Setup the Server Environment

You will need the server's IP address. Use the appropriate command for your operating system:

##### **Windows:**

Find the Server's IP Address

  ```sh
  ipconfig
  ```

##### **Linux:**

1. Find the Server's IP Address

    ```sh
    ifconfig
    ```

If your clients are using RMI, follow these additional steps:

  2. Open /etc/hosts.allow and at the end of the file add:

      ```sh
      ALL
      ```
	
  3. Open /etc/hosts and modify all `127.0.1.1` to `127.0.0.1`

##### **macOS:**

Find the Server's IP Address

  ```sh
  ifconfig
  ```

#### Step 3: Start the Server

```sh
java -jar server.jar -Djava.rmi.server.hostname=<server-ip-address>
```

Replace `<server-ip-address>` with the IP address obtained in Step 2.  
> **Do not** use `localhost` instead of `<server-ip-address>`

> In case of `Error: Invalid or corrupt jarfile server.jar` we suggest to clone the repository instead of downloading the .zip file.


#### Step 4: Start the Clients

##### On Windows

1. Set the code page to UTF-8:

    ```sh
    chcp 65001
    ```

2. Start the client:

   - **RMI Client:**
     ```sh
     java -jar client.jar RMI <server-ip-address>
     ```
   - **Socket Client:**
     ```sh
     java -jar client.jar socket <server-ip-address>
     ```

    Replace `<server-ip-address>` with the IP address obtained in Step 2.

##### On Other Operating Systems

- **RMI Client:**

  ```sh
  java -jar client.jar RMI <server-ip-address>
  ```

- **Socket Client:**

  ```sh
  java -jar client.jar socket <server-ip-address>
  ```

Replace `<server-ip-address>` with the IP address obtained in Step 2.

## Disclaimer
> NOTA: Codex Naturalis è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.