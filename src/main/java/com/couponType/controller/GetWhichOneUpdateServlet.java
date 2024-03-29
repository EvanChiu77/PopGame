package com.couponType.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.couponType.model.CouponTypeService;
import com.couponType.model.CouponTypeVO;
import com.memCoupon.model.MemCouponService;
import com.memCoupon.model.MemCouponVO;

@WebServlet("/couponType/GetWhichOneUpdateServlet")
public class GetWhichOneUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		/***************************1.接收請求參數****************************************/
		Integer couponTypeNo = Integer.valueOf(request.getParameter("couponTypeNo"));
		
		/***************************2.開始查詢資料****************************************/
		CouponTypeService couponTypeService=new CouponTypeService();
		CouponTypeVO couponTypeVO =couponTypeService.listOneCouponType(couponTypeNo);
		/***************************3.查詢完成,準備轉交(Send the Success view)*************/
		request.setAttribute("couponTypeVO", couponTypeVO); // 資料庫取出的empVO物件,存入req
		String url = "/backend/couponType/editCouponType.jsp";
		MemCouponService memCouponService = new MemCouponService();
		List<MemCouponVO> list = memCouponService.showAllMemCoupon();
		getServletContext().setAttribute("list", list);
		RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 
		successView.forward(request, response);
		
		
		
	}

}