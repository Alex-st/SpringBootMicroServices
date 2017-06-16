package com.controllers;

import com.dto.UserModel;
import com.services.StorageService;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Oleksandr on 10/17/2016.
 */
public class MainControllerTest extends AbstractControllerTest {

    static final String PATH_API = "/user";

    @InjectMocks
    private MainController mainController;

    @Mock
    private StorageService storageService;

    private HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.initBaseController(mainController);
    }

    @Test
    public void shouldReturnAllUsersSuccess() throws Exception {
        when(storageService.getAllUsers()).thenReturn(asList(createUserModel()));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(PATH_API).content("application/json")).andReturn();

        int status = result.getResponse().getStatus();
        String contentAsString = result.getResponse().getContentAsString();
        verify(storageService, times(1)).getAllUsers();
        verifyNoMoreInteractions(storageService);
        assertEquals(200, status);
        assertEquals("[{\"id\":1,\"username\":\"userSuccess@test.com\"}]", contentAsString);
    }

    @Test
    public void shouldSuccessfullySaveUser() throws Exception {
        UserModel userModel = createUserModel();

        String json = objectToJsonString(userModel);
        MvcResult result = mockMvc.perform(
                post(PATH_API).content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();
        verify(storageService, times(1)).putData(any(UserModel.class));
        verifyNoMoreInteractions(storageService);
        assertEquals(200, status);
    }

    @Test
    public void shouldNotFindUser() throws Exception {
        when(storageService.getAllUsers()).thenThrow(new IllegalArgumentException("User userSuccess@test.com not found!"));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(PATH_API).content("application/json")).andReturn();

        int status = result.getResponse().getStatus();
        verify(storageService, times(1)).getAllUsers();
        verifyNoMoreInteractions(storageService);
        assertEquals(HttpStatus.SC_NOT_FOUND, status);
    }

    private UserModel createUserModel() {
        UserModel testModel = new UserModel();
        testModel.setUsername("userSuccess@test.com");
        testModel.setId(1);
        return testModel;
    }

    private String objectToJsonString(final Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
