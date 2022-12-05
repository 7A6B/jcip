package com.jcip.chapter2;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * note->线程安全性的定义：
 *  当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些线程将如何交替执行，
 *  并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，那么就称这个类时线程安全的
 *
 *  note-> 该定义非常严谨而且富有操作性，它要求线程安全地代码都必须具备一个共同的特征：代码本身封装了所有
 *      必要的正确性保障手段（如互斥同步等），令调用者无法关心多线程下的调用问题，更无需自己实现任何措施来保障多线程环境下的正确调用
 *
 */
@WebServlet(name = "StatelessFactorrizerServlet", value = "/stateless")
public class StatelessFactorizerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigInteger num = extractFromRequest(request);
        BigInteger[] factors = factor(num);
        encodeIntoResponse(response,factors);
    }

    private void encodeIntoResponse(HttpServletResponse response, BigInteger[] factors) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<span>"+"因式分解的因子为："+ Arrays.toString(factors) +"</span>");
        out.println("</body></html>");
    }

    //进行因式分解
    private BigInteger[] factor(BigInteger num) {
        final ArrayList<Integer> integers = new ArrayList<>();
        int intValue = num.intValue();
        for(int i =2;i < intValue;i++){
            while (intValue % i == 0){
                integers.add(i);
                intValue /=i;
            }
        }
        if(intValue > 1){
            integers.add(intValue);
        }
        final BigInteger[] bigIntegers = new BigInteger[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            bigIntegers[i] = BigInteger.valueOf(integers.get(i));
        }
        return bigIntegers;
    }

    //获取请求中的数据
    private BigInteger extractFromRequest(HttpServletRequest request) {
        final String num = request.getParameter("num");
        return new BigInteger(num);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
