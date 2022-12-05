package com.jcip.chapter1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SafeServlet", value = "/safe")
public class SafeServlet extends HttpServlet {

    private List<String> msgList;

    @Override
    public void init() throws ServletException {
        msgList = new ArrayList<>();
        msgList.add("a");
        msgList.add("b");
        msgList.add("c");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + Thread.currentThread().getName() +"==>"+msgList + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
