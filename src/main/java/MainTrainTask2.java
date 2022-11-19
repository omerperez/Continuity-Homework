import task2.Ticket;
import task2.TicketFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainTrainTask2 {


	public static void main(String[] args) {
		String[] list = {"Security", "Configuration", "BestPractice"};
		TicketFactory ticketFactory = new TicketFactory();
		List<Ticket> ticketList = new ArrayList<>();

		for(int i = 0; i < 1000; i++) {
			Random r = new Random();
			String s = list[r.nextInt(list.length)];
			Ticket ticket = ticketFactory.createTicketType(s);
			ticket.represent(s.concat("number" + i));
			ticketList.add(ticket);
		}
		System.out.println("Security Tickets Count: " + TicketFactory.securitiesTicketsCount);
		System.out.println("Configuration Tickets Count: " + TicketFactory.configurationTicketsCount);
		System.out.println("Best Practice Tickets Count: " + TicketFactory.bestPracticeTicketsCount);
	}
}
