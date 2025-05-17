package com.example.quizplatform.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class QuizDTO {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    private List<QuestionDTO> questions;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}