function validate() {
	var ok = true;

	var p_check = document.getElementById("yesship").checked;
	if (p_check) {
		// check if the user's first name and last name is valid
		var p_fname = document.getElementById("sfname").value;
		var reg_fname = /^[a-zA-Z]+$/;
		if (p_fname === "") {
			alert("First name for shiping address can not be empty!");
			ok = false;
		} else if (!p_fname.match(reg_fname)) {
			alert("First name for shiping address can ONLY be Characters!");
			ok = false;
		}
		if (!ok)
			return ok;

		// validate_lastname();
		var p_lname = document.getElementById("slname").value;
		var reg_name = /^[a-zA-Z]+$/;
		if (p_lname === "") {
			alert("Last name for shiping address can not be empty!");
			ok = false;
		} else if (!p_lname.match(reg_name)) {
			alert("Last name for shiping address can ONLY be Characters!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check phone number is valid
		var p_phone = document.getElementById("sphone").value;
		var reg_phone = /^\d{10}$/;
		if (p_phone === "") {
			alert("Phone number for shiping address can not be empty!");
			ok = false;
		} else if (!p_phone.match(reg_phone)) {
			alert("Phone number for shiping address can only have 10 digital number");
			ok = false;
		}
		if (!ok)
			return ok;

		// check street is valid
		var p_str = document.getElementById("sadrs").value;
		if (p_str === "") {
			alert("Please insert the street information for shiping address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check state is valid
		var p_sta = document.getElementById("sstate").value;
		if (p_sta === "0") {
			alert("Please Select state/province for shiping address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check country is valid
		var p_con = document.getElementById("scountry").value;
		if (p_con === "0") {
			alert("Please Select country for shiping address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check zip code is valid
		var p_zip = document.getElementById("szip").value;
		var regex = new RegExp(
				/^[ABCEGHJKLMNPRSTVXY]\d[ABCEGHJKLMNPRSTVWXYZ]( )?\d[ABCEGHJKLMNPRSTVWXYZ]\d$/i);
		if (p_zip === "") {
			alert("Please enter your zip code for shiping address!");
			ok = false;
		} else if (!regex.test(p_zip)) {
			alert("please input valid zip code for shiping address");
			ok = false;
		}
		if (!ok)
			return ok;

	}
	// check if the card holder's name is valid
	var p_fname = document.getElementById("cardHolder").value;
	var reg_fname = /^[a-zA-Z ]+$/;
	if (p_fname === "") {
		alert("Card Holder's name can not be empty!");
		ok = false;
	} else if (!p_fname.match(reg_fname)) {
		alert("Card Holder's name can ONLY be Characters!");
		ok = false;
	}
	if (!ok)
		return ok;

	// check creadit card nulber is valid
	var visa = new RegExp("^4[0-9]{12}(?:[0-9]{3})?$");
	var amex = new RegExp("^3[47][0-9]{13}$");
	var mastercard = new RegExp("^5[1-5][0-9]{14}$");
	var ccNum = document.getElementById("cardNumber").value;
	var cardnumber = ccNum.replace(/[ -]/g, '');
	var card_type = document.getElementById("cardType");
	if (cardnumber === "") {
		alert("Please input card number!");
		ok = false;
	} else {
		if (card_type.value == "VISA") {
			if (!visa.test(cardnumber)) {
				alert("Please input valid visa number");
				ok = false;
			}
		} else if (card_type.value == "MASTER CARD") {
			if (!mastercard.test(cardnumber)) {
				alert("Please input valid master card number");
				ok = false;
			}
		} else if (card_type.value == "AMERICAN") {
			if (!amex.test(cardnumber)) {
				alert("Please input valid amex card number");
				ok = false;
			}
		}

	}
	if (!ok)
		return ok;
	// check CVC
	var visa_maste_cvc = /^[0-9]{3,3}$/;
	var amex_cvc = /^[0-9]{4,4}$/;
	var cvc = document.getElementById("cvc").value;
	if (cardnumber === "") {
		alert("Please input cvc number!");
		ok = false;
	} else {
		if (card_type.value == "VISA" || card_type.value == "MASTER CARD") {
			if (!visa_maste_cvc.test(cvc)) {
				alert("Please input valid cvc number");
				ok = false;
			}
		} else if (card_type.value == "AMERICAN") {
			if (!amex_cvc.test(cvc)) {
				alert("Please input valid amex card cvc number");
				ok = false;
			}
		}
	}

	if (!ok)
		return ok;

	// check validate year and month
	var exMonth = document.getElementById("expireM").value;
	var exYear = document.getElementById("expireY").value;
	var date = new Date();
	var curmonth = date.getMonth() + 1;
	var curyear = date.getFullYear();

	var today = new Date();

	if ((curYear > exYear)||(curyear = exYear && curmonth > exMonth) ) {
		alert("The expiry date is before today's date. Please select a valid expiry date");
		ok = false;
	}

	if (!ok)
		return ok;

	// chek billing address
	var p_check = document.getElementById("yesbill").checked;
	if (p_check) {
		// check if the user's first name and last name is valid
		var p_fname = document.getElementById("cfname").value;
		var reg_fname = /^[a-zA-Z]+$/;
		if (p_fname === "") {
			alert("First name for billing address can not be empty!");
			ok = false;
		} else if (!p_fname.match(reg_fname)) {
			alert("First name for billing address can ONLY be Characters!");
			ok = false;
		}
		if (!ok)
			return ok;

		// validate_lastname();
		var p_lname = document.getElementById("clname").value;
		var reg_name = /^[a-zA-Z]+$/;
		if (p_lname === "") {
			alert("Last name for billing address can not be empty!");
			ok = false;
		} else if (!p_lname.match(reg_name)) {
			alert("Last name for billing address can ONLY be Characters!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check phone number is valid
		var p_phone = document.getElementById("cphone").value;
		var reg_phone = /^\d{10}$/;
		if (p_phone === "") {
			alert("Phone number for billing address can not be empty!");
			ok = false;
		} else if (!p_phone.match(reg_phone)) {
			alert("Phone number for billing address can only have 10 digital number");
			ok = false;
		}
		if (!ok)
			return ok;

		// check street is valid
		var p_str = document.getElementById("cstreet").value;
		if (p_str === "") {
			alert("Please insert the street information for billing address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check state is valid
		var p_sta = document.getElementById("cstate").value;
		if (p_sta === "0") {
			alert("Please Select state/province for billing address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check country is valid
		var p_con = document.getElementById("ccountry").value;
		if (p_con === "0") {
			alert("Please Select country for billing address!");
			ok = false;
		}
		if (!ok)
			return ok;

		// check zip code is valid
		var p_zip = document.getElementById("czip").value;
		var regex = new RegExp(
				/^[ABCEGHJKLMNPRSTVXY]\d[ABCEGHJKLMNPRSTVWXYZ]( )?\d[ABCEGHJKLMNPRSTVWXYZ]\d$/i);
		if (p_zip === "") {
			alert("Please enter your zip code for billing address!");
			ok = false;
		} else if (!regex.test(p_zip)) {
			alert("please input valid zip code for billing address");
			ok = false;
		}
		if (!ok)
			return ok;

	}
	return ok;
}