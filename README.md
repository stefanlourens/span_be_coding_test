## BE Coding Test 

### Implementation 

I took the opportunity to test-drive Scala 3, particularly the new ```significant indentation``` syntax, hopefully it 
doesn't offend you (the reviewer) as much as it offended me initially. I also made liberal use of type aliases to 
improve readability.

Outside of the above, it's a standard SBT Scala project, using the following libraries / plugins:
* ```sbt-assembly``` - building an executable jar
* ```scalatest```  - unit testing


The implementation consists of the following classes: 
* [Team](src/main/scala/league/Team.scala) - A wrapper around the team's name, allowing overrides of equality and hashing, to deal with case differences.
* [Match](src/main/scala/league/Match.scala) - Case class representing a match, containing two tuples representing each team and their score (Team -> Score).
* [Table](src/main/scala/league/Table.scala) - Representing the table rankings, that are lazily evaluated once ```toString``` is called.
* [App](src/main/scala/App.scala) - The main entry point handling arguments and printing of the table.


### Usage

The project can be compiled / tested / ran via the provided shell script: 

```bash
./build_and_run.sh PATH/TO/INPUT_FILE
```

or alternatively: 

```bash
sbt assembly && java -jar assembly/print-table.jar PATH/TO/INPUT_FILE 
```
