package task2;

public class TicketFactory {
    public static Integer securitiesTicketsCount = 0;
    public static Integer configurationTicketsCount = 0;
    public static Integer bestPracticeTicketsCount = 0;

    public Ticket createTicketType(String ticketType){
        if(ticketType == null || ticketType.isEmpty()){
            return null;
        }
        switch (ticketType){
            case "Security":
                return new SecurityTicket();
            case "Configuration":
                return new ConfigurationTicket();
            case "BestPractice":
                return new BestPracticeTicket();
            default:
                throw new IllegalArgumentException("Unknown ticket type " + ticketType);
        }
    }
}
