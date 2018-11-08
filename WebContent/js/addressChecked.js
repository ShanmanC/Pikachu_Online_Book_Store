function checkBillAddr() {
	var checkBox = document.getElementById("yesbill");
	var text = document.getElementById("billing_addr_block");
	if (checkBox.checked == true) {
		text.style.display = "block";
	} else {
		text.style.display = "none";
	}
}

function checkNewShippingAddr() {
	var checkBox = document.getElementById("yesship");
	var text = document.getElementById("new_shipping_addr_block");
	if (checkBox.checked == true) {
		text.style.display = "block";
	} else {
		text.style.display = "none";
	}
}
