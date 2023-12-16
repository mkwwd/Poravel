//// 현재 로그인 여부 체크
//isLogin = function() {
//    let id = sessionStorage.getItem("memberId");
//    let pw = sessionStorage.getItem("password");
//
//    // 로그아웃 되어있다면
//    if (id == null || pw == null) return false;
//    
//    // 로그인 되어있다면
//    else return true;
//}
//
//// 로그인 여부에 따른 nav 변경
//if(!isLogin()){
//    document.querySelector("#loginInfo").style.display = 'none';
//    document.querySelector("#logoutInfo").style.display = '';
//}
//else {
//    document.querySelector("#loginInfo").style.display = '';
//    document.querySelector("#logoutInfo").style.display = 'none';
//}
//
//// 로그아웃 처리 : 세션에 저장된 로그인 정보 지우기, nav 정보 변경(로그인, 회원가입 보이게)
//logOut = function() {
//    if (!isLogin()) return; // 로그인 되어있지 않다면 -> 종료
//
//    // 세션에서 로그인정보(아이디, 비밀번호) 제거
//    sessionStorage.removeItem("memberId");
//    sessionStorage.removeItem("password");
//    
//    alert('로그아웃 성공');
//
//    return location.href = '/index.html';
//}

// // 로그인 처리 :
// login = functinon() {

// }
