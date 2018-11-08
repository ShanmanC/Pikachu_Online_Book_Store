function validate(){
	var ok = true;
	var p = document.getElementById("forget_password_button").value;
	if (p ==="true") return true;
	
	p = document.getElementById("login_register").value;
	if (p ==="true") return true;
	
	///check if the input user name is valid
	p = document.getElementById("login_userid").value;
	var reg_name = /^[a-zA-Z0-9_-]+$/;
	if (p ===""){
		alert("user name can not be empty!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the password is valid
	p = document.getElementById("login_password").value;
	if (p ===""){
		alert("Please input the password!");
		ok = false;
	}
	if (!ok) return ok;
	
	
	return ok;
}