document.addEventListener("DOMContentLoaded", () => {
    const path = window.location.pathname;
    const token = localStorage.getItem("jwtToken");

    // 로그인 페이지
    if (path.includes("loginPage.html")) {
        const loginForm = document.querySelector("form");
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const accountId = document.getElementById("UserAccountID").value;
            const password = document.getElementById("password").value;
            try {
                const response = await fetch("http://localhost:8080/api/user/login?accountId=" + accountId + "&password=" + password, {
                    method: "POST"
                });
                if (!response.ok) throw new Error("Login failed");
                const token = await response.text();
                localStorage.setItem("jwtToken", token);
                alert("로그인 성공!");
                window.location.href = "listMain.html";
            } catch (err) {
                alert("에러: " + err.message);
            }
        });

        const createAccountBtn = document.getElementById("create-account-btn");
        if (createAccountBtn) {
            createAccountBtn.addEventListener("click", () => {
                window.location.href = "createAccountPage.html";
            });
        }
    }

    // 회원가입 페이지
    if (path.includes("createAccountPage.html")) {
        const form = document.getElementById("signup-form");
        form.addEventListener("submit", async (e) => {
            e.preventDefault();
            const name = document.getElementById("name").value;
            const accountId = document.getElementById("UserAccountID").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("http://localhost:8080/api/user", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ name, accountId, password })
                });
                if (!response.ok) throw new Error("회원가입 실패");
                alert("회원가입 완료! 로그인 페이지로 이동합니다.");
                window.location.href = "loginPage.html";
            } catch (err) {
                alert("에러: " + err.message);
            }
        });
    }

    // 리스트 페이지
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

    async function fetchTodos() {
        const response = await fetch("http://localhost:8080/api/lists", {
            headers: { Authorization: `Bearer ${token}` }
        });
        const todos = await response.json();
        const container = document.getElementById("listContainer");
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
                container.appendChild(note);

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
        await fetch("http://localhost:8080/api/lists", {
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
        await fetch("http://localhost:8080/api/lists", {
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

    async function deleteTodo(title, content) {
        await fetch("http://localhost:8080/api/lists", {
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
