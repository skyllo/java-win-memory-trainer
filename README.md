Java Memory Trainer Library (Windows)
=====================================
A simple Java library used to Read and Write Memory from a Windows process, written using the Java Native Access and Google Guava api.


Usage
-----
Basic example:
```java
// Relative memory address inside process
final int MEMORY_ADDRESS = 0x00010ABF;

// Create JTrainer instance for Minesweeper process (window name)
JTrainer jTrainer = new JTrainer(null, "Minesweeper");

// Write 4 bytes as hex 0x90 (nop) to process
jTrainer.writeProcessMemory(MEMORY_ADDRESS, new int[] { 0x90, 0x90, 0x90, 0x90 });

// Read 4 bytes from process and print out the result
byte[] bytes = jTrainer.readProcessMemory(MEMORY_ADDRESS, 4);
System.out.println(Arrays.toString(MemoryUtils.convertByteArrayToHex(bytes));
```

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Dependencies
------------
* Google Guava (13.0.1)

* Java JNA Platform (3.4.0)

* Java JNA (3.3.0)