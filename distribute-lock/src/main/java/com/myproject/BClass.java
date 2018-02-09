package com.myproject;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:58 2018/2/8
 * @Modified By
 */
public class BClass extends AClass {

    static {
        System.out.println("B static block...");
    }

    {
        System.out.println("B block...");
    }

    public BClass (){
        System.out.println("B init...");
    }
}
