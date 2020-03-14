package personnes;

public class Personne {

    private String id;
    private String pass;
    private String ftp;

    public Personne(String id, String pass, String ftp) {
        this.id = id;
        this.pass = pass;
        this.ftp = ftp;

    }

    public String getId() {
        return this.id;
    }
    public String getPass() {
        return this.pass;
    }
    public String getFtp() {
        return this.ftp;
    }

}