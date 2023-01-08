package htwberlin.de.planyourtodo.web.api;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ToDoManipulationRequest {

    @NotBlank(message = "A title muss be filled in.")
    private String title;
    @NotBlank(message = "A description muss be filled in.")
    private String description;
    @FutureOrPresent(message = "A date muss be selected.")
    private LocalDate deadline;
    private boolean completed;

    public ToDoManipulationRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
