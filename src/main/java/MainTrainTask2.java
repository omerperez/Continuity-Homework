import task2.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static constant.IConstantSecondPart.ticketsTypes;
import static task2.Ticket.ticketList;

public class MainTrainTask2 {


	public static void main(String[] args) {
		TicketFactory ticketFactory = new TicketFactory();

		for(int i = 0; i < 1000; i++) {
			Random r = new Random();
			String randomTicketType = ticketsTypes.get(r.nextInt(ticketsTypes.size()));
			Logger.getLogger("services").info(String.format("Random Type - %s",randomTicketType ));
			ticketFactory.createTicketType(randomTicketType, randomTicketType.concat("- reason..." + i));
		}

		Map<String, Integer> score = new HashMap<>();
		for(Ticket ticket: ticketList){
			if(score.containsKey(ticket.getSeverityLevel())){
				Integer count = score.get(ticket.getSeverityLevel());
				score.put(ticket.getSeverityLevel(), count + 1 );
			} else {
				score.put(ticket.getSeverityLevel(), 1);
			}
		}
		System.out.println(score);

		Map<String, Integer> CVE = new HashMap<>();
		for(int i = 0; i < ticketList.size(); i++){
			if(CVE.containsKey(ticketList.get(i).getCVE())){
				Integer count = CVE.get(ticketList.get(i).getCVE());
				CVE.put(ticketList.get(i).getCVE(), count + 1);
			} else {
				if(ticketList.get(i).getCVE() != null) {
					CVE.put(ticketList.get(i).getCVE(), 1);
				}
			}
		}
		System.out.println(CVE);
	}
}
