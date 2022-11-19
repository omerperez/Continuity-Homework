package task2;

public class SecurityTicket implements Ticket{

    private String securityRepresent;

    @Override
    public void represent(String description) {
        TicketFactory.securitiesTicketsCount += 1;
        this.securityRepresent = description;
    }
}
