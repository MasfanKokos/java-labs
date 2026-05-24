package ru.lab.parser;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.lab.entity.Department;
import ru.lab.entity.Gender;
import ru.lab.entity.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads employee records from a semicolon-delimited CSV file and returns them
 * as a {@link List} of {@link Person} objects.
 *
 * <p>The CSV format (header included) is:
 * <pre>id;name;gender;BirtDate;Division;Salary</pre>
 *
 * <p>Departments are deduplicated: if two rows share the same division letter
 * they will reference the same {@link Department} instance.</p>
 */
public class CsvPersonParser {

    private static final char SEPARATOR = ';';
    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Parses the CSV file located at {@code csvFilePath} on the classpath.
     *
     * @param csvFilePath classpath-relative path to the CSV file (e.g. {@code "foreign_names.csv"})
     * @return unmodifiable list of persons in the order they appear in the file
     * @throws IOException  if the resource cannot be found or read
     * @throws CsvException if the CSV content is malformed
     */
    public List<Person> parse(String csvFilePath) throws IOException, CsvException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(csvFilePath);
        if (in == null) {
            throw new FileNotFoundException("Classpath resource not found: " + csvFilePath);
        }

        List<Person> persons = new ArrayList<>();
        Map<String, Department> departments = new HashMap<>();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(in))
                .withCSVParser(new CSVParserBuilder().withSeparator(SEPARATOR).build())
                .withSkipLines(1)   // skip header row
                .build()) {

            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length < 6) continue;   // skip malformed rows

                int id                  = Integer.parseInt(row[0].trim());
                String name             = row[1].trim();
                Gender gender           = Gender.fromString(row[2].trim());
                LocalDate birthDate     = LocalDate.parse(row[3].trim(), DATE_FMT);
                String divisionName     = row[4].trim();
                double salary           = Double.parseDouble(row[5].trim());

                Department dept = departments.computeIfAbsent(divisionName, Department::new);
                persons.add(new Person(id, name, gender, dept, salary, birthDate));
            }
        }

        return List.copyOf(persons);
    }
}
