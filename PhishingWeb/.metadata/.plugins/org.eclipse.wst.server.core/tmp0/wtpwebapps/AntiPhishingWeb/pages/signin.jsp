
<%@page import="java.util.HashMap"%>
<%@page import="com.helper.UserModel"%>
<%@page import="com.helper.ConnectionManager"%>
<%@page import="com.helper.StringHelper"%>

<%
String projectTitle="AntiPhishing";
	String sMethod = StringHelper.n2s(request.getParameter("methodId"));
	if (sMethod != null && sMethod.length() > 0) {
		String returnString = "";
		HashMap parameters = StringHelper.displayRequest(request);
		boolean bypasswrite = false;
		if (sMethod.equalsIgnoreCase("checkLogin")) {
			UserModel um = ConnectionManager.checkLogin(parameters);
			if (um != null) {
				session.setAttribute("USER_MODEL", um);
				returnString = "1";
			} else {
				returnString = "2";
			}
		} else if (sMethod.equalsIgnoreCase("registerNewUser")) {
			System.out.print("registrer call");
			returnString = ConnectionManager.insertIntoDB(parameters,
					"useraccounts");
		}
		if (!bypasswrite) {
			out.println(returnString);
			out.flush();
			out.close();
		}
		return;
	}
	/*   DROP TABLE IF EXISTS `useraccount`;
	 CREATE TABLE `useraccounts` (
	 `name` varchar(45) NOT NULL,
	 `emailid` varchar(45) NOT NULL,
	 `phone` varchar(45) NOT NULL,
	 `uname` varchar(45) NOT NULL,
	 `password` varchar(45) NOT NULL,
	 `address` varchar(45) NOT NULL,
	 `role` varchar(45) NOT NULL,
	 `uid` int(11) NOT NULL AUTO_INCREMENT,
	 PRIMARY KEY (`uname`,`uid`) USING BTREE,
	 UNIQUE KEY `user_email` (`emailid`),
	 UNIQUE KEY `uc` (`emailid`,`uname`,`phone`)
	 ) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

	 public static UserModel checkLogin(HashMap parameters){
	 String uname = StringHelper.n2s(parameters.get("username"));
	 String upass = StringHelper.n2s(parameters.get("pass"));
	 UserModel um = null;
	 String query = "select * from useraccount where uname like '" + uname
	 + "' and password like '" + upass + "'";
	 List l = DBUtils.getBeanList(UserModel.class, query);

	 if (l!=null&& l.size() > 0) {
	 um = (UserModel) l.get(0);
	 return um;
	 } else {
	 return null;
	 }
	 }
	
	 ===============================================
	 public static String insertIntoDB(HashMap parameters, String tabelName) {
	 Set<String> keys = parameters.keySet();
	 StringBuffer data = new StringBuffer();
	 StringBuffer value = new StringBuffer();
	 for (String key : keys) {
	 if (key.equalsIgnoreCase("methodId")
	 || key.equalsIgnoreCase("confirm")|| key.equalsIgnoreCase("repass")) {
	 continue;
	 }
	 data.append(key + ",");
	 value.append("'" + parameters.get(key).toString() + "',");
	 }
	 String sql = "insert into " + tabelName + " ("
	 + data.toString().substring(0, data.toString().length() - 1)
	 + ") values("
	 + value.toString().substring(0, value.toString().length() - 1)
	 + ")";
	 System.out.println("sql :" + sql);
	 int rows = DBUtils.executeUpdate(sql);
	 if (rows > 0) {
	 return "User Registered Successfully..!!";
	 } else {
	 return "Error..?? Somethings is wrong..??";
	 }
	 }


	 */
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login Form</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=projectTitle %> Signin/Signup</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/theme/css/stylesighup.css">
<script src="<%=request.getContextPath()%>/theme/js/jquery-2.1.1.min.js"></script>
</head>
<body>

	<section>
		<div class="container">
			<div class="user signinBx">
				<div class="imgBx">
					<img
						src="https://image.freepik.com/free-vector/cross-platform-business-app-illustration_82574-7639.jpg"
						alt="" />
				</div>
				<div class="formBx">
					<form id='loginform' action="javascript:fnLogin();" method="post">
						<h2><%=projectTitle %> Sign In</h2>
						<input type="text" name="username" placeholder="Username" /> <input
							type="password" name="pass" placeholder="Password" /> <input
							type="submit" name="" style="background-color: #008CBA;"value="Login" />
						<p class="signup">
							Don't have an account ? <a href="#" onclick="toggleForm();">Sign
								Up.</a>
						</p>
					</form>
				</div>
			</div>
			<div class="user signupBx">
				<div class="formBx">
					<form  method="post"id="regform">
						<h2><%=projectTitle %> Signup</h2>
						<input type="text" name="name" placeholder="Full Name"
							required="required" /> <input type="email" name="emailid"
							placeholder="Email Address" required="required"
							pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
							title="Please enter valid email" /> <input type="text"
							name="phone" placeholder="Mobile Number" pattern="[789][0-9]{9}"
							title="Please enter valid 10 digit mobile number"
							required="required" /> <input type="text" name="uname"
							placeholder="Username" required="required" /><input
							type="password" name="password" placeholder="Create Password"
							required="required" /> <input type="password" name="repass"
							placeholder="Confirm Password" required="required" />
							 <input type="button" onclick="fnRegister()" style="background-color: #008CBA;"  name="" value="Sign Up" />

						<!-- 							pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$" -->
						<!-- 							title="Password should contain Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character" -->

						<p class="signup">
							Already have an account ? <a href="#" onclick="toggleForm();">Sign
								in.</a>
						</p>
					</form>
				</div>
				<div class="imgBx">
					<img
						src="https://assets-global.website-files.com/5bcb46130508ef456a7b2930/5c65f4ae2f71d67d0ee9f032_hero-image.png"
						alt="" />
				</div>
			</div>
		</div>
	</section>
</body>

<script type="text/javascript">
function toggleForm(){
	  const container = document.querySelector('.container');
	  container.classList.toggle('active');
}
function fnLogin(){	  
	var str = $("#loginform" ).serialize();
	//alert(str);
	$.post("<%=request.getContextPath()%>/pages/signin.jsp?methodId=checkLogin",
						str, function(data) {		
		                    data = $.trim(data);
								if (data==1) {								
								window.location.href="<%=request.getContextPath()%>/pages/index.jsp";
							} else {
								alert("Please Enter Valid Credentials");
								$('#username').css({ "border": '#FF0000 1px solid'});
								$('#pass').css({ "border": '#FF0000 1px solid'});
							}
// 							$('#loginform')[0].reset();
						});
   
	}    
	
function fnRegister(){	
	alert("ok");
	var str = $("#regform" ).serialize();
	alert(str);
	var pass = $('#password').val();
	var cpass = $('#repass').val();
	if(pass != cpass){
		alert('password and confirm password should match');
		$('#password').css({ "border": '#FF0000 1px solid'});
		$('#repass').css({ "border": '#FF0000 1px solid'});
		return;		
	}

$.post("<%=request.getContextPath()%>/pages/signin.jsp?methodId=registerNewUser",
						str, function(data) {
							data = $.trim(data);
							if (data==1) {
								$('#regform')[0].reset();
								window.location.href="<%=request.getContextPath()%>/pages/login.jsp";
							} else {
								alert(data);
							}

						});

	}
</script>
</body>
</html>
