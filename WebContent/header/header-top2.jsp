<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%KhachHang kh = (KhachHang)session.getAttribute("userlogin"); %>
</head>
<body>
		<div class="header" id="home">	
			<div class="header-para">
				<p>The quick brown <span>fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog.</span></p>	
			</div>	
			<ul class="header-in">
				<li><a href="">Xin chào,
				<%if(kh!=null){
					out.print(kh.getTen());
					} 
				%>
				</a> </li>
				<li><a href="account.html">MY ACCOUNT</a> </li>
				<li><a href="wishlist.html">  WISHLIST</a></li>
				<li ><a href="contact.html" > CONTACT US</a></li>
				<li ><a href="Login?action=Logout" >Đăng xuất</a></li>
			</ul>
				<div class="clearfix"> </div>
		</div>
		<!---->
		<div class="header-top">
			<div class="logo">
				<a href="index.html"><img src="images/logo.png" alt="" ></a>
			</div>
			<div class="header-top-on">
				<ul class="social-in">
					<li><a href="#"><i> </i></a></li>						
					<li><a href="#"><i class="ic"> </i></a></li>
					<li><a href="#"><i class="ic1"> </i></a></li>
					<li><a href="#"><i class="ic2"> </i></a></li>
					<li><a href="#"><i class="ic3"> </i></a></li>
				</ul>
			</div>
				<div class="clearfix"> </div>
		</div>
</body>
</html>