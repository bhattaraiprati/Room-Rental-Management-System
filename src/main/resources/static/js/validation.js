

document
  .getElementById("signupForm")
  .addEventListener("submit", function (event) {
    var email = document.getElementById("email").value;
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    // Clear previous error messages
    clearErrors();
    clearInputBoxClasses();

    // Email validation
    if (!isValidEmail(email)) {
      showError("emailError", "Please enter a valid email address");
      event.preventDefault(); // Prevent form submission
    }

    // Name validation (optional)
    if (name.trim() === "") {
      showError("nameError", "Please enter your name");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidName(name)) {
      showError("nameError", "Name should not contain numbers");
      event.preventDefault(); // Prevent form submission
    }

    // Password validation
    if (password.trim() === "") {
      showError("passwordError", "Please enter your password");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidPassword(password)) {
      showError("passwordError", "Enter valid password");
      event.preventDefault(); // Prevent form submission
    } else if (password !== confirmPassword) {
      showError("passwordError", "Passwords do not match");
      event.preventDefault(); // Prevent form submission
    }  
  });

// Email validation function using regular expression
function isValidEmail(email) {
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

function isValidPassword(password) {
  var passwordRegex =
    /^(?=.*[A-Z])(?=.*[!@#$%^&*()_+}{"':;?/>.<,])(?=.*[a-z]).{6,}$/;
  return passwordRegex.test(password);
}

// Name validation function
function isValidName(name) {
  var nameRegex = /^[A-Za-z ]+$/; // Only allow letters and spaces
  return nameRegex.test(name);
}

// Function to display error message
function showError(elementId, errorMessage) {
  var errorElement = document.getElementById(elementId);
  errorElement.textContent = errorMessage;
}

// Function to clear error messages
function clearErrors() {
  var errorElements = document.querySelectorAll(".error-message");
  errorElements.forEach(function (element) {
    element.textContent = "";
  });
}

function clearInputBoxClasses() {
  var inputBoxElements = document.querySelectorAll(".input_box");
  inputBoxElements.forEach(function (element) {
    element.classList.remove("has-error");
  });
}
