package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemVO;
import static com.core.utils.JSONTrans.json2Pojo;
import static com.core.utils.JSONTrans.writePojo2Json;
import static com.member.utils.MemeberConstants.MEM_SERVICE;
@WebServlet("/mem/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		//將JSON轉成MemVO
		MemVO memVO = json2Pojo(request, MemVO.class);
		if(memVO == null) {
			memVO =new MemVO();
			memVO.setMessage("查無此會員");
			memVO.setSuccessful(false);
			writePojo2Json(response, memVO);
			return;
		}
		//調用service
		memVO = MEM_SERVICE.login(memVO);
		//如果帳密錯誤即立刻回傳
		if(!memVO.isSuccessful()) {
			writePojo2Json(response, memVO);
			return;
		}
		//如果尚未驗證即立刻回傳
		if(memVO.getMemVrfed() == 0) {
			memVO.setMessage("尚未驗證，請至信箱點取驗證超連結");
			memVO.setSuccessful(false);
			writePojo2Json(response, memVO);
			return;
		}
		//帳號遭停權則回傳
		if(memVO.getMemStatus() == 0) {
			memVO.setMessage("帳號已被停權，如有疑問請洽詢客服");
			memVO.setSuccessful(false);
			writePojo2Json(response, memVO);
			return;
		}
		final HttpSession session = request.getSession();
		//驗證成功設定session
		if(memVO.isSuccessful()) {
			if(request.getSession(false) != null) {
				request.changeSessionId();
			}
			session.setAttribute("loggedin", true);
			session.setAttribute("memVO", memVO);
		}
		String initlocation = (String) session.getAttribute("initlocation");
		if(initlocation !=null) {
			session.removeAttribute("initlocation");
			memVO.setInitlocation(initlocation);
		}
		writePojo2Json(response, memVO);
	}
	
	

	
	
	
}
