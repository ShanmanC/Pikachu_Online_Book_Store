function validate_register(){
	var ok = true;
	
	
	///check if the input user name is valid
	p = document.getElementById("register_username").value;
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
	p = document.getElementById("register_email").value;
	if (p ===""){
		alert("Please input the email address!");
		ok = false;
	}else if (!reg_email.test(p)){
		alert("please input valide email address");
		ok = false;
	}
	if (!ok) return ok;
	
	
	//check if the confirm password is valid at least be 6 digit
	var reg_pass = /^(?:(?=.*[0-9].*)(?=.*[A-Za-z].*)(?=.*[,\.#%'\+\*\-:;^_`].*))[,\.#%'\+\*\-:;^_`0-9A-Za-z]{6,16}$/;
	p = document.getElementById("register_password").value;
	if (p ===""){
		alert("Please input the password!");
		ok = false;
	}else if (!reg_pass.test(p)){
		alert("A strong password is required. Strong passwords are 6 to 16 charactoers and must combine letters, numbers, and symbos.");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the confirm password is as the same as the previous password
	var p_conf = document.getElementById("register_passwordcfm").value;
	p = document.getElementById("register_password").value;
	if (p_conf ===""){
		alert("Please input the comfirm password!");
		ok = false;
	}else if (p_conf != p){
		alert("Password is not the same!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check if the user's  first name and last name is valid
	var p_fname = document.getElementById("sfname").value;
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
	var p_lname = document.getElementById("slname").value;
	var reg_name = /^[a-zA-Z]+$/;
	if (p_lname ===""){
		alert("Last name can not be empty!");
		ok = false;
	}else if(!p_lname.match(reg_name)) {
		alert("Last name can ONLY be Characters!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check phone number is valid
	var p_phone = document.getElementById("sphone").value;
	var reg_phone = /^\d{10}$/;
	if(p_phone ===""){
		alert("Phone number can not be empty!");
		ok = false;
	}else if(!p_phone.match(reg_phone)) {
		alert("Phone number can only have 10 digital number");
		ok = false;
	}
	if (!ok) return ok;
	
	//check street is valid
	var p_str = document.getElementById("sadrs").value;
	if(p_str ===""){
		alert("Please insert the street information!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check state is valid
	var p_sta = document.getElementById("sstate").value;
	if(p_sta ==="0"){
		alert("Please Select state/province!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check country is valid
	var p_con = document.getElementById("scountry").value;
	if(p_con ==="0"){
		alert("Please Select country!");
		ok = false;
	}
	if (!ok) return ok;
	
	//check zip code is valid
	var p_zip = document.getElementById("szip").value;
	var regex = new RegExp(/^[ABCEGHJKLMNPRSTVXY]\d[ABCEGHJKLMNPRSTVWXYZ]( )?\d[ABCEGHJKLMNPRSTVWXYZ]\d$/i);
	if(p_zip ===""){
		alert("Please enter your zip code!");
		ok = false;
	}else if (!regex.test(p_zip)){
		alert("please input valid zip code");
		ok = false;
	}
	if (!ok) return ok;
	
	return ok
}





