package com.example.survey_app.service;

import com.example.survey_app.model.SurveyResponse;
import com.example.survey_app.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    // Save only if age is valid
    public void saveSurvey(SurveyResponse survey) {
        if (!survey.isAgeValid()) {
            throw new IllegalArgumentException("Only users aged between 5 and 120 are allowed to complete the survey.");
        }
        surveyRepository.save(survey);
    }

    // Retrieves all survey responses from the database
    public List<SurveyResponse> getAllSurveys() {
        return surveyRepository.findAll();
    }

    //Analytics

    // Calculates the total number of surveys recorded
    public int getTotalSurveys() {
        return surveyRepository.findAll().size();
    }

    //Calculates the average age of all survey participants.
    //The result is rounded to one decimal place
    public double getAverageAge() {
        List<SurveyResponse> surveys = surveyRepository.findAll();
        if (surveys.isEmpty()) return 0.0;

        double totalAge = 0.0;
        for (SurveyResponse survey : surveys) {
            totalAge += calculateAge(survey.getDateOfBirth());
        }

        return Math.round((totalAge / surveys.size()) * 10.0) / 10.0;
    }

    //Finds the age of the oldest person who participated in a survey.
    public int getOldestAge() {
        return surveyRepository.findAll().stream()
                .mapToInt(s -> calculateAge(s.getDateOfBirth()))
                .max().orElse(0);
    }

    //Finds the age of the youngest person who participated in a survey.
    public int getYoungestAge() {
        return surveyRepository.findAll().stream()
                .mapToInt(s -> calculateAge(s.getDateOfBirth()))
                .min().orElse(0);
    }
    //Calculates the percentage of survey participants who like "Pizza".
    public double getPizzaPercentage() {
        return calculateFoodPercentage("Pizza");
    }

   // Calculates the percentage of survey participants who like "Pasta".
    public double getPastaPercentage() {
        return calculateFoodPercentage("Pasta");
    }
//Calculates the percentage of survey participants who like "Pap and Wors".
    public double getPapAndWorsPercentage() {
        return calculateFoodPercentage("Pap and Wors");
    }

    //Helper method to calculate the percentage of people who prefer a specific food.
    //The result is rounded to one decimal place.
    private double calculateFoodPercentage(String food) {
        List<SurveyResponse> surveys = surveyRepository.findAll();
        if (surveys.isEmpty()) return 0.0;// Return 0 if there are no surveys.

        // Filters surveys to count those where the favorite food matches the given 'food' string.
        long count = surveys.stream()
                .filter(s -> s.getFavouriteFoods() != null && s.getFavouriteFoods().equals(food))
                .count();

        // Calculates the percentage and rounds it to one decimal place.
        return Math.round(((double) count / surveys.size()) * 1000.0) / 10.0; // 1 decimal place
    }

    //Calculates the average rating for "Watching movie".
    //The result is rounded to one decimal place.
    public double getAverageMovieRating() {
        // Collects all movie ratings and passes them to the generic average calculation helper.
        return getAverageOf(surveyRepository.findAll().stream()
                .mapToInt(SurveyResponse::getMovieRating).toArray());
    }

    //Calculates the average rating for "Watching radio".
    //The result is rounded to one decimal place.
    public double getAverageRadioRating() {
        // Collects all radio ratings and passes them to the generic average calculation helper.
        return getAverageOf(surveyRepository.findAll().stream()
                .mapToInt(SurveyResponse::getRadioRating).toArray());
    }

    //Calculates the average rating for "Eat out".
    //The result is rounded to one decimal place.

    public double getAverageEatOutRating() {
        return getAverageOf(surveyRepository.findAll().stream()
                .mapToInt(SurveyResponse::getEatOutRating).toArray());
    }

    //Calculates the average rating for "Watching TV".
    //Round the  result to one decimal place.

    public double getAverageTvRating() {
        // Collects all TV ratings and passes them to the generic average calculation helper
        return getAverageOf(surveyRepository.findAll().stream()
                .mapToInt(SurveyResponse::getTvRating).toArray());
    }

    //Helper Methods
//Calculates the average of an array of integer values.
//Round the result to one decimal place.
    private double getAverageOf(int[] values) {
        if (values.length == 0) return 0.0;// Return 0 if the array is empty to prevent division by zero.

        int total = 0;
        // Sums up all values in the array
        for (int v : values) total += v;

        // Calculates the average and rounds it to one decimal place
        return Math.round(((double) total / values.length) * 10.0) / 10.0;
    }

    //Calculates the age in years based on a given date of birth
    private int calculateAge(LocalDate dob) {

        return Period.between(dob, LocalDate.now()).getYears();
    }
}
