package com.myproject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        Server server = new Server(9999);
//        server.run();

        String rechargeAmount = "11,10.";

        rechargeAmount = rechargeAmount.replaceAll(",", "");
        String[] arrRechargeAmount = rechargeAmount.split("\\.");
        Long lRechargeAmount = Long.valueOf(arrRechargeAmount[0]) * 100;
        if (arrRechargeAmount.length == 2 && arrRechargeAmount[1].length() <= 2) {
            if (arrRechargeAmount[1].length() == 1) {
                lRechargeAmount += (Long.valueOf(arrRechargeAmount[1]) * 10);
            } else if (arrRechargeAmount[1].length() == 2) {
                lRechargeAmount += Long.valueOf(arrRechargeAmount[1]);
            }
        } else if(arrRechargeAmount.length == 1) {
        } else {
            System.out.println("充值金额异常");
        }

        System.out.println(lRechargeAmount);
    }
}
