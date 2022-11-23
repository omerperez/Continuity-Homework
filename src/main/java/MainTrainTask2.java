import ModelsTask2.Ticket;

import java.util.*;
import java.util.logging.Logger;

import static constant.IConstantSecondPart.ticketsTypes;

public class MainTrainTask2 {

	public static List<Ticket> ticketList = new ArrayList<>();

	public static void main(String[] args) {
		TicketFactory ticketFactory = new TicketFactory();

		for(int i = 0; i < 1000; i++) {
			Random r = new Random();
			String randomTicketType = ticketsTypes.get(r.nextInt(ticketsTypes.size()));
			Logger.getLogger("services").info(String.format("Random Type - %s",randomTicketType ));
			ticketFactory.createTicketType(randomTicketType, randomTicketType.concat("- reason..." + i));
		}

		Map<String, Integer> score = new HashMap<>();
		Map<String, Integer> typeOfCVE = new HashMap<>();

		for(Ticket ticket: Ticket.ticketList){
			if(score.containsKey(ticket.getSeverityLevel())){
				Integer count = score.get(ticket.getSeverityLevel());
				score.put(ticket.getSeverityLevel(), count + 1 );
			} else {
				score.put(ticket.getSeverityLevel(), 1);
			}
			if(typeOfCVE.containsKey(ticket.getCVE())){
				Integer count = typeOfCVE.get(ticket.getCVE());
				typeOfCVE.put(ticket.getCVE(), count + 1);
			} else {
				if(ticket.getCVE() != null) {
					typeOfCVE.put(ticket.getCVE(), 1);
				}
			}
		}
		System.out.println("Severity: ");
		System.out.println(score);

		System.out.println("CVE: ");
		System.out.println(typeOfCVE);
	}
}
