<%@page import="com.helper.UserModel"%>
<%@page import="com.helper.ConnectionManager"%>
<%@page import="com.feature.urls.TestClassifier"%>
<%@page import="com.action.CrawlWebsite"%>
<%@page import="com.helper.WebsiteModel"%>
<%@page import="com.helper.SpamModel"%>
<%@page import="com.helper.PhishMailGuard"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.io.OutputStream"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="com.helper.StringHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.constant.ServerConstants"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%
	System.out.println("Hiii");
	String sMethod = StringHelper.n2s(request.getParameter("methodId"));
	String returnString = "";
	boolean bypasswrite = false;
	//OutputStream responseBody = response.getOutputStream();
	System.out.println(sMethod);
	HashMap parameters = StringHelper.displayRequest(request);
	System.out.println(parameters);

	if (sMethod.equalsIgnoreCase("checkURL")) {
		UserModel um = (UserModel)session.getAttribute("USER_MODEL");
		System.out.println("Method Calling" + sMethod);
		String url = StringHelper.n2s(request.getParameter("url"));
		url = URLDecoder.decode(url);
		

		SpamModel sm = PhishMailGuard.getAllInformationFromUrl(url);
		boolean map = TestClassifier.checkURLPhishing(url);
		if(map){
			System.out.println("PHISHING");
			returnString = "PHISHING";
		}else{
			System.out.println("NORMAL");
			returnString = "NORMAL";
		}
		int i = ConnectionManager.addToHistory(url, um.getUid());
	} else if (sMethod.equalsIgnoreCase("checkURLCSS")) {
		String url = StringHelper.n2s(request.getParameter("url"));
		url = URLDecoder.decode(url);
		WebsiteModel siteToBeChecked = CrawlWebsite.createCSSTemplate(
				url, true);
		ObjectOutputStream oos = new ObjectOutputStream(
				response.getOutputStream());
		oos.writeObject(siteToBeChecked);
		oos.close();
		return;
	} else if (sMethod.equalsIgnoreCase("checkSCSSSimilatity")) {
		String url = StringHelper.n2s(request.getParameter("url"));
		url = URLDecoder.decode(url);
		String siteToBeChecked = CrawlWebsite.checkIfPhishing(url);
		System.out.println(siteToBeChecked);
		ObjectOutputStream oos = new ObjectOutputStream(
				response.getOutputStream());
		oos.writeObject(siteToBeChecked);
		oos.close();
		return;
	} else if (sMethod.equalsIgnoreCase("logout")) {
		System.out.println("logout");
		session.removeAttribute("USER_MODEL");
		bypasswrite = true;
%>
<script>
			window.location.href='<%=request.getContextPath()%>/pages/signin.jsp';
</script>
<%
	}

	if (!bypasswrite) {
		out.println(returnString);
	}
%>