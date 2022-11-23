package ModelsTask2;


public class ConfigurationTicket extends Ticket {

    private String id;
    private Double severityScore;
    private String configRepresent;

    public ConfigurationTicket(String id, Double severityScore, String configRepresent) {
        this.id = id;
        this.severityScore = severityScore;
        this.configRepresent = configRepresent;
        ticketList.add(this);
    }

    @Override
    public String getDescription() {
        return this.configRepresent;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getCVE() {
        return null;
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
