package htwberlin.de.planyourtodo.service;

import htwberlin.de.planyourtodo.persistence.ToDoEntity;
import htwberlin.de.planyourtodo.web.api.ToDo;
import org.springframework.stereotype.Service;

@Service
public class ToDoTransformer {

    public ToDo transformEntity(ToDoEntity toDoEntity) {
        return new ToDo(
                toDoEntity.getId(),
                toDoEntity.getTitle(),
                toDoEntity.getDescription(),
                toDoEntity.getDeadline(),
                toDoEntity.isCompleted());
    }
}
