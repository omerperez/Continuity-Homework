package task2;

import java.util.ArrayList;
import java.util.List;

public abstract class Ticket {
    public static List<Ticket> ticketList = new ArrayList<>();

    public abstract String getId();
    public abstract String getDescription();
    public abstract String getSeverityLevel();
}
