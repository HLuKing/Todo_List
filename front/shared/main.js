document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("jwtToken");
    const path = window.location.pathname;

    // 로그인 처리
    if (path.includes("loginPage.html")) {
        const loginForm = document.querySelector("form");

        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const accountId = document.getElementById("UserAccountID").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("http://13.210.183.61:8080/api/user/login?accountId=" + accountId + "&password=" + password, {
                    method: "POST"
                });


                if (!response.ok) throw new Error("Login failed");

                const token = await response.text();
                localStorage.setItem("jwtToken", token);

                alert("로그인 성공!");
                window.location.href = "../../Listpage/src/listMain.html";
            } catch (err) {
                alert("로그인 실패: " + err.message);
            }

        });
        const createAccountBtn = document.getElementById("create-account-btn");
        if (createAccountBtn) {
            createAccountBtn.addEventListener("click", () => {
                window.location.href = "createAccountPage.html";
            });
        }
    }

    // 리스트 페이지 동작
    if (path.includes("listMain.html")) {
        fetchTodos();

        document.getElementById("applyButton").addEventListener("click", () => {
            const input = document.getElementById("noteInput").value;
            if (input.trim() !== "") {
                createTodo(input, "기본 내용");
            }
        });

        document.getElementById("cancelButton").addEventListener("click", () => {
            document.getElementById("modalBackground").style.display = "none";
        });

        document.getElementById("addButton").addEventListener("click", () => {
            document.getElementById("noteInput").value = "";
            document.getElementById("modalBackground").style.display = "flex";
        });
    }

    // -------- API 연동 함수들 --------
    async function fetchTodos() {
        const response = await fetch("http://13.210.183.61:8080/api/lists", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        const todos = await response.json();
        const listContainer = document.getElementById("listContainer");

        if (todos.length === 0) {
            document.getElementById("emptyMessage").style.display = "block";
        } else {
            document.getElementById("emptyMessage").style.display = "none";
            todos.forEach(todo => {
                const note = document.createElement("div");
                note.className = "note";
                note.innerHTML = `
                    <span class="note-label">${todo.title}</span>
                    <span class="edit-icon">✏️</span>
                    <span class="trash-icon">🗑️</span>
                `;
                listContainer.appendChild(note);

                note.querySelector(".edit-icon").addEventListener("click", () => {
                    const newTitle = prompt("새 제목", todo.title);
                    const newContent = prompt("새 내용", todo.content);
                    updateTodo(todo.title, newTitle, newContent);
                });

                note.querySelector(".trash-icon").addEventListener("click", () => {
                    deleteTodo(todo.title, todo.content);
                });
            });
        }
    }

    async function createTodo(title, content) {
        await fetch("http://13.210.183.61:8080/api/lists", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({ title, content })
        });
        location.reload();
    }

    async function updateTodo(oldTitle, newTitle, newContent) {
        await fetch("http://13.210.183.61:8080/api/lists", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({
                title: oldTitle,
                newTitle,
                newContent
            })
        });
        location.reload();
    }
    if (path.includes("createAccountPage.html")) {
        const signupForm = document.getElementById("signup-form");

        signupForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const name = document.getElementById("name").value;
            const accountId = document.getElementById("UserAccountID").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("http://13.210.183.61:8080/api/user", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ name, accountId, password })
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error("회원가입 실패: " + errorText);
                }

                alert("회원가입 성공! 로그인 페이지로 이동합니다.");
                window.location.href = "loginPage.html";
            } catch (err) {
                alert("에러: " + err.message);
            }
        });
    }


    async function deleteTodo(title, content) {
        await fetch("http://13.210.183.61:8080/api/lists", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({ title, content })
        });
        location.reload();
    }
});

