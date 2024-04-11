import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Temperature {
    private double degree;
    private char scale;

    public Temperature() {
        this.degree = 0;
        this.scale = 'C';
    }

    public Temperature(final char scale) {
        this.degree = 0;
        this.scale = scale;
    }

    public Temperature(final double degree) {
        this.degree = degree;
        this.scale = 'C';
    }

    public Temperature(final double degree, final char scale) {
        this.degree = degree;
        this.scale = scale;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Temperature)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return getDegreeInCelsius() == ((Temperature) o).getDegreeInCelsius();
    }

    public double getDegreeInCelsius() {
        if (scale == 'F') {
            return (degree - 32) * 5 / 9;
        }
        return degree;
    }

    public double getDegreeInFahrenheit() {
        if (scale == 'C') {
            return degree * 9 / 5 + 32;
        }
        return degree;
    }

    public boolean isGreaterThan(final Temperature obj) {
        return getDegreeInCelsius() > obj.getDegreeInCelsius();
    }

    public boolean isLessThan(final Temperature obj) {
        return getDegreeInCelsius() < obj.getDegreeInCelsius();
    }

    public void setDegree(final double degree) {
        this.degree = degree;
    }

    public void setDegree(final double degree, final char scale) {
        this.degree = degree;
        this.scale = scale;
    }

    public void setScale(final char scale) {
        this.scale = scale;
    }
}
