package aut.utcluj.isp.ex2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author stefan
 */
public class AirplaneTicketTest {
    @Test
    public void testCreate() {
        final AirplaneTicket airplaneTicket = new AirplaneTicket("TK-1234", "John", 20D, "Cluj-Napoca");
        assertEquals("Ticket destination should be 'Cluj-Napoca'", "Cluj-Napoca", airplaneTicket.getDestination());
        assertThat("Instance should be 'Ticket'", airplaneTicket, instanceOf(Ticket.class));
    }
}
