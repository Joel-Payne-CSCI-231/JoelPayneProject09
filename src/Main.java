import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        final Temperature temperature0 = new Temperature();
        final Temperature temperature1 = new Temperature(32);
        final Temperature temperature2 = new Temperature('F');
        final Temperature temperature3 = new Temperature(98.6, 'F');

        printHeader(ConstructorMethod.DEFAULT, temperature0);
        promptUserForNewArguments(ConstructorMethod.DEFAULT, temperature0);

        printHeader(ConstructorMethod.DEGREE, temperature1);
        promptUserForNewArguments(ConstructorMethod.DEGREE, temperature1);

        printHeader(ConstructorMethod.SCALE, temperature2);
        promptUserForNewArguments(ConstructorMethod.SCALE, temperature2);

        printHeader(ConstructorMethod.DEGREE_SCALE, temperature3);
        promptUserForNewArguments(ConstructorMethod.DEGREE_SCALE, temperature3);

        System.out.printf(
                "In order of creation the temperatures in Celsius are: %s, %s, %s, %s%n",
                temperature0.getDegreeInCelsius(),
                temperature1.getDegreeInCelsius(),
                temperature2.getDegreeInCelsius(),
                temperature3.getDegreeInCelsius()
        );
        System.out.printf(
                "In order of creation the temperatures in Fahrenheit are: %s, %s, %s, %s%n",
                temperature0.getDegreeInFahrenheit(),
                temperature1.getDegreeInFahrenheit(),
                temperature2.getDegreeInFahrenheit(),
                temperature3.getDegreeInFahrenheit()
        );
        printComparisons(temperature0, temperature1, "first", "second");
        printComparisons(temperature0, temperature2, "first", "third");
        printComparisons(temperature0, temperature3, "first", "fourth");
        printComparisons(temperature1, temperature2, "second", "third");
        printComparisons(temperature1, temperature3, "second", "fourth");
        printComparisons(temperature2, temperature3, "third", "fourth");
    }

    private static void printHeader(
            final ConstructorMethod constructorMethod,
            final Temperature temperature
    ) {
        final boolean isDefaultConstructor = constructorMethod == ConstructorMethod.DEFAULT;
        String template = """
                {0} Temperature
                ==================
                  Created using the {1}.

                  Default Arguments
                  -----------------
                    {2}

                  Provided Arguments
                  ------------------
                    {3}
                """;

        String constructorDescription;

        if (isDefaultConstructor) {
            constructorDescription = "default constructor";
        } else {
            constructorDescription = MessageFormat.format(
                    "constructor with {0}",
                    MessageFormat.format(
                            constructorMethod.providedArguments.length == 1 ? "a {0} argument" : "{0} arguments",
                            Arrays.stream(constructorMethod.providedArguments)
                                    .map(arr -> "`%s`".formatted(arr[1]))
                                    .collect(Collectors.joining(" and "))
                    )
            );
        }
        String defaultArgumentsString = Arrays.stream(constructorMethod.defaultArguments)
                .map(arr -> "`%s`: set to the default value of `%s`".formatted(arr[0], arr[1]))
                .collect(Collectors.joining("\n    "));
        String providedArgumentsString = Arrays.stream(constructorMethod.providedArguments)
                .map(arr -> "`%s`: set to the provided `%s` argument".formatted(arr[0], arr[1]))
                .collect(Collectors.joining("\n    "));

        if (defaultArgumentsString.isBlank()) {
            defaultArgumentsString = "No default arguments.";
        }
        if (providedArgumentsString.isBlank()) {
            providedArgumentsString = "No arguments provided.";
        }

        String header = MessageFormat.format(
                template,
                constructorMethod.ordinal,
                constructorDescription,
                defaultArgumentsString,
                providedArgumentsString
        );
        System.out.println(header);
        printTemperature(temperature);
    }

    private static void promptUserForNewArguments(
            final ConstructorMethod constructorMethod,
            final Temperature temperature
    ) {
        final Scanner sc = new Scanner(System.in);
        System.out.printf(
                "Set the degree (a number) and the scale (`F` or `C`) of the %s temperature.%n",
                constructorMethod.ordinal
        );
        System.out.print("Please provide a new value for the `degree`: ");
        temperature.setDegree(sc.nextDouble());

        System.out.print("Please provide a new value for the `scale`: ");
        temperature.setScale(sc.next().toUpperCase().charAt(0));
        System.out.println();
        printTemperature(temperature);
    }

    private static void printComparisons(
            final Temperature tempA,
            final Temperature tempB,
            final String ordinalA,
            final String ordinalB
    ) {
        System.out.println(MessageFormat.format(
                "\nComparing {0} and {1} temperatures",
                ordinalA,
                ordinalB
        ));
        System.out.println(MessageFormat.format(
                "  {0} temperature is {2}equal to {1} temperature.",
                ordinalA,
                ordinalB,
                tempA.equals(tempB) ? "" : "not "
        ));
        System.out.println(MessageFormat.format(
                "  {0} temperature is {2}less than {1} temperature",
                ordinalA,
                ordinalB,
                tempA.isLessThan(tempB) ? "" : "not "
        ));
        System.out.println(MessageFormat.format(
                "  {0} temperature is {2}greater than {1} temperature",
                ordinalA,
                ordinalB,
                tempA.isGreaterThan(tempB) ? "" : "not "
        ));
    }

    private static void printTemperature(final Temperature temperature) {
        System.out.println(MessageFormat.format(
                "  Temperatures\n  ------------\n    {0} C\n    {1} F\n",
                temperature.getDegreeInCelsius(),
                temperature.getDegreeInFahrenheit()
        ));
    }

    public enum ConstructorMethod {
        DEFAULT(
                "First",
                new String[][]{
                        new String[]{"degree", "0.0"},
                        new String[]{"scale", "C"},
                },
                new String[][]{}
        ),
        DEGREE(
                "Second",
                new String[][]{
                        new String[]{"scale", "C"},
                },
                new String[][]{
                        new String[]{"degree", "double"},
                }
        ),
        SCALE(
                "Third",
                new String[][]{
                        new String[]{"degree", "0.0"},
                },
                new String[][]{
                        new String[]{"scale", "char"},
                }
        ),
        DEGREE_SCALE(
                "Fourth",
                new String[][]{},
                new String[][]{
                        new String[]{"degree", "double"},
                        new String[]{"scale", "char"},
                }
        );

        private final String[][] defaultArguments;
        private final String ordinal;
        private final String[][] providedArguments;

        ConstructorMethod(String ordinal, String[][] defaultArguments, String[][] providedArguments) {
            this.defaultArguments = defaultArguments;
            this.providedArguments = providedArguments;
            this.ordinal = ordinal;
        }
    }
}
