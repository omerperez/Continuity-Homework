import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static constant.IConstantSecondPart.ticketsTypes;

public class MainTrainTask2 {


	public static void main(String[] args) {
		TicketFactory ticketFactory = new TicketFactory();

		for(int i = 0; i < 1000; i++) {
			Random r = new Random();
			String randomTicketType = ticketsTypes.get(r.nextInt(ticketsTypes.size()));
			Logger.getLogger("services").info(String.format("Random Type - %s",randomTicketType ));
			ticketFactory.createTicketType(randomTicketType, randomTicketType.concat("- reason..." + i));
		}

		Map<String, Integer> cve = new HashMap<>();
//		for(int i = 0; i < ticketList.size(); i++){
//			if(cve.containsKey(ticketList.get(i).getClass().getName()))
//			System.out.println(ticketList.get(i).toString());
//		}

		System.out.println("Security Tickets Count: " + TicketFactory.securitiesTicketsCount);
		System.out.println("Configuration Tickets Count: " + TicketFactory.configurationTicketsCount);
		System.out.println("Best Practice Tickets Count: " + TicketFactory.bestPracticeTicketsCount);
	}
}
