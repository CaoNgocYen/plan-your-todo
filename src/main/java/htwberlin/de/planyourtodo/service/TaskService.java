package htwberlin.de.planyourtodo.service;

import htwberlin.de.planyourtodo.persistence.TaskEntity;
import htwberlin.de.planyourtodo.persistence.TaskRepository;
import htwberlin.de.planyourtodo.web.api.Task;
import htwberlin.de.planyourtodo.web.api.TaskManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskEntity -> new Task(
                        taskEntity.getId(),
                        taskEntity.getTitle(),
                        taskEntity.getDescription(),
                        taskEntity.getDueDate(),
                        taskEntity.isCompleted()
                ))
                .collect(Collectors.toList());
    }

    public Task findById(Long id) {
        var taskEntity = taskRepository.findById(id);
        return taskEntity.isPresent()? transformEntity(taskEntity.get()) : null;
    }

    public Task create(TaskManipulationRequest request) {
        var taskEntity = new TaskEntity(request.getTitle(), request.getDescription(), request.getDueDate(), request.isCompleted());
        taskEntity = taskRepository.save(taskEntity);
        return transformEntity(taskEntity);
    }

    public Task update(Long id, TaskManipulationRequest request) {
        var taskEntityOptional = taskRepository.findById(id);
        if (taskEntityOptional.isEmpty()){
            return null;
        }
        var taskEntity = taskEntityOptional.get();
        taskEntity.setTitle(request.getTitle());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setDueDate(request.getDueDate());
        taskEntity.setCompleted(request.isCompleted());
        return transformEntity(taskEntity);
    }

    public boolean deleteById(Long id) {
        if (!taskRepository.existsById(id)){
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    public Task transformEntity(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getDueDate(),
                taskEntity.isCompleted()
        );
    }
}

