package htwberlin.de.planyourtodo;

import htwberlin.de.planyourtodo.service.ToDoService;
import htwberlin.de.planyourtodo.web.api.ToDo;
import htwberlin.de.planyourtodo.web.api.ToDoManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ToDoRestController {
    private final ToDoService toDoService;
    public ToDoRestController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping(path = "api/v1/todos")
    public ResponseEntity<List<ToDo>> fetchTasks() {
        return ResponseEntity.ok(toDoService.findAll());
    }

    @GetMapping(path = "api/v1/todos/{id}")
    public ResponseEntity<ToDo> fetchTaskById(@PathVariable Long id) {
        var toDo = toDoService.findById(id);
        return toDo != null? ResponseEntity.ok(toDo) : ResponseEntity.notFound().build();
    }

    @PostMapping(path = "api/v1/todos")
    public ResponseEntity<Void> createTask(@RequestBody ToDoManipulationRequest request) throws URISyntaxException {
         var toDo = toDoService.create(request);
         URI uri = new URI("/api/v1/todos/" + toDo.getId());
         return ResponseEntity.created(URI.create(String.valueOf(uri))).build();
    }

    @PutMapping(path = "api/v1/todos/{id}")
    public ResponseEntity<ToDo> updateTask(@PathVariable Long id, @RequestBody ToDoManipulationRequest request) {
        var toDo = toDoService.update(id, request);
        return toDo != null? ResponseEntity.ok(toDo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "api/v1/todos/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean successful = toDoService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
