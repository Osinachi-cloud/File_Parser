## Simple File Parser
Simple File Parser is console based Application it takes directory as command line argument and does the following:

* The application will then monitor the folder for new Files.
* For each file arrives it reads File contents and print statistics on the console
* Then move the file to a new folder under the folder we are monitoring.

Note: Presently Application supports plain text files and print the number of words, number of dots, and most used word on console as statistics. It's flexible enough for extension in the future.

## Prerequisites:
* Java 17
* Maven
* Lombok
* Junit Jupiter
* Mockito

## How to Build the Project
* Using either Java IDEs (e.g. Intellij) build tools to compile and build project or
* Use below command build jar file

```bash
mvn clean install
```

## Usage
- [x] You can supply any valid directory in the project structure. e.g. 
    * "/" which is the root dir
    * "/src"
    * "/src/main"
    * "/src/main"
    * "/src/main/java" and so on.

- [x] For the purpose of this task I choose to create a fileStorage directory in the java folder, so that I can keep things clean, 
  thus I will be using /src/main/java/fileStorage for the directory
  
```bash
java -jar target/hicx-1.0-SNAPSHOT.jar "/src/main/java/fileStorage" or use your favourite IDE build tools
```

## How parser works.
When we run jar file. it invokes Starter class main method it takes directory path as argument and does the following:

- [x] Checks if the argument (directory) supplied is valid , if not exit and print an error message to the console.
- [x] Initializes the watcherService and get it to start monitoring newly created files in the directory. It does this by
  * Creating a concurrent thread pool to achieve parallel processing or asynchronous behavior by invoking 
     * fileWatchInProcess() → WatchCallable class → DirectoryObserver class
  * DirectoryObserver class is responsible for monitoring new files through the help of the watcher service. It is created using singleton pattern
  to ensure that only a single instance of DirectoryObserver is doing the monitoring at run time.
- [x] Created a bash script to create a text file in the directory
   ```
     bash filegenerator.sh or /bin/bash filegenerator.sh or use your IDE support
  
     it will ask user to input directory e.g. /src/main/java/fileStorage
    ```
- [x] Once a text file is created, the FileProcessorFactory class is invoked, which then creates an object responsible for either processing 
  a pdf, a txt, or csv file based on the type of file created in the directory. This is the Factory Method Pattern.

- [x] The file is then processed by
  * Reading the contents in the file
  * Moving the file to the processedDir folder
  * displaying the file stats to the console and  
  * saving the statistics of the file in the output.txt file under stat folder located in the directory
  * The folder and the file may not show up immediately on intellij, you can either wait 4 - 5 seconds or run the tree command, to be sure that it has been created
   ```
     tree src/main/java/fileStorage
    ```
- [x] getAllStatistics() in the Main class returns all the statistics that was saved in the output.txt file

## Run Test

```bash
mvn clean verify
```

## UML DESIGN

Can be found in the resources folder# NTXtask
