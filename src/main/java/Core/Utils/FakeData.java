package Core.Utils;

import com.github.javafaker.Faker;

public class FakeData {
    private static Faker faker = new Faker();

    public static String fakeName() {
        return faker.name().fullName();
    }

    public static String fakeHarryPotter() {
        return faker.harryPotter().character();
    }

    public static String fakeEmail() {
        return faker.internet().emailAddress();
    }

    public static String fakeId() {
        return faker.idNumber().valid();
    }
}
