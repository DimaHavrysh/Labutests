package com.example.quizplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.quizplatform.dto.QuizDTO;
import com.example.quizplatform.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllQuizzes_shouldReturnQuizList() throws Exception {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(1L);
        quizDTO.setTitle("Математична вікторина");
        quizDTO.setDescription("Вікторина з базової математики");

        when(quizService.getAllQuizzes()).thenReturn(Arrays.asList(quizDTO));

        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Математична вікторина"))
                .andExpect(jsonPath("$[0].description").value("Вікторина з базової математики"));
    }

    @Test
    void createQuiz_shouldReturnCreatedQuiz() throws Exception {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setTitle("Нова вікторина");
        quizDTO.setDescription("Опис нової вікторини");

        when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(quizDTO);

        mockMvc.perform(post("/api/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Нова вікторина"))
                .andExpect(jsonPath("$.description").value("Опис нової вікторини"));
    }

    @Test
    void createQuiz_withInvalidData_shouldReturnBadRequest() throws Exception {
        QuizDTO quizDTO = new QuizDTO();
        // Title is missing, should trigger validation error

        mockMvc.perform(post("/api/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is mandatory"));
    }
}