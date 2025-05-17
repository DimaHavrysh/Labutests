package com.example.quizplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.quizplatform.dto.QuestionDTO;
import com.example.quizplatform.service.QuestionService;
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

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getQuestionsByQuizId_shouldReturnQuestionList() throws Exception {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(1L);
        questionDTO.setText("Скільки буде 2 + 2?");
        questionDTO.setCorrectAnswer("4");
        questionDTO.setQuizId(1L);

        when(questionService.getQuestionsByQuizId(1L)).thenReturn(Arrays.asList(questionDTO));

        mockMvc.perform(get("/api/questions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Скільки буде 2 + 2?"))
                .andExpect(jsonPath("$[0].correctAnswer").value("4"));
    }

    @Test
    void createQuestion_shouldReturnCreatedQuestion() throws Exception {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setText("Нове питання");
        questionDTO.setCorrectAnswer("Відповідь");
        questionDTO.setQuizId(1L);

        when(questionService.createQuestion(any(QuestionDTO.class))).thenReturn(questionDTO);

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Нове питання"))
                .andExpect(jsonPath("$.correctAnswer").value("Відповідь"));
    }

    @Test
    void createQuestion_withInvalidData_shouldReturnBadRequest() throws Exception {
        QuestionDTO questionDTO = new QuestionDTO();
        // Text and correctAnswer are missing, should trigger validation error

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text").value("Text is mandatory"))
                .andExpect(jsonPath("$.correctAnswer").value("Correct answer is mandatory"));
    }
}
