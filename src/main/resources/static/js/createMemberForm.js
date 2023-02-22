let index = {
    mailCheck: function () {
        const eamil = $('#email').val(); // 이메일 주소값 얻어오기!
        console.log('완성된 이메일 : ' + eamil); // 이메일 오는지 확인
        const checkInput = $('.mail-check-input') // 인증번호 입력하는곳

        $.ajax({
            type: 'GET',
            url: "/members/createMemberForm?email=" + eamil, // GET방식이라 Url 뒤에 email을 뭍힐수있다.
            success: function (data) {
                console.log("data : " + data);
                checkInput.attr('disabled', false);
                code = data;
                alert('인증번호가 전송되었습니다.')
            }
        }); // end ajax
    }
} // end send eamil

// 인증번호 비교
// blur -> focus가 벗어나는 경우 발생
$('.mail-check-input').blur(function () {
    const inputCode = $(this).val();
    const $resultMsg = $('#mail-check-warn');

    if (inputCode === code) {
        $resultMsg.html('인증번호가 일치합니다.');
        $resultMsg.css('color', 'green');
        $('#mail-Check-Btn').attr('disabled', true);
        $('#email').attr('readonly', true);
    } else {
        $resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
        $resultMsg.css('color', 'red');
    }
});