package com.jcip.chapter4;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OneServlet", value = "/one")
public class OneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Person person = new Person("tom", 22);
        final ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("person",person);
        person.setAge(33);
        person.setName("Kobe");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
