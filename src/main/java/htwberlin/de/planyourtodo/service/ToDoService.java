package htwberlin.de.planyourtodo.service;

import htwberlin.de.planyourtodo.persistence.ToDoEntity;
import htwberlin.de.planyourtodo.persistence.ToDoRepository;
import htwberlin.de.planyourtodo.web.api.ToDo;
import htwberlin.de.planyourtodo.web.api.ToDoManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> findAll() {
        List<ToDoEntity> toDos = toDoRepository.findAll();
        return toDos.stream()
                .map(toDoEntity -> new ToDo(
                        toDoEntity.getId(),
                        toDoEntity.getTitle(),
                        toDoEntity.getDescription(),
                        toDoEntity.getDueDate(),
                        toDoEntity.isCompleted()
                ))
                .collect(Collectors.toList());
    }

    public ToDo findById(Long id) {
        var toDoEntity = toDoRepository.findById(id);
        return toDoEntity.isPresent()? transformEntity(toDoEntity.get()) : null;
    }

    public ToDo create(ToDoManipulationRequest request) {
        var toDoEntity = new ToDoEntity(request.getTitle(), request.getDescription(), request.getDueDate(), request.isCompleted());
        toDoEntity = toDoRepository.save(toDoEntity);
        return transformEntity(toDoEntity);
    }

    public ToDo update(Long id, ToDoManipulationRequest request) {
        var toDoEntityOptional = toDoRepository.findById(id);
        if (toDoEntityOptional.isEmpty()){
            return null;
        }
        var toDoEntity = toDoEntityOptional.get();
        toDoEntity.setTitle(request.getTitle());
        toDoEntity.setDescription(request.getDescription());
        toDoEntity.setDueDate(request.getDueDate());
        toDoEntity.setCompleted(request.isCompleted());

        toDoRepository.save(toDoEntity);
        return transformEntity(toDoEntity);
    }

    public boolean deleteById(Long id) {
        if (!toDoRepository.existsById(id)){
            return false;
        }
        toDoRepository.deleteById(id);
        return true;
    }

    public ToDo transformEntity(ToDoEntity toDoEntity) {
        return new ToDo(
                toDoEntity.getId(),
                toDoEntity.getTitle(),
                toDoEntity.getDescription(),
                toDoEntity.getDueDate(),
                toDoEntity.isCompleted()
        );
    }
}

