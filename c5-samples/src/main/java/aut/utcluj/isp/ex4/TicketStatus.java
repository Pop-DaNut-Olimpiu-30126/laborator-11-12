package aut.utcluj.isp.ex4;

/**
 * @author stefan
 */
public enum TicketStatus {
    /**
     * By default, any ticket has ACTIVE status
     */
    NEW,

    /**
     * When a ticket is bought by the employee, status becomes ACTIVE
     */
    ACTIVE,

    /**
     * When a ticket is canceled by the employee, status becomes CANCELED
     */
    CANCELED
}
