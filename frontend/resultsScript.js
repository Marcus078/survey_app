// resultsScript.js

// Ensures the script runs only after the entire HTML document has been loaded and parsed.
document.addEventListener('DOMContentLoaded', async () => {
    // Get the HTML element where survey results will be displayed.
    const resultsContent = document.getElementById('resultsContent');

     // Base URL for the backend analytics API.
    const API_BASE_URL = 'http://localhost:8080/api/surveys/analytics';

      try {
        // Use Promise.all to concurrently fetch data from all required API endpoints.
        const responses = await Promise.all([
            fetch(`${API_BASE_URL}/total`), // Total number of surveys
            fetch(`${API_BASE_URL}/average-age`),
            fetch(`${API_BASE_URL}/oldest-age`),
            fetch(`${API_BASE_URL}/youngest-age`),
            fetch(`${API_BASE_URL}/pizza-percentage`),
            fetch(`${API_BASE_URL}/pasta-percentage`),
            fetch(`${API_BASE_URL}/pap-and-wors-percentage`),
            fetch(`${API_BASE_URL}/average-movie-rating`),
            fetch(`${API_BASE_URL}/average-radio-rating`),
            fetch(`${API_BASE_URL}/average-eatout-rating`),
            fetch(`${API_BASE_URL}/average-tv-rating`)
        ]);

        // After all fetches complete, parse each response as JSON.
        const data = await Promise.all(responses.map(res => res.json()));

        // Extract values from the responses (each response is an object like { "key": value })
        const totalSurveys = data[0].totalSurveys;

        // If no surveys are available, display a message and stop further processing.
        if (totalSurveys === 0) {
            resultsContent.innerHTML = `
                <p class="no-surveys-message">No Surveys Available.</p>
            `;
            return;// Exit the function
        }

        const averageAge = data[1].averageAge;
        const oldestAge = data[2].oldestAge;
        const youngestAge = data[3].youngestAge;
        const pizzaPercentage = data[4].pizzaPercentage;
        const pastaPercentage = data[5].pastaPercentage;
        const papAndWorsPercentage = data[6].papAndWorsPercentage;
        const averageMovieRating = data[7].averageMovieRating;
        const averageRadioRating = data[8].averageRadioRating;
        const averageEatOutRating = data[9].averageEatOutRating;
        const averageTvRating = data[10].averageTvRating;

        // Structured the content into three distinct sections
        // Display results vertically with spacing
        resultsContent.innerHTML = `
            <div class="results-grid">
                <div class="results-section">
                    <div>
                        <strong>Total number of surveys :</strong>
                        <span class="result-value">#${totalSurveys}</span>
                    </div>
                    <div>
                        <strong>Average Age :</strong>
                        <span class="result-value">#${averageAge}</span>
                    </div>
                    <div>
                        <strong>Oldest person who participated in survey :</strong>
                        <span class="result-value">#${oldestAge}</span>
                    </div>
                    <div>
                        <strong>Youngest person who participated in survey :</strong>
                        <span class="result-value">#${youngestAge}</span>
                    </div>
                </div>

                <div class="results-section">
                    <div>
                        <strong>Percentage of people who like Pizza :</strong>
                        <span class="result-value">#${pizzaPercentage}%</span>
                    </div>
                    <div>
                        <strong>Percentage of people who like Pasta :</strong>
                        <span class="result-value">#${pastaPercentage}%</span>
                    </div>
                    <div>
                        <strong>Percentage of people who like Pap and Wors :</strong>
                        <span class="result-value">#${papAndWorsPercentage}%</span>
                    </div>
                </div>

                <div class="results-section">
                    <div>
                        <strong>People who like to watch movies :</strong>
                        <span class="result-value">#${averageMovieRating}</span>
                    </div>
                    <div>
                        <strong>People who like to listen to radio :</strong>
                        <span class="result-value">#${averageRadioRating}</span>
                    </div>
                    <div>
                        <strong>People who like like to eat out :</strong>
                        <span class="result-value">#${averageEatOutRating}</span>
                    </div>
                    <div>
                        <strong>People who like to watch TV :</strong>
                        <span class="result-value">#${averageTvRating}</span>
                    </div>
                </div>
            </div>
        `;

     } catch (error) {
        // Catches any errors during the fetch or JSON parsing.
        console.error('Error fetching survey results:', error);
        // Displays a user-friendly error message if data retrieval fails.
        resultsContent.innerHTML = `
            <p class="server-error-message">Failed to load survey results. Please ensure the backend server is running and accessible.</p>
        `;
    }
});