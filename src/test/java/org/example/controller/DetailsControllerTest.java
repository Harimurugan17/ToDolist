package org.example.controller;

import org.example.model.Details;
import org.example.repository.DetailsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DetailsController.class)
public class DetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetailsRepository detailsRepository;

    @Test
    @WithMockUser(username = "user")
    public void testListDetails() throws Exception {
        mockMvc.perform(get("/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("details-list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testShowAddForm() throws Exception {
        mockMvc.perform(get("/details/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-details"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testSaveDetails() throws Exception {
        mockMvc.perform(post("/details")
                        .with(csrf()) // Functionality requires CSRF token
                        .flashAttr("details", new Details("Hari", "Math", 101)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/details"));
        
        Mockito.verify(detailsRepository, Mockito.times(1)).save(Mockito.any(Details.class));
    }
}
