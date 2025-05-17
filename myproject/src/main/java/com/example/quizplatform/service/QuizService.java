package com.example.quizplatform.service;

import com.example.quizplatform.dto.QuizDTO;
import com.example.quizplatform.exception.ResourceNotFoundException;
import com.example.quizplatform.model.Quiz;
import com.example.quizplatform.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public QuizService(QuizRepository quizRepository, ModelMapper modelMapper) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
    }

    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quiz -> modelMapper.map(quiz, QuizDTO.class))
                .collect(Collectors.toList());
    }

    public QuizDTO getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        return modelMapper.map(quiz, QuizDTO.class);
    }

    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        Quiz savedQuiz = quizRepository.save(quiz);
        return modelMapper.map(savedQuiz, QuizDTO.class);
    }

    public QuizDTO updateQuiz(Long id, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        Quiz updatedQuiz = quizRepository.save(quiz);
        return modelMapper.map(updatedQuiz, QuizDTO.class);
    }

    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}