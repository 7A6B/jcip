package com.jcip.chapter2;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SyncFactorizerServlet", value = "/sync")
public class SyncFactorizerServlet extends HttpServlet {

    private BigInteger lastNum;
    private BigInteger[] lastFactors;

    @Override
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final BigInteger num = extractFromRequest(request);
        if(num.equals(lastNum)){
            encodeIntoResponse(response,lastFactors);
        }else {
            final BigInteger[] factors = factor(num);
            lastNum =num;
            lastFactors = factors;
            encodeIntoResponse(response,factors);
        }
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
        System.out.println("-------------------------------");
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
