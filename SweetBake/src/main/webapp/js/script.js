function updatePrice() {
    var total = 0;
    var inputs = document.querySelectorAll("input[data-price]");

    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].checked) {
            total = total + Number(inputs[i].dataset.price);
        }
    }

    var quantity = Number(document.getElementById("quantity").value);
    document.getElementById("total").textContent = total * quantity;
}

function checkRegister() {
    var phone = document.getElementById("phone").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var error = document.getElementById("formError");

    if (phone.length != 10 || phone.substring(0, 2) != "05" || isNaN(phone)) {
        error.textContent = "Phone number must start with 05 and have 10 digits.";
        return false;
    }
    if (password.length < 8) {
        error.textContent = "Password must be at least 8 characters.";
        return false;
    }
    if (password != confirmPassword) {
        error.textContent = "The two passwords are not the same.";
        return false;
    }
    return true;
}

if (document.getElementById("total")) {
    updatePrice();
}
