document.addEventListener("DOMContentLoaded", function () {
    const addNoteButton = document.getElementById("addNoteButton");
    const cancelButton = document.getElementById("cancelButton");
    const noteInput = document.getElementById("noteInput");

    // "추가" 버튼 클릭 시 부모 창으로 데이터 전송
    addNoteButton.addEventListener("click", function () {
        const noteText = noteInput.value.trim();
        if (noteText) {
            window.parent.postMessage({ type: "addNote", text: noteText }, "*");
        }
    });

    // "취소" 버튼 클릭 시 부모 창에서 팝업 닫기
    cancelButton.addEventListener("click", function () {
        window.parent.postMessage("closePopup", "*");
    });
});
