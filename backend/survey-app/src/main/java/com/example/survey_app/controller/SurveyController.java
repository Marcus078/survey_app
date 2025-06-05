package com.example.survey_app.controller;

import com.example.survey_app.model.SurveyResponse;
import com.example.survey_app.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // Submit a new survey
    @PostMapping
    public ResponseEntity<?> submitSurvey(@Valid @RequestBody SurveyResponse survey, BindingResult result) {
        if (result.hasErrors()) {
            // Collect all validation errors into a map
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            surveyService.saveSurvey(survey);
            return ResponseEntity.ok("Survey submitted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("ageError", e.getMessage()));
        }
    }

    // Get all survey responses
    @GetMapping
    public List<SurveyResponse> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    // Analytics endpoint - example for total surveys
    @GetMapping("/analytics/total")
    public Map<String, Integer> getTotalSurveys() {
        return Map.of("totalSurveys", surveyService.getTotalSurveys());
    }



    //Get average age endpoint
    @GetMapping("/analytics/average-age")
    public Map<String, Double> getAverageAge() {
        return Map.of("averageAge", surveyService.getAverageAge());
    }

    //Get oldest age endpoint
    @GetMapping("/analytics/oldest-age")
    public Map<String, Integer> getOldestAge() {
        return Map.of("oldestAge", surveyService.getOldestAge());
    }
    //Get youngest endpoint
    @GetMapping("/analytics/youngest-age")
    public Map<String, Integer> getYoungestAge() {
        return Map.of("youngestAge", surveyService.getYoungestAge());
    }


    @GetMapping("/analytics/pizza-percentage")
    public Map<String, Double> getPizzaPercentage() {
        return Map.of("pizzaPercentage", surveyService.getPizzaPercentage());
    }

    @GetMapping("/analytics/pasta-percentage")
    public Map<String, Double> getPastaPercentage() {
        return Map.of("pastaPercentage", surveyService.getPastaPercentage());
    }

    @GetMapping("/analytics/pap-and-wors-percentage")
    public Map<String, Double> getPapAndWorsPercentage() {
        return Map.of("papAndWorsPercentage", surveyService.getPapAndWorsPercentage());
    }

    @GetMapping("/analytics/average-movie-rating")
    public Map<String, Double> getAverageMovieRating() {
        return Map.of("averageMovieRating", surveyService.getAverageMovieRating());
    }

    @GetMapping("/analytics/average-radio-rating")
    public Map<String, Double> getAverageRadioRating() {
        return Map.of("averageRadioRating", surveyService.getAverageRadioRating());
    }

    @GetMapping("/analytics/average-eatout-rating")
    public Map<String, Double> getAverageEatOutRating() {
        return Map.of("averageEatOutRating", surveyService.getAverageEatOutRating());
    }

    @GetMapping("/analytics/average-tv-rating")
    public Map<String, Double> getAverageTvRating() {
        return Map.of("averageTvRating", surveyService.getAverageTvRating());
    }
}

