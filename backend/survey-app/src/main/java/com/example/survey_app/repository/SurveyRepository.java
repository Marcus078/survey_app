package com.example.survey_app.repository;


import com.example.survey_app.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<SurveyResponse, Long> {
}

