package com.alevel;

import com.alevel.model.DriverLicence;
import com.alevel.model.Person;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        final DriverLicence driverLicence = DriverLicence.builder()
                .categories(new String[]{"A", "B", "C"})
                .expireDate(Instant.parse("2021-11-30T18:35:24.00Z"))
                .build();
        final Person person = Person.builder()
                .firstName("John")
                .lastName("Smith")
                .driverLicence(Optional.ofNullable(driverLicence))
                .build();

        Instant currentDate = Instant.now();
        Optional.ofNullable(person)
                .flatMap(Person::getDriverLicence)
                .filter(driverLicence1 -> currentDate.isBefore(driverLicence1.getExpireDate()))
                .map(DriverLicence::getCategories)
                .ifPresentOrElse(categories -> System.out.println(Arrays.toString(categories)),
                        () -> {
                            throw new RuntimeException("Driver license is not valid");
                        });



        /*Optional<DriverLicence> licence = Optional.ofNullable(person)
                .map(Person::getDriverLicence) // getting driver licence from the person
                .filter(drvLicence -> drvLicence.isPresent() && drvLicence.get().getExpireDate().isAfter(currentDate)) // checking if driver licence is present and not expired
                .orElseThrow(() -> new RuntimeException("Driver licence not valid (not present or expired")); // throw exception if expired or not present
        // Printing categories
        licence.ifPresent(driverLicence1 -> Arrays.stream(driverLicence1.getCategories()).forEach(System.out::println));
         */
    }
}
