let editMode = false;
let editTarget = null;

document.getElementById("addButton").addEventListener("click", function () {
    openPopup("할 일 추가", "");
    editMode = false;
    editTarget = null;
});

document.getElementById("cancelButton").addEventListener("click", function () {
    closePopup();
});

document.getElementById("applyButton").addEventListener("click", function () {
    const noteText = document.getElementById("noteInput").value.trim();

    if (noteText !== "") {
        if (editMode && editTarget) {
            // 수정 모드일 때
            editTarget.textContent = noteText;
        } else {
            // 추가 모드일 때
            addNoteToList(noteText);
        }
        closePopup();
    }
});

function openPopup(title, text) {
    document.getElementById("noteInput").value = text;
    document.getElementById("modalBackground").style.display = "flex";
}

function closePopup() {
    document.getElementById("modalBackground").style.display = "none";
    document.getElementById("noteInput").value = "";
}

function checkEmptyList() {
    const listContainer = document.getElementById("listContainer");
    const emptyMessage = document.getElementById("emptyMessage");
    emptyMessage.style.display = listContainer.querySelectorAll(".note").length === 0 ? "block" : "none";
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

    const editButton = document.createElement("span");
    editButton.classList.add("edit-icon");
    editButton.innerHTML = "✏️";
    editButton.addEventListener("click", function () {
        editMode = true;
        editTarget = label;
        openPopup("할 일 수정", label.textContent);
    });

    const trashIcon = document.createElement("span");
    trashIcon.classList.add("trash-icon");
    trashIcon.innerHTML = "🗑️";
    trashIcon.addEventListener("click", function () {
        newNote.remove();
        checkEmptyList();
    });

    newNote.appendChild(checkbox);
    newNote.appendChild(label);
    newNote.appendChild(editButton);
    newNote.appendChild(trashIcon);

    listContainer.appendChild(newNote);
    checkEmptyList();
}

checkEmptyList();
