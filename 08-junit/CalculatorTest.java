package put.io.testing.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
    }

    @Test
    void testAddition() {
        assertEquals(2, this.calculator.add(1, 1));
        assertEquals(4, this.calculator.add(2, 2));
    }

    @Test
    void testMultiplication() {
        assertEquals(0, this.calculator.multiply(1, 0));
        assertEquals(4, this.calculator.multiply(2, 2));
    }

    @Test
    void testAddPositiveNumbers() {
        assertThrows(RuntimeException.class, () -> {
            this.calculator.addPositiveNumbers(-1, 1);
        });
    }

}