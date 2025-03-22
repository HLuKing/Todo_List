const urlParams = new URLSearchParams(window.location.search);
const date = urlParams.get("date");

document.getElementById("selectedDay").innerText = date.split("-")[2]; // 날짜 숫자만

fetch(`http://localhost:8080/api/todos/${date}`)
    .then(res => res.json())
    .then(data => {
        const ul = document.getElementById("notes");
        ul.innerHTML = "";
        data.forEach(todo => {
            const li = document.createElement("li");
            li.innerHTML = '<input type="checkbox"> ' + todo.task;
            ul.appendChild(li);
        });
    });
