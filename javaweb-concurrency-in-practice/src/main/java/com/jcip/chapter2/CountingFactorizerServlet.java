package com.jcip.chapter2;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CountingFactorizerServlet", value = "/count")
public class CountingFactorizerServlet extends HttpServlet {
    //warn-> 虽然AtomicLong是线程安全的类，但是count.get() 和count.incrementAndGet() 不是原子操作
    private final AtomicLong count = new AtomicLong(0);

    public long getCount(){
        return count.get();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigInteger num = extractFromRequest(request);
        BigInteger[] factors = factor(num);
        count.incrementAndGet();
        encodeIntoResponse(response, factors);
    }

    private BigInteger extractFromRequest(HttpServletRequest request) {
        return new BigInteger("7");
    }

    private BigInteger[] factor(BigInteger num) {
        return new BigInteger[]{num};
    }

    private void encodeIntoResponse(HttpServletResponse response, BigInteger[] factors) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.println(Thread.currentThread().getName()+" 执行的次数："+getCount());
        System.out.println(Thread.currentThread().getName()+" 执行的次数： "+getCount());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
