# JavaPinger
A simple utility to send a number of pings to an IP address

## Requirements
- Java 21 or higher

## Usage

### Compilation
First, compile the Java source file:
```
cd src
javac JavaPinger.java
```

### Execution
Then run the application (make sure you're in the project root directory):
```
java -cp src JavaPinger <ip_address> <number_of_pings>
```

### Example
```
java -cp src JavaPinger 127.0.0.1 3
```

### Common Mistakes
Do not try to run the .class file directly:
```
# This will NOT work
java .\src\JavaPinger.class 127.0.0.1 10
```

## Features
- Cross-platform support (Windows, Linux, macOS)
- Simple command-line interface
- Displays ping results in real-time
