package com.example.quizplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotBlank(message = "Correct answer is mandatory")
    private String correctAnswer;

    private Long quizId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}