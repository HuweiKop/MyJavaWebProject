package com.myproject;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:58 2018/2/8
 * @Modified By
 */
public class AClass {

    static {
        System.out.println("A static block...");
    }

    {
        System.out.println("A block...");
    }

    public AClass (){
        System.out.println("A init...");
    }
}
