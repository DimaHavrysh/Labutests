package com.example.quizplatform.service;

import com.example.quizplatform.dto.QuestionDTO;
import com.example.quizplatform.exception.ResourceNotFoundException;
import com.example.quizplatform.model.Question;
import com.example.quizplatform.model.Quiz;
import com.example.quizplatform.repository.QuestionRepository;
import com.example.quizplatform.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
    }

    public List<QuestionDTO> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId).stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return modelMapper.map(question, QuestionDTO.class);
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + questionDTO.getQuizId()));
        Question question = modelMapper.map(questionDTO, Question.class);
        question.setQuiz(quiz);
        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + questionDTO.getQuizId()));
        question.setText(questionDTO.getText());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setQuiz(quiz);
        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }

    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }
}