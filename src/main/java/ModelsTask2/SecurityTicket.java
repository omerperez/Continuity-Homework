package ModelsTask2;

import task2.Ticket;

public class SecurityTicket extends Ticket {

    private String id;
    private Double severityScore;
    private String CVE;
    private String securityRepresent;

    public SecurityTicket(String id, Double severityScore, String CVE, String securityRepresent) {
        this.id = id;
        this.severityScore = severityScore;
        this.CVE = CVE;
        this.securityRepresent = securityRepresent;
        ticketList.add(this);
    }

    @Override
    public String getDescription() {
        return this.securityRepresent;
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
