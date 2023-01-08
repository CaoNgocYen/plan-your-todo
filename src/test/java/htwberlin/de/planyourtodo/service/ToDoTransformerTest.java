package htwberlin.de.planyourtodo.service;

import htwberlin.de.planyourtodo.persistence.ToDoEntity;
import htwberlin.de.planyourtodo.web.api.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

public class ToDoTransformerTest {
    private final ToDoTransformer underTest = new ToDoTransformer();

    @Test
    @DisplayName("should transform PersonEntity to Person")
    void should_transform_person_entity_to_person() {
        // given
        var todoEntity = Mockito.mock(ToDoEntity.class);
        doReturn(111L).when(todoEntity).getId();
        doReturn("Go shopping").when(todoEntity).getTitle();
        doReturn("Buy clothes").when(todoEntity).getDescription();
        doReturn(LocalDate.now()).when(todoEntity).getDeadline();
        doReturn(true).when(todoEntity).isCompleted();

        // when
        var result = underTest.transformEntity(todoEntity);

        // then
        assertThat(result.getId()).isEqualTo(111);
        assertThat(result.getTitle()).isEqualTo("Go shopping");
        assertThat(result.getDescription()).isEqualTo("Buy clothes");
        assertThat(result.getDeadline()).isEqualTo(LocalDate.now());
        assertThat(result.isCompleted()).isEqualTo(true);
    }

}
