// 팝업 열기
document.getElementById("addButton").addEventListener("click", function () {
    const modalBackground = document.getElementById("modalBackground");
    const popupIframe = document.getElementById("popupIframe");

    // setaddpage/index.html을 iframe에 로드
    popupIframe.src = "../setaddpage/index.html";
    // 팝업 배경 표시
    modalBackground.style.display = "flex";
});

// 팝업 닫기 (배경 클릭 시)
document.getElementById("modalBackground").addEventListener("click", function (event) {
    if (event.target === this) {
        const modalBackground = document.getElementById("modalBackground");
        const popupIframe = document.getElementById("popupIframe");

        // iframe 내용 초기화
        popupIframe.src = "about:blank";
        // 팝업 배경 숨기기
        modalBackground.style.display = "none";
    }
});

// iframe 내부의 CANCEL 버튼 이벤트 처리
window.addEventListener("message", function (event) {
    if (event.data === "closePopup") {
        const modalBackground = document.getElementById("modalBackground");
        const popupIframe = document.getElementById("popupIframe");

        // iframe 내용 초기화
        popupIframe.src = "about:blank";
        // 팝업 배경 숨기기
        modalBackground.style.display = "none";
    }
});

// 휴지통 아이콘 클릭 시 항목 삭제
document.querySelector('.trash-icon').addEventListener('click', function() {
    const noteId = this.getAttribute('data-note-id'); // 삭제할 항목의 ID
    const noteElement = this.closest('.note'); // 가장 가까운 .note 요소

    if (noteElement) {
        noteElement.remove(); // 항목 삭제
        console.log(`Note #${noteId} deleted.`);

        // 모든 체크박스가 삭제되었는지 확인
        checkIfAllNotesDeleted();
    }
});


// 모든 체크박스가 삭제되었는지 확인하는 함수
function checkIfAllNotesDeleted() {
    const notes = document.querySelectorAll('.note'); // 모든 .note 요소
    const clearImage = document.getElementById('clearImage'); // clear.svg 이미지

    if (notes.length === 0) {
        clearImage.style.display = 'block'; // 모든 항목이 삭제되면 이미지 표시
    } else {
        clearImage.style.display = 'none'; // 항목이 남아있으면 이미지 숨김
    }
}

