package ModelsTask2;

import task2.Ticket;

public class BestPracticeTicket extends Ticket {

    private String id;
    private Double severityScore;
    private String CVE;
    private String practiceRepresent;

    public BestPracticeTicket(String id, Double severityScore, String CVE, String practiceRepresent) {
        this.id = id;
        this.severityScore = severityScore;
        this.CVE = CVE;
        this.practiceRepresent = practiceRepresent;
        ticketList.add(this);
    }

    @Override
    public String getDescription() {
        return this.practiceRepresent;
    }

    @Override
    public String getCVE() {
        return CVE;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getSeverityLevel() {
        if(this.severityScore < 4)
            return "Low";
        else if (this.severityScore < 7){
            return "Medium";
        }
        return "High";
    }
}