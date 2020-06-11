function submitUser() {
	let userName = $("#userForm input[name='userName']").val();
	if (userName == '') { alert("请输入用户名!"); return false; }
	let password = $("#userForm input[name='password']").val();
	if (password == '') { alert("请输入密码!"); return false; }
	
}
function resetUserInfo() {
	$("#userForm input").each(function(){
		if ($(this)[0].type != 'radio') {
			$(this).val("");
		}
	})
	$("#userForm input[type='radio'][value='0']").prop("checked", true);
}