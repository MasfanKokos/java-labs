package ru.lab;

import ru.lab.entity.Person;
import ru.lab.parser.CsvPersonParser;

import java.util.List;

/**
 * Entry point: loads employees from {@code foreign_names.csv} and prints a summary.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        CsvPersonParser parser = new CsvPersonParser();
        List<Person> persons = parser.parse("foreign_names.csv");

        System.out.println("Total persons loaded: " + persons.size());
        System.out.println("First 5 records:");
        persons.stream().limit(5).forEach(System.out::println);
    }
}
