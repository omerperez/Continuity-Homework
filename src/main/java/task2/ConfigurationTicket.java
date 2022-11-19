package task2;

public class ConfigurationTicket implements Ticket{

    private String configRepresent;

    @Override
    public void represent(String description) {
        TicketFactory.configurationTicketsCount += 1;
        this.configRepresent = description;
    }
}
