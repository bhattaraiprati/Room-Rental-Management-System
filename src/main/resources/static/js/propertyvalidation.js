	document.getElementById("addproperty")
  .addEventListener("submit", function (event) {
    var city = document.getElementById("city").value;
    var place = document.getElementById("place").value;
    var propertyType = document.getElementById("propertyType").value;
    var price = document.getElementById("price").value;
    var bedroom = document.getElementById("bedroom").value;
    var contactname = document.getElementById("contactname").value;
    var email = document.getElementById("email").value;
    var mobilenumber = document.getElementById("number").value;
   
    var desc = document.getElementById("description").value;

    // Clear previous error messages
    clearErrors();
    clearInputBoxClasses();

    // Email validation
    if (!isValidEmail(email)) {
      showError("emailError", "Please enter a valid email address");
      event.preventDefault(); // Prevent form submission
    }
    if (bedroom.trim() === "") {
      showError("bedError", "Please choose one option");
      event.preventDefault(); // Prevent form submission
    }
    // Name validation (optional)
    if (city.trim() === "") {
      showError("cityError", "Please enter city name");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidCity(city)) {
      showError("cityError", "city should not contain numbers");
      event.preventDefault(); // Prevent form submission
    }
    if (place.trim() === "") {
      showError("placeError", "Please enter place name");
      event.preventDefault(); // Prevent form submission
    }
    if (propertyType.trim() === "") {
      showError("propertyError", "Please choose one option");
      event.preventDefault(); // Prevent form submission
    }
    if (!isValidPlace(desc)) {
      showError("descError", "Description should not empty");
      event.preventDefault(); // Prevent form submission
    }

    if (price.trim() === "") {
      showError("PriceError", "Please enter Price");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidPrice(price)) {
      showError("PriceError", "Price should not contain alphabet");
      event.preventDefault(); // Prevent form submission
    }

    if (contactname.trim() === "") {
      showError("contactError", "Please enter your name");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidContactName(contactname)) {
      showError("contactError", "Name should not contain Number");
      event.preventDefault(); // Prevent form submission
    }

    if (mobilenumber.trim() === "") {
      showError("numberError", "Please enter your number");
      event.preventDefault(); // Prevent form submission
    } else if (!isValidNumber(mobilenumber)) {
      showError("numberError", "Number should not contain alphabet");
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

// Name validation function
function isValidCity(city) {
  var nameRegex = /^[A-Za-z ]+$/; // Only allow letters and spaces
  return nameRegex.test(city);
}
function isValidPlace(place) {
  var nameRegex = /^[A-Za-z0-9 ]+$/;
  // Only allow letters and spaces
  return nameRegex.test(place);
}
function isValidPrice(price) {
  var numberRegex = /^[0-9]+$/;
  // Only allow letters and spaces
  return nameRegex.test(price);
}

function isValidContactName(contactname) {
  var nameRegex = /^[A-Za-z ]+$/;
  // Only allow letters and spaces
  return nameRegex.test(contactname);
}

function isValidNumber(mobilenumber) {
  var numberRegex = /^[0-9]+$/;
  // Only allow letters and spaces
  return nameRegex.test(mobilenumber);
}

// Email validation function using regular expression
function isValidEmail(email) {
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
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
