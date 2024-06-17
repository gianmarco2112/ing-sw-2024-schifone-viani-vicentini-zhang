# Codex Naturalis - Software Engineering Project
Progetto Ingegneria del Software 2024

<img src="src/main/resources/Codex_scatola.png" width=192px height=192px align="right"  alt="Codex Naturalis Logo"/>

Codex Naturalis is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"**
held at Politecnico di Milano (2023/2024). <br />

**Professor**: [Gianpaolo Cugola](https://cugola.faculty.polimi.it/images/paolino.jpg)

**Group**: GC-29

**The team**:
- Schifone Gianmarco
- Viani Alessia Beatrice
- Vicentini Leonardo
- Zhang Andrea

## Project info
The project consists of a Java version of the board game *Codex Naturalis*.

The project includes:
- High level uml diagram;
- Complete uml diagram;
- uml documentation of the comunication protocol between client and server
- Peer Reviews of model and network of group GC-39;
- Source code of the game implementation;
- Source code of junit tests;
- Compiled JAR file;

## Testing

Almost all `model` and `controller` classes have a class and method coverage of 100%.

**Coverage**: code lines covered.

| Package   | Class              | Coverage       |
|-----------|--------------------|----------------|
| Model     | Entire Package     | ---% (---/---) |
| Controller | ServerImpl         | ---% (---/---) |
| Controller | GameControllerImpl | ---% (---/---) | 

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

1. **Download the folder containing the JAR files.**



### Playing locally

To play locally, follow these steps:

#### Step 1: Start the Server

Start the server on your PC using the following command:

```sh
java -jar server.jar
```

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

#### Step 2: Start the Server

On one PC, start the server using the following command:

```sh
java -jar server.jar
```

#### Step 3: Find the Server's IP Address

You will need the server's IP address. Use the appropriate command for your operating system:

- **Windows:**

  ```sh
  ipconfig
  ```

- **Linux:**

  ```sh
  ifconfig
  ```

- **macOS:**

  ```sh
  ifconfig
  ```

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

    Replace `<server-ip-address>` with the IP address obtained in Step 3.

##### On Other Operating Systems

- **RMI Client:**

  ```sh
  java -jar client.jar RMI <server-ip-address>
  ```

- **Socket Client:**

  ```sh
  java -jar client.jar socket <server-ip-address>
  ```

Replace `<server-ip-address>` with the IP address obtained in Step 3.
