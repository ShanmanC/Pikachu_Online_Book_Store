function resetValidate(){
	var ok = true;
	///check if the input user name is valid
	var p = document.getElementById("password_uid").value;
	var reg_name = /^[a-zA-Z0-9_-]+$/;
	if (p ===""){
		alert("user name can not be empty!");
		ok = false;
	}else if(!p.match(reg_name)) {
		alert("User name can not be special character");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the input email are not validate
	var reg_email = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	p = document.getElementById("password_email").value;
	if (p ===""){
		alert("Please input the email address!");
		ok = false;
	}else if (!reg_email.test(p)){
		alert("please input valide email address");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the user's  first name and last name is valid
	var p_fname = document.getElementById("password_fname").value;
	var reg_fname = /^[a-zA-Z]+$/;
	if (p_fname ===""){
		alert("First name can not be empty!");
		ok = false;
	}else if(!p_fname.match(reg_fname)) {
		alert("First name can ONLY be Characters!");
		ok = false;
	}
	if (!ok) return ok;
	
	//validate_lastname();
	var p_lname = document.getElementById("password_lname").value;
	var reg_name = /^[a-zA-Z]+$/;
	if (p_lname ===""){
		alert("Last name can not be empty!");
		ok = false;
	}else if(!p_lname.match(reg_name)) {
		alert("Last name can ONLY be Characters!");
		ok = false;
	}
	if (!ok) return ok;
	
	return ok;
}

function password(){
	var ok = true;
	//check if the confirm password is valid at least be 6 digit
	var reg_pass = /^(?:(?=.*[0-9].*)(?=.*[A-Za-z].*)(?=.*[,\.#%'\+\*\-:;^_`].*))[,\.#%'\+\*\-:;^_`0-9A-Za-z]{6,16}$/;
	var p = document.getElementById("reset_pwd1").value;
	if (p ===""){
		alert("Please input the password!");
		ok = false;
	}else if (!reg_pass.test(p)){
		alert("A strong password is required. Strong passwords are 6 to 16 charactoers and must combine letters, numbers, and symbos.");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the confirm password is as the same as the previous password
	var p_conf = document.getElementById("reset_pwd2").value;
	if (p_conf ===""){
		alert("Please input the comfirm password!");
		ok = false;
	}else if (p_conf != p){
		alert("Password is not the same!");
		ok = false;
	}
	if (!ok) return ok;
	
	return ok;
}