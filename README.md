Welcome to our Island Trader project for SENG201!!

To build and run the Island Trader game from the command line:

1. Ensure you are in the root project directory. This directory contains this README, the src and doc directories.
2. Run the following command to compile the Java source code and place the resulting compiled classes into the
   bin directory:

    javac -d bin -cp src src/main/Main.java

3. To start RocketManager with a graphical user interface run:

     java -cp bin main.Main

   To start RocketManager with a command line interface run:

     java -cp bin main.Main cmd

Alternatively, to load the project into Eclipse:
1. Create a new Eclipse Project
2. Right click on the project, and select Import -> General -> File System. It will make a copy of your files. 
You can also drag and drop files from the Finder into the src folder. 