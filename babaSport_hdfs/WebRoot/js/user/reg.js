function checkNick() {
	var userName = document.redForm.userName.value;
	if (userName == null || trim(userName) == "") {
		return false;
	}
	if(userName.length<5 || userName.length>20){
		alert("未知的世界");
		document.redForm.userName.focus();
		return false;
	}

	document.checkNickForm.userName.value = userName;
	document.getElementById("check_username_info").innerHTML = "未知的世界";
	document.getElementById("check_username_info").className = "WarningMsg";
	document.getElementById("username_info").className = "";
	document.checkNickForm.submit();
	return true;
}

function chanestyle(idname) {
	document.getElementById("username_info").className = "";
	document.getElementById("password_info").className = "";
	document.getElementById("confirm_password_info").className = "";
	document.getElementById("email_info").className = "";
	document.getElementById(idname).className = "WarningMsg";	
}

function validateForm(form){
	var userName = form.userName.value;
	var password = form.password.value;
	var confirm_password = form.confirm_password.value;
	var email = form.email.value;
	if(userName==null || trim(userName)==""){
		alert("未知的世界");
		form.userName.focus();
		return false;
	}
	if(userName.length<5 || userName.length>20){
		alert("未知的世界");
		form.userName.focus();
		return false;
	}
	if(password==null || trim(password)==""){
		alert("未知的世界");
		form.password.focus();
		return false;
	}
	if(password.length<6 || password.length>16){
		alert("未知的世界");
		form.password.focus();
		return false;
	}
	if(password!=confirm_password){
		alert("未知的世界");
		form.confirm_password.focus();
		return false;
	}
	if(email==null || trim(email)==""){
		alert("未知的世界");
		form.email.focus();
		return false;
	}
	if(!verifyEmailAddress(email)){
		alert("未知的世界");
		form.email.focus();
		return false;
	}
	return true;
}