package com.example.demo;

import java.io.IOException;

class ExceptionPropagation{
    void m(){
        int data=2/0;
    }
    void n(){
        m();
    }
    void p(){
        try{
            n();
        }catch(Exception e){System.out.println("exception catch");}
    }
    public static void main(String args[]){
        ExceptionPropagation obj=new ExceptionPropagation();
        obj.p();
        System.out.println(" Main Method executed ");
    }
}
