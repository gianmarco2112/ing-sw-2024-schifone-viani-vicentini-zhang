# IngSW2024
Progetto Ingegneria del Software 2024

---

## Requirements met
We have `implemented`, in addiction to the `Game Specific` and `Game Agnostic` requirements,  the following requirements:
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
   - **RMI client:**
     ```sh
     java -jar RMI localhost
     ```
   - **Socket client:**
     ```sh
     java -jar socket localhost
     ```

##### On Other Operating Systems

1. **RMI Client:**
   ```sh
   java -jar RMI localhost
   ```
2. **Socket Client:**
   ```sh
   java -jar socket localhost
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

   - **RMI client:**
     ```sh
     java -jar RMI <server-ip-address>
     ```
   - **Socket client:**
     ```sh
     java -jar socket <server-ip-address>
     ```

    Replace `<server-ip-address>` with the IP address obtained in Step 3.

##### On Other Operating Systems

- **RMI Client:**

  ```sh
  java -jar RMI <server-ip-address>
  ```

- **Socket Client:**

  ```sh
  java -jar socket <server-ip-address>
  ```

Replace `<server-ip-address>` with the IP address obtained in Step 3.
