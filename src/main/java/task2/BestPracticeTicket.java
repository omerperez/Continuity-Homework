package task2;

public class BestPracticeTicket implements Ticket{

    private String practiceRepresent;

    @Override
    public void represent(String description) {
        TicketFactory.bestPracticeTicketsCount += 1;
        this.practiceRepresent = description;
    }
}