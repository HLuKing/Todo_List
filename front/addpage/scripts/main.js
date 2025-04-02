// 팝업 열기
document.getElementById("addButton").addEventListener("click", function () {
    const modalBackground = document.getElementById("modalBackground");
    const popupIframe = document.getElementById("popupIframe");

    // setaddpage/index.html을 iframe에 로드
    popupIframe.src = "../setaddpage/index.html";
    modalBackground.style.display = "flex";
});

// 팝업 닫기 (배경 클릭 시)
document.getElementById("modalBackground").addEventListener("click", function (event) {
    if (event.target === this) {
        closePopup();
    }
});

// iframe 내부에서 메시지 받기 (Apply 버튼 클릭 시)
window.addEventListener("message", function (event) {
    if (event.data.action === "addNote") {
        addNoteToList(event.data.noteText); // 리스트에 추가
        closePopup(); // 팝업 닫기
    } else if (event.data === "closePopup") {
        closePopup();
    }
});

// 팝업 닫는 함수
function closePopup() {
    const modalBackground = document.getElementById("modalBackground");
    const popupIframe = document.getElementById("popupIframe");

    popupIframe.src = "about:blank"; // iframe 초기화
    modalBackground.style.display = "none";
}

// 리스트에 새로운 할 일 추가
function addNoteToList(noteText) {
    const listContainer = document.getElementById("listContainer"); // 리스트 컨테이너 가져오기

    const newNote = document.createElement("div");
    newNote.classList.add("note");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.classList.add("note-checkbox");

    const label = document.createElement("label");
    label.classList.add("note-label");
    label.textContent = noteText;

    const optionsDiv = document.createElement("div");
    optionsDiv.classList.add("options");

    const trashIcon = document.createElement("img");
    trashIcon.src = "img/TrashIcon.svg";
    trashIcon.classList.add("img-2", "trash-icon");

    // 삭제 기능 추가
    trashIcon.addEventListener("click", function () {
        newNote.remove();
        checkIfAllNotesDeleted();
    });

    optionsDiv.appendChild(trashIcon);
    newNote.appendChild(checkbox);
    newNote.appendChild(label);
    newNote.appendChild(optionsDiv);

    listContainer.appendChild(newNote); // 리스트에 추가!

    checkIfAllNotesDeleted();
}
