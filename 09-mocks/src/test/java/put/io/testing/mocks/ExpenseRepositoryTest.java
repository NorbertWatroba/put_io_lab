package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import put.io.students.fancylibrary.database.FancyDatabase;
import put.io.students.fancylibrary.database.IFancyDatabase;

public class ExpenseRepositoryTest {

    @Test
    void loadExpenses() {
        // Tworzenie
        IFancyDatabase mockDatabase = mock(IFancyDatabase.class);

        // Konfiguracja
        when(mockDatabase.queryAll()).thenReturn(Collections.emptyList());

        // Interakcja
        ExpenseRepository repository = new ExpenseRepository(mockDatabase);
        repository.loadExpenses();

        // Weryfikacja
        InOrder inOrder = inOrder(mockDatabase);
        inOrder.verify(mockDatabase).connect();
        inOrder.verify(mockDatabase).queryAll();
        inOrder.verify(mockDatabase).close(); // To początkowo obleje test!

        // Asercja stanu obiektu (czy lista jest pusta)
        assertTrue(repository.getExpenses().isEmpty());
    }

    @Test
    void saveExpenses() {
        // Tworzenie
        IFancyDatabase mockDatabase = mock(IFancyDatabase.class);

        // Konfiguracja
        when(mockDatabase.queryAll()).thenReturn(Collections.emptyList());

        ExpenseRepository repository = new ExpenseRepository(mockDatabase);

        for (int i = 0; i < 5; i++) {
            repository.addExpense(new Expense());
        }

        // Interakcja
        repository.saveExpenses();

        // Weryfikacja
        verify(mockDatabase, times(5)).persist(any(Expense.class));
    }
}
