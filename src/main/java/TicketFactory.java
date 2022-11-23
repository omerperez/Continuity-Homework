import ModelsTask2.BestPracticeTicket;
import ModelsTask2.ConfigurationTicket;
import ModelsTask2.SecurityTicket;
import task2.Ticket;

import java.util.Random;
import java.util.UUID;

import static constant.IConstantSecondPart.listOfCVE;

public class TicketFactory {

    Random r = new Random();
    Double calcRangeNumber = 10.0;

    public Ticket createTicketType(String ticketType, String represent){
        if(ticketType == null || ticketType.isEmpty()){
            return null;
        }
        String ticketId = UUID.randomUUID().toString();
        Double randomSeverityScore = (r.nextDouble() * calcRangeNumber) % calcRangeNumber;
        Integer randomCveLocation = r.nextInt(4);
        switch (ticketType){
            case "Security":
                return new SecurityTicket(ticketId, randomSeverityScore, listOfCVE.get(randomCveLocation), represent);
            case "Configuration":
                return new ConfigurationTicket(ticketId, randomSeverityScore,  represent);
            case "BestPractice":
                return new BestPracticeTicket(ticketId, randomSeverityScore, listOfCVE.get(randomCveLocation), represent);
            default:
                throw new IllegalArgumentException("Unknown ticket type " + ticketType);
        }
    }
}
