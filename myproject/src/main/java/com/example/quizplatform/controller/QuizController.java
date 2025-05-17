package com.example.quizplatform.controller;

import com.example.quizplatform.dto.QuizDTO;
import com.example.quizplatform.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        return new ResponseEntity<>(quizService.createQuiz(quizDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @Valid @RequestBody QuizDTO quizDTO) {
        return ResponseEntity.ok(quizService.updateQuiz(id, quizDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}