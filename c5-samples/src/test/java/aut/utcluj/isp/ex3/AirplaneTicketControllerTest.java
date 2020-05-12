package aut.utcluj.isp.ex3;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @author stefan
 */
public class AirplaneTicketControllerTest {
    @Test
    public void shouldReturnAllTickets() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();

        // asserts
        final List<AirplaneTicket> tickets = airplaneTicketController.getTickets();
        Assert.assertNotNull("Tickets should not be null", tickets);
    }

    @Test
    public void shouldAddNewTicket() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-11", 20D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-12", 20D, "Cluj-Napoca"));

        // asserts
        final List<AirplaneTicket> tickets = airplaneTicketController.getTickets();
        Assert.assertEquals("Number of tickets should be 11", 2, tickets.size());
        // find our new ticket
        final Optional<AirplaneTicket> airplaneTicketOptional = tickets.stream()
                .filter(ticket -> ticket.getId().equals("ID-11"))
                .findFirst();
        Assert.assertTrue("Airplane ticket should not be null", airplaneTicketOptional.isPresent());
        final AirplaneTicket airplaneTicket = airplaneTicketOptional.get();
        Assert.assertEquals("Id should be ID-11", "ID-11", airplaneTicket.getId());
        Assert.assertEquals("Price should be 20", 20D, airplaneTicket.getPrice(), 0);
        Assert.assertEquals("Destination should be 'Cluj-Napoca'", "Cluj-Napoca", airplaneTicket.getDestination());
    }

    @Test
    public void shouldReturnTotalNumberOfTickets() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();

        // asserts
        Assert.assertEquals("Number of tickets should be 0", 0, airplaneTicketController.getTotalNumberOfTickets());

        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-12", 20D, "Cluj-Napoca"));

        // asserts
        Assert.assertEquals("Number of tickets should be 1", 1, airplaneTicketController.getTotalNumberOfTickets());
    }

    @Test
    public void shouldRemoveTicketById() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-11", 20D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-12", 20D, "Cluj-Napoca"));
        Assert.assertEquals("Number of tickets should be 2", 2, airplaneTicketController.getTotalNumberOfTickets());

        // remove non existent ticket
        airplaneTicketController.removeTicketById("ID-13");

        // assert
        Assert.assertEquals("Number of tickets should be 2", 2, airplaneTicketController.getTotalNumberOfTickets());

        // remove existing ticket
        airplaneTicketController.removeTicketById("ID-11");

        // assert
        final List<AirplaneTicket> tickets = airplaneTicketController.getTickets();
        Assert.assertEquals("Number of tickets should be 1", 1, tickets.size());
        final Optional<AirplaneTicket> airplaneTicketOptional = tickets
                .stream()
                .filter(ticket -> ticket.getId().equals("ID-11"))
                .findFirst();
        Assert.assertFalse("No ticket with id 'ID-11' should be found", airplaneTicketOptional.isPresent());
    }

    @Test
    public void shouldUpdateTicketDestination() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-11", 20D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-12", 20D, "Cluj-Napoca"));

        // update ticket with id 11
        airplaneTicketController.updateTicketDestination("ID-11", "New Destination");

        // asserts
        final List<AirplaneTicket> tickets = airplaneTicketController.getTickets();
        Assert.assertEquals("Number of tickets should be 2", 2, tickets.size());
        final Optional<AirplaneTicket> airplaneTicketWithId11Optional = tickets
                .stream()
                .filter(ticket -> ticket.getId().equals("ID-11"))
                .findFirst();
        Assert.assertEquals("Airplane ticket should be found", true, airplaneTicketWithId11Optional.isPresent());
        Assert.assertEquals("New destination should be 'New Destination'", "New Destination", airplaneTicketWithId11Optional.get().getDestination());

        // retrieve also airplane ticket with ID-12 just to make sure that only ID-11 is updated
        final Optional<AirplaneTicket> airplaneTicketWithId12Optional = tickets
                .stream()
                .filter(ticket -> ticket.getId().equals("ID-12"))
                .findFirst();
        Assert.assertEquals("Airplane ticket should be found", true, airplaneTicketWithId12Optional.isPresent());
        Assert.assertEquals("Airplane with id 12 should not be updated", "Cluj-Napoca", airplaneTicketWithId12Optional.get().getDestination());
    }

    @Test
    public void shouldFilterTicketsBetweenMinAndMaxPrice() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-1", 10D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-2", 12D, "Timisoara"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-3", 8D, "Baia Mare"));
        Assert.assertEquals("Number of tickets should be 3", 3, airplaneTicketController.getTotalNumberOfTickets());

        // asserts
        final List<AirplaneTicket> noTicketsAvailable = airplaneTicketController.filterTicketsBetweenMinMaxPrice(0d, 0d);
        Assert.assertEquals("Number of filtered tickets should be 0", 0, noTicketsAvailable.size());

        final List<AirplaneTicket> ticketsBetween10And10 = airplaneTicketController.filterTicketsBetweenMinMaxPrice(10d, 10d);
        Assert.assertEquals("Number of filtered tickets should be 1", 1, ticketsBetween10And10.size());
        Assert.assertEquals("Id should be 'ID-1'", "ID-1", ticketsBetween10And10.get(0).getId());

        final List<AirplaneTicket> ticketsBetween8And12 = airplaneTicketController.filterTicketsBetweenMinMaxPrice(8d, 12D);
        Assert.assertEquals("Number of filtered tickets should be 3", 3, ticketsBetween8And12.size());
    }

    @Test
    public void shouldFilterTicketsWithPriceAndDestination() {
        final AirplaneTicketController airplaneTicketController = new AirplaneTicketController();
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-1", 10D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-2", 12D, "Timisoara"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-3", 8D, "Baia Mare"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-4", 9D, "Cluj-Napoca"));
        airplaneTicketController.addNewTicket(new AirplaneTicket("ID-5", 11D, "Cluj-Napoca"));
        Assert.assertEquals("Number of tickets should be 5", 5, airplaneTicketController.getTotalNumberOfTickets());

        // asserts
        final List<AirplaneTicket> noTicketsAvailable = airplaneTicketController.filterTicketsWithPriceAndDestination(0d, 0d, "Cluj");
        Assert.assertEquals("Number of filtered tickets should be 0", 0, noTicketsAvailable.size());

        final List<AirplaneTicket> ticketsWithClujNapoca = airplaneTicketController.filterTicketsWithPriceAndDestination(10D, 11D, "Cluj-Napoca");
        Assert.assertEquals("Number of filtered tickets should be 2", 2, ticketsWithClujNapoca.size());
        for (AirplaneTicket ticket : ticketsWithClujNapoca) {
            Assert.assertEquals("Destination should be Cluj-Napoca", "Cluj-Napoca", ticket.getDestination());
            Assert.assertTrue("Price should be in interval", ticket.getPrice() >= 10 && ticket.getPrice() <= 11);
            Assert.assertTrue("Id should be ID-5 or ID-1", ticket.getId().equals("ID-5") || ticket.getId().equals("ID-1"));
        }
    }
}
