package action;

import javax.servlet.http.Cookie;
import service.impl.AccountManager;

/**
 * 登入動作
 * @author John
 */
public class Logout extends BaseAction{
	
	/**
	 * stis logout
	 * @return "public-access.xml"中"LoginAction"節點名稱"logout"定位頁面"/index.jsp"
	 */
	public String execute() throws Exception {		
		AccountManager am = (AccountManager) get("AccountManager");
		if(getSession()!=null){
			am.logOut(request, response);
		}
		Cookie cookie = new Cookie("userid", "deleted");
		cookie.setMaxAge(0);
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	response.addCookie(cookie);
		response.sendRedirect("/ssos/Logout");
		return null;
	}

}
