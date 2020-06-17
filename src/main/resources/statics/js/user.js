function submitUser() {
	let userName = $("#userForm input[name='userName']").val();
	if (userName == '') { alert("请输入用户名!"); return false; }
	let userNamePatrn = /^[a-zA-Z0-9_]{4,16}$/;  
	if (!userNamePatrn.exec(userName)){ alert("用户名只能输入4到16位（字母，数字，下划线）!"); return false; }
	let password = $("#userForm input[name='password']").val();
	if (password == '') { alert("请输入密码!"); return false; }
	let passwordPatrn = /^(\w){6,20}$/;  
	if (!passwordPatrn.exec(password)){ alert("密码只能输入6-20个字母、数字、下划线!"); return false;}
	let userType = $("#userForm input[name='userType']:checked").val();
	$.ajax({
		url : "/addUser",
		type : 'POST',
		data : {
			userName : userName,
			password : password,
			userType : userType
		},
		dataType : 'JSON',
		success : function(result) {
			if (result.code == 1) {
				$('#addOrUpdateUserModal').modal('hide');
				$('.modal-backdrop').remove();
				resetUserInfo();
				window.location.reload(); 
			}else {
				alert(result.msg);
			}
		}
	});
}
function resetUserInfo() {
	$("#userForm input").each(function(){
		if ($(this)[0].type != 'radio') {
			$(this).val("");
		}
	})
	$("#userForm input[type='radio'][value='0']").prop("checked", true);
}
function deleteUser(userId) {
	if (confirm("确定删除吗?") == true){ 
		$.ajax({
			url : "/deleteUserById",
			type : 'POST',
			data : {
				userId : userId,
			},
			dataType : 'JSON',
			success : function(result) {
				if (result.code == 1) {
					window.location.reload(); 
				}else {
					alert(result.msg);
				}
			}
		});
	}
}
function checkUser(userId) {
	$('#checkUserModal').modal('show');
	$("#checkUserForm input[name='userId']").val(userId);
}
function submitCheckUser() {
	let userId = $("#checkUserForm input[name='userId']").val();
	let checkResult = $("#checkUserForm input[name='userState']:checked").val();
	$.ajax({
		url : "/checkUser",
		type : 'POST',
		data : {
			userId : userId,
			userState : checkResult
		},
		dataType : 'JSON',
		success : function(result) {
			if (result.code == 1) {
				$("#checkUserForm input[type='radio'][value='1']").prop("checked", true);
				window.location.reload(); 
			}else {
				alert(result.msg);
			}
		}
	});
}
function resetPwd(userId) {
	if (confirm("确定要重置密码吗?") == true){ 
		$.ajax({
			url : "/resetPwd",
			type : 'POST',
			data : {
				userId : userId,
			},
			dataType : 'JSON',
			success : function(result) {
				if (result.code == 1) {
					alert("修改成功, 初始密码为【666666】！");
				}else {
					alert(result.msg);
				}
			}
		});
	}
}