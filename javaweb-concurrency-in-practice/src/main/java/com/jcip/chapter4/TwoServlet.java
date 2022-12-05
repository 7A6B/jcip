package com.jcip.chapter4;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TwoServlet", value = "/two")
public class TwoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletContext servletContext = request.getServletContext();
        final Object person = servletContext.getAttribute("person");
        System.out.println(Thread.currentThread().getName()+"==>"+person);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
