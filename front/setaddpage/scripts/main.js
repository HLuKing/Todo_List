// CANCEL 버튼 클릭 시 부모 창에 메시지 전송
document.getElementById('cancelButton').addEventListener('click', function() {
  window.parent.postMessage('closePopup', '*');
});
