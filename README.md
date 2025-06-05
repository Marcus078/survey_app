# 📝 Survey Application

A simple Spring Boot application that allows users to fill out a survey form with personal details and preferences, stores the data in a MySQL database, and displays result analytics.

---

## 📚 Table of Contents

- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database Structure](#database-structure)
- [Validation Rules](#validation-rules)
- [Survey Result Analytics](#survey-result-analytics)

---

## Overview

This application collects survey data including:
- Full name
- Email
- Date of birth
- Contact number
- Survey questions with checkbox and radio inputs

It then provides analytics such as average age, pizza preferences, and average eating out rating.

---

## Technologies Used

- Java 22
- Spring Boot
- MySQL
- HTML/CSS
- JavaScript (basic form behavior)
- Spring Data JPA

---

## Features

- Survey form with personal details & preferences
- Input validation (on the backend)
- Data persistence using MySQL
- Analytics for submitted survey data
- User-friendly UI

---

## Installation

1. Clone this repo
2. Set up MySQL and create a database (e.g., `survey_db`)
3. Configure `application.properties`
4. Run the application with:
   ```bash
   ./mvnw spring-boot:run
5 Open http://localhost:8080


## Usage
	•	Navigate to the form, fill in the details, and submit
	•	Admin can access /results to view analytics

⸻

## API Endpoints
	•	GET / – Survey form
	•	POST /submit – Submit survey
	•	GET /results – Show results analytics


## Database Structure

Table: survey_response
- Column -Type
- id       Long
- full_name String
- email String
- dob Date
- contact String
- likes_pizza Boolean
- rating_out Integer
- submitted_at Timestamp


⸻

##Validation Rules
	•	Full name: not blank
	•	Email: valid format
	•	DOB: must be in the past
	•	Contact: digits only, min 10 chars

⸻

## Survey Result Analytics
	•	Total number of submissions
	•	Average age of participants
	•	Youngest & oldest participant
	•	Percentage of people who like pizza
	•	Average rating for eating out (1–5 scale)

⸻
