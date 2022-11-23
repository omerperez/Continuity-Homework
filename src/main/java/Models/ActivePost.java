package Models;

import java.util.List;

public class ActivePost {
    private Integer id;
    private String title;
    private List<String> repliersEmails;

    public ActivePost() {}

    public ActivePost(Integer id, String title, List<String> repliersEmails) {
        this.id = id;
        this.title = title;
        this.repliersEmails = repliersEmails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getRepliersEmails() {
        return repliersEmails;
    }

    public void setRepliersEmails(List<String> repliersEmails) {
        this.repliersEmails = repliersEmails;
    }

    @Override
    public String toString() {
        return "ActivePost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", repliersEmails=" + repliersEmails +
                '}';
    }
}
