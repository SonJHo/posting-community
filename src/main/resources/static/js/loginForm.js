window.onload = function() {
    // 로그아웃 메시지가 있을 경우, 3초 후에 사라지게 함
    var message = document.getElementById("logoutMessage");
    if (message) {
        setTimeout(function() {
            message.classList.add('fade-out');
            setTimeout(function() {
                message.style.display = 'none';
            }, 500);
        }, 3000);
    }
};

// 아이디, 비밀번호 확인 태그 받기
let id = document.querySelector("#accountId");
let pwd = document.querySelector("#password");
let submitBtn = document.querySelector("#submit_btn");  // 버튼

// 각 태그에 변화 시 체크 조건 실행 (input 이벤트로 실시간 처리)
id.addEventListener('input', checkFormValidity);
pwd.addEventListener('input', checkFormValidity);

// 폼 유효성 검사 함수
function checkFormValidity() {
    if (id.value.length >= 7 && pwd.value.length >= 7) {
        submitBtn.disabled = false;
        submitBtn.style.cursor = 'pointer';  // 활성화된 상태로 변경
        submitBtn.style.opacity = 1;      // 활성화된 상태로 변경
    } else {
        submitBtn.disabled = true;
        submitBtn.style.cursor = 'none'; // 비활성화 상태로 변경
        submitBtn.style.opacity = 0.7;   // 비활성화 상태로 변경
    }
}