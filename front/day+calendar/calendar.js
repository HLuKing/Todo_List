let selectedDate = new Date().getDate();
let selectedMonth = new Date().getMonth();
let selectedYear = new Date().getFullYear();

function changeMonth(direction) {
    selectedMonth += direction;
    if (selectedMonth < 0) {
        selectedMonth = 11;
        selectedYear--;
    } else if (selectedMonth > 11) {
        selectedMonth = 0;
        selectedYear++;
    }
    generateCalendar(selectedMonth, selectedYear);
}

function generateCalendar(month, year) {
    const monthNames = ["January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"];
    document.getElementById("monthYear").innerText = `${monthNames[month]} ${year}`;

    let firstDay = new Date(year, month, 1).getDay();
    let daysInMonth = new Date(year, month + 1, 0).getDate();
    let calendarBody = document.getElementById("calendarBody");
    calendarBody.innerHTML = "";
    let row = document.createElement("tr");

    for (let i = 0; i < firstDay; i++) {
        row.appendChild(document.createElement("td"));
    }

    for (let day = 1; day <= daysInMonth; day++) {
        let cell = document.createElement("td");
        cell.innerText = day;

        cell.onclick = () => {
            const m = (month + 1).toString().padStart(2, '0');
            const d = day.toString().padStart(2, '0');
            window.location.href = `day.html?date=${year}-${m}-${d}`;
        };

        if (day === selectedDate && month === new Date().getMonth() && year === new Date().getFullYear()) {
            cell.classList.add("selected");
        }

        row.appendChild(cell);
        if ((firstDay + day) % 7 === 0) {
            calendarBody.appendChild(row);
            row = document.createElement("tr");
        }
    }

    if (row.children.length > 0) calendarBody.appendChild(row);
}

document.addEventListener("DOMContentLoaded", () => {
    generateCalendar(selectedMonth, selectedYear);
});
