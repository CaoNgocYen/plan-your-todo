package htwberlin.de.planyourtodo.service;

import htwberlin.de.planyourtodo.persistence.ToDoEntity;
import htwberlin.de.planyourtodo.persistence.ToDoRepository;
import htwberlin.de.planyourtodo.web.api.ToDo;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest implements WithAssertions {

    @Mock
    private ToDoRepository repository;

    @InjectMocks
    private ToDoService underTest;

    @Test
    @DisplayName("should return true if delete was successful")
    void should_return_true_if_delete_was_successful() {
        // given
        Long givenId = 111L;
        doReturn(true).when(repository).existsById(givenId);

        // when
        boolean result = underTest.deleteById(givenId);

        // then
        verify(repository).deleteById(givenId);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("should return false if todo to delete does not exist")
    void should_return_false_if_todo_to_delete_does_not_exist() {
        // given
        Long givenId = 111L;
        doReturn(false).when(repository).existsById(givenId);

        // when
        boolean result = underTest.deleteById(givenId);

        // then
        verifyNoMoreInteractions(repository);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("should return the correct ToDo when given a valid ID")
    void should_return_correct_todo_when_given_valid_id() {
        // given
        Long id = 1L;
        String title = "Write code";
        String description = "Write some code for a new feature";
        LocalDate deadline = LocalDate.of(2021, 1, 1);
        boolean completed = false;
        ToDoEntity toDoEntity = new ToDoEntity(title, description, deadline, completed);
        when(repository.findById(id)).thenReturn(Optional.of(toDoEntity));

        // when
        ToDo result = underTest.findById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getDescription()).isEqualTo(description);
        //assertThat(result.getDeadline()).isEqualTo(deadline);
        assertThat(result.isCompleted()).isEqualTo(completed);
    }


}
