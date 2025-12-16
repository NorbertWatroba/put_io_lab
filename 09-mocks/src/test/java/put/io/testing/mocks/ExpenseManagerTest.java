package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {
    @Test
    void calculateTotal() {
        // Tworzenie
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);

        // Przygotowanie danych (3 wydatki)
        List<Expense> expenses = new ArrayList<>();
        for(int i=0; i<3; i++) {
            Expense e = new Expense();
            e.setAmount(10); // Każdy po 10 jednostek
            expenses.add(e);
        }

        // Konfiguracja
        when(mockRepo.getExpenses()).thenReturn(expenses);

        // Testowanie
        ExpenseManager manager = new ExpenseManager(mockRepo, mockService);
        long total = manager.calculateTotal();

        // Asercja: 3 * 10 = 30
        assertEquals(30, total);
    }

    @Test
    void calculateTotalForCategory() {
        // Tworzenie
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);

        Expense eHome = new Expense(); eHome.setAmount(100); eHome.setCategory("Home");
        Expense eCar = new Expense(); eCar.setAmount(200); eCar.setCategory("Car");

        List<Expense> homeList = new ArrayList<>(); homeList.add(eHome);
        List<Expense> carList = new ArrayList<>(); carList.add(eCar);

        // Konfiguracja
        when(mockRepo.getExpensesByCategory(anyString())).thenReturn(new ArrayList<>());
        when(mockRepo.getExpensesByCategory("Home")).thenReturn(homeList);
        when(mockRepo.getExpensesByCategory("Car")).thenReturn(carList);

        ExpenseManager manager = new ExpenseManager(mockRepo, mockService);

        // Asercje
        assertEquals(100, manager.calculateTotalForCategory("Home"));
        assertEquals(200, manager.calculateTotalForCategory("Car"));
        assertEquals(0, manager.calculateTotalForCategory("Sport"));
        assertEquals(0, manager.calculateTotalForCategory("Food"));
    }

    @Test
    void calculateTotalInDollars_ThrowsException() throws ConnectException {
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);

        Expense e = new Expense(); e.setAmount(100);
        when(mockRepo.getExpenses()).thenReturn(Collections.singletonList(e));

        when(mockService.convert(anyDouble(), eq("PLN"), eq("USD")))
                .thenThrow(new ConnectException());

        ExpenseManager manager = new ExpenseManager(mockRepo, mockService);

        assertEquals(-1, manager.calculateTotalInDollars());
    }

    @Test
    void calculateTotalInDollars_DynamicCalculation() throws ConnectException {
        ExpenseRepository mockRepo = mock(ExpenseRepository.class);
        FancyService mockService = mock(FancyService.class);

        Expense e = new Expense(); e.setAmount(100);
        when(mockRepo.getExpenses()).thenReturn(Collections.singletonList(e));

        when(mockService.convert(anyDouble(), eq("PLN"), eq("USD"))).thenAnswer(
                invocation -> {
                    double amountInPLN = invocation.getArgument(0);
                    return amountInPLN / 4.0;
                }
        );

        ExpenseManager manager = new ExpenseManager(mockRepo, mockService);

        assertEquals(25, manager.calculateTotalInDollars());
    }
}
