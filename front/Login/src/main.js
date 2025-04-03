document.addEventListener("DOMContentLoaded", () => {
    const createAccountBtn = document.getElementById("create-account-btn");
    const forgotPasswordBtn = document.getElementById("forgot-password-btn");

    if (createAccountBtn) {
        createAccountBtn.addEventListener("click", () => {
            window.location.href = "createAccountPage.html"; // 파일명 확인!
        });
    }

    if (forgotPasswordBtn) {
        forgotPasswordBtn.addEventListener("click", () => {
            alert("Password reset feature coming soon!");
        });
    }
});
