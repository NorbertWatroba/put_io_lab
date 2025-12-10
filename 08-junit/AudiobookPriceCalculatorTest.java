package put.io.testing.audiobooks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudiobookPriceCalculatorTest {
    private AudiobookPriceCalculator audiobookPriceCalculator;
    private Audiobook audiobook;
    @BeforeEach
    void setUp() {
        this.audiobook = new Audiobook("audiobook",10.0);
        this.audiobookPriceCalculator = new AudiobookPriceCalculator();
    }

    @Test
    void getAudiobookPriceForSubscriber() {
        Customer customer = new Customer("test", Customer.LoyaltyLevel.GOLD, true);
        assertEquals(0.0, this.audiobookPriceCalculator.calculate(customer,this.audiobook));
    }

    @Test
    void getAudiobookPriceForGold() {
        Customer customer = new Customer("test", Customer.LoyaltyLevel.GOLD, false);
        assertEquals(this.audiobook.getStartingPrice() * 0.8, this.audiobookPriceCalculator.calculate(customer,this.audiobook));
    }

    @Test
    void getAudiobookPriceForSilver() {
        Customer customer = new Customer("test", Customer.LoyaltyLevel.SILVER, false);
        assertEquals(this.audiobook.getStartingPrice() * 0.9, this.audiobookPriceCalculator.calculate(customer,this.audiobook));
    }


    @Test
    void getAudiobookPriceForStandard() {
        Customer customer = new Customer("test", Customer.LoyaltyLevel.STANDARD, false);
        assertEquals(this.audiobook.getStartingPrice(), this.audiobookPriceCalculator.calculate(customer,this.audiobook));
    }
}