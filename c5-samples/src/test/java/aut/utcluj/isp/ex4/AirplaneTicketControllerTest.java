package aut.utcluj.isp.ex4;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author stefan
 */
public class AirplaneTicketControllerTest {
    @Test
    public void shouldGenerateDefaultTickets() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();

        // asserts
        final List<AirplaneTicket> tickets = airplaneTicketController.getTickets();
        Assert.assertNotNull("Tickets should not be null", tickets);
        Assert.assertEquals("Default number of tickets should be 10", AirplaneTicketController.DEFAULT_NUMBER_OF_TICKETS, tickets.size());

        for (AirplaneTicket airplaneTicket : tickets) {
            Assert.assertNotNull("Airplane ticket should not be null", airplaneTicket);
            Assert.assertNotNull("Ticket id should not be null", airplaneTicket.getId());
            Assert.assertEquals("Ticket status should be active", TicketStatus.NEW, airplaneTicket.getStatus());
        }
    }

    @Test(expected = NoTicketAvailableException.class)
    public void shouldThrowExceptionWhenGetTicketDetailsAndTicketNotFound() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.getTicketDetails("Some-Random-Id");
    }

    @Test
    public void shouldGetTicketDetails() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        final AirplaneTicket airplaneTicket = airplaneTicketController.getTicketDetails("ID-2");

        // asserts
        Assert.assertNotNull("Airplane ticket should not be null", airplaneTicket);
        Assert.assertEquals("Id should be ID-2", "ID-2", airplaneTicket.getId());
        Assert.assertEquals("Destination should be 'Cluj-Napoca'", "Cluj-Napoca", airplaneTicket.getDestination());
    }

    @Test(expected = NoDestinationAvailableException.class)
    public void shouldThrowExceptionWhenBuyTicketAndDestinationNotFound() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.buyTicket("Some-Random-Destination", "John");
    }

    @Test(expected = NoTicketAvailableException.class)
    public void shouldThrowExceptionWhenBuyTicketAndNoTicketAvailable() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        // we have 3 tickets with destination Cluj Napoca
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");

        // try to buy the fourth => should throw exception
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
    }

    @Test(expected = NoTicketAvailableException.class)
    public void shouldThrowExceptionWhenCancelTicketAndTicketNotFound() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.cancelTicket("Some-Random-Id");
    }

    @Test
    public void shouldCancelTicket() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.cancelTicket("ID-0");

        // asserts
        final AirplaneTicket airplaneTicket = airplaneTicketController.getTicketDetails("ID-0");
        Assert.assertEquals("Id should be ID-0", "ID-0", airplaneTicket.getId());
        Assert.assertEquals("Status should be CANCELED", TicketStatus.CANCELED, airplaneTicket.getStatus());
    }

    @Test(expected = NoTicketAvailableException.class)
    public void shouldThrowExceptionWhenChangeTicketCustomerNameAndTicketNotFound() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.changeTicketCustomerId("Some-random-Id", "New Customer Name");
    }

    @Test(expected = TicketNotAssignedException.class)
    public void shouldThrowExceptionWhenChangeTicketCustomerNameAndTicketNotAssigned() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.changeTicketCustomerId("ID-2", "New Customer Name");
    }

    @Test
    public void shouldFilterTicketsByStatus() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();

        // buy 3 tickets
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");

        // cancel one ticket
        airplaneTicketController.cancelTicket("ID-0");

        // asserts
        List<AirplaneTicket> ticketsWithNewStatus = airplaneTicketController.filterTicketsByStatus(TicketStatus.NEW);
        List<AirplaneTicket> ticketsWithCanceledStatus = airplaneTicketController.filterTicketsByStatus(TicketStatus.CANCELED);
        List<AirplaneTicket> ticketsWithActiveStatus = airplaneTicketController.filterTicketsByStatus(TicketStatus.ACTIVE);

        Assert.assertEquals("Number of tickets with NEW status should be 7", 7, ticketsWithNewStatus.size());
        for (AirplaneTicket airplaneTicket : ticketsWithNewStatus) {
            Assert.assertEquals("Status should be NEW", TicketStatus.NEW, airplaneTicket.getStatus());
        }

        Assert.assertEquals("Number of tickets with CANCELED status should be 1", 1, ticketsWithCanceledStatus.size());
        for (AirplaneTicket airplaneTicket : ticketsWithCanceledStatus) {
            Assert.assertEquals("Status should be CANCELED", TicketStatus.CANCELED, airplaneTicket.getStatus());
        }

        Assert.assertEquals("Number of tickets with ACTIVE status should be 2", 2, ticketsWithActiveStatus.size());
        for (AirplaneTicket airplaneTicket : ticketsWithActiveStatus) {
            Assert.assertEquals("Status should be ACTIVE", TicketStatus.ACTIVE, airplaneTicket.getStatus());
        }
    }

    @Test
    public void shouldGroupTicketsByCustomerName() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();

        // buy 3 tickets for John, 2 for Marry
        airplaneTicketController.buyTicket("Cluj-Napoca", "John");
        airplaneTicketController.buyTicket("Timisoara", "John");
        airplaneTicketController.buyTicket("Baia Mare", "John");

        airplaneTicketController.buyTicket("Timisoara", "Mary");
        airplaneTicketController.buyTicket("Baia Mare", "Mary");

        // asserts
        final Map<String, List<AirplaneTicket>> ticketsByCustomerName = airplaneTicketController.groupTicketsByCustomerId();
        Assert.assertEquals("Number of keys should be 2", 2, ticketsByCustomerName.keySet().size());
        final List<AirplaneTicket> johnsTickets = ticketsByCustomerName.get("John");
        Assert.assertNotNull("John's tickets should not be null", johnsTickets);
        Assert.assertEquals("Number of John's tickets should be 3", 3, johnsTickets.size());
        for (AirplaneTicket airplaneTicket : johnsTickets) {
            Assert.assertEquals("Customer name should be John", "John", airplaneTicket.getCustomerId());
        }

        final List<AirplaneTicket> marysTickets = ticketsByCustomerName.get("Mary");
        Assert.assertNotNull("Mary's tickets should not be null", marysTickets);
        Assert.assertEquals("Number of Mary's tickets should be 2", 2, marysTickets.size());
        for (AirplaneTicket airplaneTicket : marysTickets) {
            Assert.assertEquals("Customer name should be Mary", "Mary", airplaneTicket.getCustomerId());
        }
    }
}
