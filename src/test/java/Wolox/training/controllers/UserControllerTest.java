package Wolox.training.controllers;

import Wolox.training.DAO.UserDAO;
import Wolox.training.exceptions.BookAlreadyOwnedException;
import Wolox.training.exceptions.UserDoesNotExistException;
import Wolox.training.models.User;
import Wolox.training.repositories.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@WebMvcTest(value = UserController.class, secure = false)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenAddedUsers_whenGetUsers_thenReturnFullList() throws Exception {
        User user1 = new User(new UserDAO("username", "name", LocalDate.now(), "password"));
        String user =  "{\"name\": \"name\",\"username\": \"username\",\"birthday\": " +
                "\"new Date(1995, 11, 17).toISOString().split(\"T\")[0]\",";
        List<User> expectedUsers = Arrays.asList(user1);
        Page<User> page = new PageImpl<>(expectedUsers);
        mvc.perform(put("/api/users/create/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(status().isCreated());
        given(userRepository.findAll(Mockito.mock(PageRequest.class))).willReturn(page);
        mvc.perform(get("/api/users/view/")
                .param("sortBy", "name")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(user1.getName())));
    }

    @Test
    public void givenAddedBooks_whenGetBooks_thenReturnFullList() throws Exception {
        User user1 = new User(new UserDAO("username", "name", LocalDate.now(), "password"));
        String book = "{\"author\": \"author\",\"title\": \"title\",\"subtitle\": \"subtitle\"," +
                "\"genre\": \"genre\", \"isbn\": \"isbn\",\"pages\": 5,\"image\": \"image\",\"publisher\": " +
                "\"publisher\",\"year\": \"year\" }";
        given(userRepository.findById(1)).willReturn(Optional.ofNullable(user1));
        mvc.perform(put("/api/users/addBook/1")
                .contentType(MediaType.APPLICATION_JSON).content(book))
                .andExpect(status().isOk());
        mvc.perform(get("/api/users/view/1/library")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("title")));
    }

    @Test(expected = BookAlreadyOwnedException.class)
    public void givenUserHasBook_whenAddTheSameBook_thenThrowException() throws Throwable {
        User user1 = new User(new UserDAO("username", "name", LocalDate.now(), "password"));
        String book = "{\"author\": \"author\",\"title\": \"title\",\"subtitle\": \"subtitle\"," +
                "\"genre\": \"genre\", \"isbn\": \"isbn\",\"pages\": 5,\"image\": \"image\",\"publisher\": " +
                "\"publisher\",\"year\": \"year\" }";
        given(userRepository.findById(1)).willReturn(Optional.ofNullable(user1));
        mvc.perform(put("/api/users/addBook/1")
                .contentType(MediaType.APPLICATION_JSON).content(book));
        try {
            mvc.perform(put("/api/users/addBook/1")
                    .contentType(MediaType.APPLICATION_JSON).content(book));
        } catch (NestedServletException ex) {
            throw ex.getCause();
        }
    }

    @Test(expected = UserDoesNotExistException.class)
    public void givenUserExists_whenDeleteUser_thenUserDoesNotExist() throws Throwable {
        User user1 = new User(new UserDAO("username", "name", LocalDate.now(), "password"));
        List expectedUsers = Arrays.asList(user1);
        mvc.perform(delete("/api/users/delete/1")
                .contentType(MediaType.APPLICATION_JSON));
        try {
            mvc.perform(get("/api/users/view/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$", hasSize(0)));
        } catch (NestedServletException ex) {
            throw ex.getCause();
        }
    }
}
