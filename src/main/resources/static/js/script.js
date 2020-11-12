function login(){

    //校验用户账号
    var userName=form1.username.value;
    var regName= /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,6})/;
    if (!regName.test(userName)) {
        $("#error1").html("用户名2-6位中文或者6-16位字符");
        return false;
    }
    // else if(regName.test(userName)){
    //     $("#error").empty();
    // }

    //校验用户密码
    var password =form1.password.value;
    var regPassword= /(^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$)/;
    if (!regPassword.test(password)) {
        $("#error2").html("用户密码必须包含字母和数字，且在6~18位");
        return false;
    }
    // else if(regPassword.test(password)){
    //     $("#error2").empty();
    // }

	// if(form1.username.value==''){
	// 	alert('用户名不能为空！');
	// 	return false;
	// }
	// if(form1.password.value==''){
	// 	alert('密码不能为空！');
	// 	return false;
	// }
	form1.action="/Userlogin";
	form1.submit();
}

window.onload = function(){
	var i3 = document.getElementsByClassName('input_3');
	for(var i=0;i<i3.length;i++){
		i3[i].onmouseover = function(){
			this.style.backgroundColor = "#23271F";
			this.style.color = "#fff";
		}
		i3[i].onmouseout = function(){
			this.style.backgroundColor = "#fff";
			this.style.color = "#23271F";
		}
	}
	var pass = document.getElementById("password");
	pass.onfocus = function(){
		pass.type = "password";	
	}
}