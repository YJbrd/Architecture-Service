package fr.insa.brauard.RequestService.Controller;

public class Task {

    private int idTask; // Correspond à ID_Task
    private int proprioId; // Correspond à ProprioID
    private String status; // Correspond à Status
    private String description; // Correspond à Description
    private String dateLimite; // Correspond à DateLimite
    private String validation; // Correspond à Validation

    // Constructeur complet
    public Task(int idTask, int proprioId, String status, String description, String dateLimite, String validation) {
        this.idTask = idTask;
        this.proprioId = proprioId;
        this.status = status;
        this.description = description;
        this.dateLimite = dateLimite;
        this.validation = validation;
    }

    // Constructeur sans statut ni validation (valeurs par défaut)
    public Task(int idTask, int proprioId, String description, String dateLimite) {
        this.idTask = idTask;
        this.proprioId = proprioId;
        this.status = "Non effectuée"; // Valeur par défaut
        this.description = description;
        this.dateLimite = dateLimite;
        this.validation = "En attente"; // Valeur par défaut
    }

    // Getters et Setters
    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getProprioId() {
        return proprioId;
    }

    public void setProprioId(int proprioId) {
        this.proprioId = proprioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
