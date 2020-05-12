package aut.utcluj.isp.ex1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author stefan
 */
public class TicketTest {
    @Test
    public void testCreateAndEquals() {
        final Ticket firstTicket = new Ticket("TK-1234", "John", 22D);
        assertEquals("Ticket id should be 'TK-1234", "TK-1234", firstTicket.getId());
        assertEquals("Customer name should be 'John'", "John", firstTicket.getCustomerName());
        assertEquals("Ticket price should be '22'", 22D, firstTicket.getPrice(), 0);

        final Ticket secondTicket = new Ticket("Merry", 20D);
        assertNotNull("Ticket id should not be null", secondTicket.getId());
        assertEquals("Customer name should be 'Merry'", "Merry", secondTicket.getCustomerName());
        assertEquals("Ticket price should be '20'", 20D, secondTicket.getPrice(), 0);

        final Ticket firstInstance = new Ticket("TK-1234", "John", 20D);
        final Ticket secondInstance = new Ticket("TK-1234", "John", 20D);

        assertEquals("Tickets should be equals", firstInstance, secondInstance);
    }
}