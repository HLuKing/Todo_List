document.getElementById("addButton").addEventListener("click", function () {
    document.getElementById("modalBackground").style.display = "flex";
});


document.getElementById("cancelButton").addEventListener("click", function () {
    closePopup();
});


document.getElementById("applyButton").addEventListener("click", function () {
    const noteText = document.getElementById("noteInput").value.trim();

    if (noteText !== "") {
        addNoteToList(noteText);
        closePopup();
    }
});


function closePopup() {
    document.getElementById("modalBackground").style.display = "none";
    document.getElementById("noteInput").value = ""; // 입력 필드 초기화
}


function checkEmptyList() {
    const listContainer = document.getElementById("listContainer");
    const emptyMessage = document.getElementById("emptyMessage");


    if (listContainer.children.length <= 1) {
        emptyMessage.style.display = "block";
    } else {
        emptyMessage.style.display = "none";
    }
}


function addNoteToList(noteText) {
    const listContainer = document.getElementById("listContainer");

    const newNote = document.createElement("div");
    newNote.classList.add("note");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";

    const label = document.createElement("label");
    label.classList.add("note-label");
    label.textContent = noteText;


    checkbox.addEventListener("change", function () {
        label.classList.toggle("checked", checkbox.checked);
    });

    const trashIcon = document.createElement("span");
    trashIcon.classList.add("trash-icon");
    trashIcon.innerHTML = "🗑️"; // 휴지통 이모지


    trashIcon.addEventListener("click", function () {
        newNote.remove();
        checkEmptyList();
    });

    newNote.appendChild(checkbox);
    newNote.appendChild(label);
    newNote.appendChild(trashIcon);

    listContainer.appendChild(newNote);

    checkEmptyList();
}
if (!window.location.pathname.includes("listMain.html")) {
    window.location.href = "404.html";
}


checkEmptyList();
