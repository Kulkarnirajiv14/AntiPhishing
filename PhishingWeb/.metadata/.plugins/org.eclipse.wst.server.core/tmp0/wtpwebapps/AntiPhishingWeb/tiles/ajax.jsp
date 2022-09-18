<%@page import="java.util.Arrays"%>

<%@page import="java.io.Writer"%>

<%@page import="java.awt.image.BufferedImageFilter"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>


<%-- <%@page import="com.google.code.chatterbotapi.ChatterBotApiTest"%> --%>

<%@page import="com.constant.ServerConstants"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>

<%@page import="java.util.List"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="com.helper.UserModel"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page import="com.helper.ConnectionManager"%>
<%@page import="com.helper.StringHelper"%>

<%@page import="java.util.HashMap"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	String sMethod = StringHelper.n2s(request.getParameter("methodId"));
	String returnString = "";
	System.out.println("HIIIII");
	boolean bypasswrite = false;

	HashMap parameters = StringHelper.displayRequest(request);
	// 	BufferedImageFilter bi = getMyImage();
	// 	if (sMethod.equalsIgnoreCase("registerNewUser")) {
	// 		returnString = ConnectionManager.insertUser(parameters);
	// 	} else
			 if (sMethod.equalsIgnoreCase("checkLogin")) {
			UserModel um = ConnectionManager.checkLogin(parameters);
			if (um != null) {
				session.setAttribute("USER_MODEL", um);
				returnString = "1";
			} else {
				returnString = "2";
			}
		}else if (sMethod.equalsIgnoreCase("registerNewUser")) {
			returnString = ConnectionManager.insertIntoDB(parameters,
					"useraccount");
		}else if (sMethod.equalsIgnoreCase("registerNewUser")) {
			returnString = ConnectionManager.insertIntoDB(parameters,
					"useraccount");
		}else if (sMethod.equalsIgnoreCase("deletebyid")) {
		
			int i=ConnectionManager.deletebyid(parameters);
			returnString =String.valueOf(i);
							
			
		}

// 	else if (sMethod.equalsIgnoreCase("logout")) {
// 		session.removeAttribute("USER_MODEL");
// 		bypasswrite = tr


	if (!bypasswrite) {
		out.println(returnString);
	}
%>
