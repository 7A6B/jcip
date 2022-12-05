package com.jcip.chapter2;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CountingFactorizer2Servlet", value = "/count2")
public class CountingFactorizer2Servlet extends HttpServlet {
    //LongAdder
    private final AtomicLong count = new AtomicLong(0);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigInteger num = extractFromRequest(request);
        BigInteger[] factors = factor(num);
        encodeIntoResponse(count,response, factors);
    }

    private BigInteger extractFromRequest(HttpServletRequest request) {
        return new BigInteger("7");
    }

    private BigInteger[] factor(BigInteger num) {
        return new BigInteger[]{num};
    }

    private void encodeIntoResponse(AtomicLong count,HttpServletResponse response, BigInteger[] factors) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.println(Thread.currentThread().getName()+" 执行的次数："+count.incrementAndGet());
        System.out.println(Thread.currentThread().getName()+" 执行的次数： "+count.incrementAndGet());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
