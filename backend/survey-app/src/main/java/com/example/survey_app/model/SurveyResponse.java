package com.example.survey_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Personal Details
    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Date of Birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Contact Number is required")
    private String contactNumber;

    //Survey Preferences

    @NotBlank(message = "A favourite food must be selected")
    @Pattern(regexp = "Pizza|Pasta|Pap and Wors|Others", message = "Favourite food must be one of: Pizza, Pasta, Pap and Wors, Others")
    private String favouriteFoods;


    @NotBlank(message = "Rating for 'Eat Out' is required")
    private String eatOut;

    @NotBlank(message = "Rating for 'Watch Movies' is required")
    private String watchMovies;

    @NotBlank(message = "Rating for 'Watch TV' is required")
    private String watchTV;

    @NotBlank(message = "Rating for 'Listen to Radio' is required")
    private String listenRadio;

    //Age utility

    // Calculate the age from DOB
    @Transient
    public int getAge() {
        if (this.dateOfBirth == null) return 0;
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    // Check age is within acceptable range
    @Transient
    public boolean isAgeValid() {
        int age = getAge();
        return age >= 5 && age <= 120;
    }

    //Rating Conversion

    @Transient
    private int convertRating(String rating) {
        return switch (rating) {
            case "Strongly Disagree" -> 5;
            case "Disagree" -> 4;
            case "Neutral" -> 3;
            case "Agree" -> 2;
            case "Strongly Agree" -> 1;
            default -> 0;
        };
    }

    @Transient
    public int getEatOutRating() {
        return convertRating(eatOut);
    }

    @Transient
    public int getMovieRating() {
        return convertRating(watchMovies);
    }

    @Transient
    public int getTvRating() {
        return convertRating(watchTV);
    }

    @Transient
    public int getRadioRating() {
        return convertRating(listenRadio);
    }
}
