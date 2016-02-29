function mouseOver(img){
	if(img.src.indexOf("_on") > 0){
	}else{
		img.src = img.src.replace("_off","_on");
	}
}

function mouseOut(img){
	if(img.src.indexOf("_off") > 0){
	}else{
		img.src = img.src.replace("_on","_off");
	}
}

function selectSize(button){
	var sizeButtons = button.form.size;
	if(sizeButtons.length > 1){
		for(var i=0; i<sizeButtons.length; i++){
			if(sizeButtons[i].value == button.value){
				sizeButtons[i].style.border = "solid 3px #999";
				button.form.product_size.value = button.value;
			}else{
				sizeButtons[i].style.border = "solid 1px #999";
			}
		}
	}else{
		button.style.border = "solid 3px #999";
		button.form.product_size.value = button.value;
	}
}

function selectColor(button){
	var colorButtons = button.form.color;
	if(colorButtons.length > 1){
		for(var i=0; i<colorButtons.length; i++){
			if(colorButtons[i].value == button.value){
				colorButtons[i].style.border = "solid 3px #999";
				button.form.product_color.value = button.value;
			}else{
				colorButtons[i].style.border = "solid 1px #999";
			}
		}
	}else{
		button.style.border = "solid 3px #999";
		button.form.product_color.value = button.value;
	}
}

function changeImage(imgName, row){
	document.getElementById('productImage').src = '../img/product/' + imgName;
	//row.style.backgroundColor = '#eee';
}

function checkSearchForm(){
	var text = document.searchForm.searchTxt;
	if(text.value == "제품 번호" || text.value == ""){
		alert("제품 번호를 입력해주세요.");
		return false;
	}

	return true;
}
