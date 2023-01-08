package htwberlin.de.planyourtodo.web;

import htwberlin.de.planyourtodo.ToDoRestController;
import htwberlin.de.planyourtodo.service.ToDoService;
import htwberlin.de.planyourtodo.web.api.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.time.LocalDate;
import org.hamcrest.Matchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToDoRestController.class)
class ToDoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoService toDoService;

    @Test
    @DisplayName("should return found todos from todo service")
    void should_return_found_todo_from_todo_service() throws Exception {
        // given
        var todos = List.of(
                new ToDo(1L, "Go shopping", "Buy clothes", LocalDate.of(2023, 01, 10), false),
                new ToDo(2L, "Send letter", "Send a letter to Thompson", LocalDate.of(2023, 01, 10), true)
        );
        doReturn(todos).when(toDoService).findAll();

        // when
        mockMvc.perform(get("/api/v1/todos"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Go shopping"))
                .andExpect(jsonPath("$[0].description").value("Buy clothes"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Send letter"))
                .andExpect(jsonPath("$[1].description").value("Send a letter to Thompson"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }
    @Test
    @DisplayName("should return 404 if todo is not found")
    void should_return_404_if_todo_is_not_found() throws Exception {
        // given
        doReturn(null).when(toDoService).findById(anyLong());

        // when
        mockMvc.perform(get("/api/v1/todos/123"))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 201 http status and Location header when creating a to-do")
    void should_return_201_http_status_and_location_header_when_creating_a_person() throws Exception {
        // given
        String todoToCreateAsJson = "{\"title\":, \"description\":, \"deadline\":,\"completed\":}";
        var todo = new ToDo(111L, null, null, null, false);
        doReturn(todo).when(toDoService).create(any());

        // when
        mockMvc.perform(
                        post("/api/v1/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(todoToCreateAsJson)
                )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.equalTo(("/api/v1/todos/" + todo.getId()))));

    }

    @Test
    @DisplayName("should validate create todo request")
    void should_validate_create_todo_request() throws Exception {
        // given
        String toDoToCreateAsJson = "{\"title\":, \"description\":, \"deadline\":,\"completed\":}";

        // when
        mockMvc.perform(
                        post("/api/v1/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toDoToCreateAsJson)
                )
                // then
                .andExpect(status().isBadRequest());
    }
}
