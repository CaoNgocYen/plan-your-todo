package htwberlin.de.planyourtodo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
    List<ToDoEntity> findAllByTitle(String title);
}
