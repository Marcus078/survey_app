// script.js

// Ensures the script runs only after the entire HTML document has been loaded and parsed.
document.addEventListener('DOMContentLoaded', () => {
    // Get references to the survey form and the message display area.
    const surveyForm = document.getElementById('surveyForm');
    const formMessage = document.getElementById('formMessage');

    // Add an event listener for the form submission.
    surveyForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevent the browser's default form submission (which would reload the page).

        formMessage.textContent = ''; // Clear any previous messages before a new submission.
        formMessage.classList.remove('success-message', 'server-error-message'); // Clear styling

        // Client-side Validation and Data Collection 
        // Get values from form fields and trim whitespace for text inputs.
        const fullName = document.getElementById('fullName').value.trim();
        const email = document.getElementById('email').value.trim();
        const dateOfBirth = document.getElementById('dateOfBirth').value; // YYYY-MM-DD format
        const contactNumber = document.getElementById('contactNumber').value.trim();

        // Get the value of the selected favourite food radio button.
        const favouriteFoodRadio = document.querySelector('input[name="favouriteFoods"]:checked');
        const favouriteFoods = favouriteFoodRadio ? favouriteFoodRadio.value : '';

        // Get the value of the selected rating for each activity.
        const watchMovies = document.querySelector('input[name="watchMovies"]:checked');
        const listenRadio = document.querySelector('input[name="listenRadio"]:checked');
        const eatOut = document.querySelector('input[name="eatOut"]:checked');
        const watchTV = document.querySelector('input[name="watchTV"]:checked');

        let isValid = true; // Flag to track overall form validity.
        const errors = []; // Array to collect validation error messages.

        // Client-side validation for required fields and format.
        if (!fullName) errors.push("Full Name is required.");
        if (!email) {
            errors.push("Email is required.");
        } else if (!/\S+@\S+\.\S+/.test(email)) { // Simple email regex validation.
            errors.push("Invalid email format.");
        }
        if (!dateOfBirth) errors.push("Date of Birth is required.");
        if (!contactNumber) {
            errors.push("Contact Number is required.");
        } else if (!/^[0-9]{10}$/.test(contactNumber)) { // Ensures 10 digits for contact number.
            errors.push("Contact Number must be 10 digits.");
        }
        if (!favouriteFoods) errors.push("A favourite food must be selected.");
        if (!watchMovies) errors.push("Rating for 'Watch Movies' is required.");
        if (!listenRadio) errors.push("Rating for 'Listen to Radio' is required.");
        if (!eatOut) errors.push("Rating for 'Eat Out' is required.");
        if (!watchTV) errors.push("Rating for 'Watch TV' is required.");

        // If any client-side validation errors exist, display them and stop.
        if (errors.length > 0) {
            formMessage.classList.add('server-error-message'); // Using error styling for validation issues.
            formMessage.innerHTML = errors.join('<br>'); // Display all errors, each on a new line.
            isValid = false;
            return; // Exit the submit handler.
        }

        // Construct the survey data object to be sent to the backend.
        const surveyData = {
            fullName: fullName,
            email: email,
            dateOfBirth: dateOfBirth, // Sent as YYYY-MM-DD string, Spring will parse it.
            contactNumber: contactNumber,
            favouriteFoods: favouriteFoods,
            eatOut: eatOut.value,
            watchMovies: watchMovies.value,
            watchTV: watchTV.value,
            listenRadio: listenRadio.value
        };

        //API Call to Submit Survey 
        try {
            const response = await fetch('http://localhost:8080/api/surveys', {
                method: 'POST', // HTTP POST request to submit data.
                headers: {
                    'Content-Type': 'application/json' // Indicate that the request body is JSON.
                },
                body: JSON.stringify(surveyData) // Convert JavaScript object to JSON string.
            });

            // Check if the response was successful (HTTP status 2xx).
            if (response.ok) {
                //  If the backend returns plain text on success, parse response as plain text.
                const successMessage = await response.text(); 

                formMessage.classList.remove('server-error-message');
                formMessage.classList.add('success-message');
                formMessage.textContent = successMessage; // Display the success message.
                surveyForm.reset(); // Clear the form fields after successful submission.
            } else {
                // If the response was not OK (e.g., 400 Bad Request, 500 Internal Server Error).
                const errorBody = await response.json(); // Attempt to parse error response as JSON.

                formMessage.classList.remove('success-message');
                formMessage.classList.add('server-error-message');

                // Check for a specific 'ageError' from the backend.
                if (errorBody.ageError) {
                    formMessage.textContent = errorBody.ageError;
                } else if (typeof errorBody === 'object' && errorBody !== null) {
                    // If it's a generic JSON error object (e.g., Spring validation errors).
                    const serverErrors = Object.values(errorBody).join('<br>'); // Join all error messages.
                    formMessage.innerHTML = `Submission failed:<br>${serverErrors}`;
                } else {
                    // Fallback for unexpected non-JSON error responses.
                    formMessage.textContent = 'Submission failed: An unknown error occurred on the server.';
                }
            }
        } catch (error) {
            // Catch network errors like erver not running or  CORS issues
            // or errors during parsing of the response (e.g., if response.json() fails).
            console.error('Error submitting survey:', error);
            formMessage.classList.remove('success-message');
            formMessage.classList.add('server-error-message');
            formMessage.textContent = 'Failed to connect to the server. Please ensure the backend server is running and accessible.';
        }
    });
});