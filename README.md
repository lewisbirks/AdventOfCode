# Advent Of Code 🎅

Java implementation of the [advent of code](https://adventofcode.com/)

## Years

- [2023](/advent-of-code.2023)
- [2022](/advent-of-code.2022)
- [2021](/advent-of-code.2021)
- [2020](/advent-of-code.2020)

## Requirements

- Java 17

----

Also being used as a test bed for learning things such as new java features and workflows

## 🚀 Running

#### IDE

Each module has a `Driver` class which can be used as the main class when running via the IDE

#### Maven

The main code can be run by first installing them

```shell
./mvnw install -DskipTests
```

and then running the appropriate jar located in each of the `target` directories generated

```shell
java -jar advent-of-code.2021/target/2021.jar
```

### 🧪 Tests

Unit tests can be run via

```shell
./mvnw test
```

Performance tests are found in the `src/performance` directory and can be run via

```shell
./mvnw verify -P performance
```

### 📝 TODO

- OCR
  for [2021 - 13 - part 2](/advent-of-code.2021/src/main/java/com/lewisbirks/adventofcode/day/Day13.java#L27)
    - optional printing to console
    - add flag for running with OCR and without
- Do proper performance analysis with jmh
