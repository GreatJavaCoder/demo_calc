package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InputException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("наберите входную строку");
        String str = scanner.nextLine();
        str = str.toUpperCase(Locale.ROOT);
        if (!isOperationOK(str)) {
            throw new InputException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, *, /)");
        }
        System.out.println(calc(str));
    }

    static boolean isOperationOK(String inData) throws InputException {
        int kp = 0, kmn = 0, kml = 0, kd = 0, k = 0;
        for (int ioo=1; ioo<inData.length(); ioo++){
            if (inData.substring(ioo-1,ioo).equals("+")){ kp++;}
            if (inData.substring(ioo-1,ioo).equals("-")){ kmn++;}
            if (inData.substring(ioo-1,ioo).equals("*")){ kml++;}
            if (inData.substring(ioo-1,ioo).equals("/")){ kd++;}
            k = kp + kmn + kml + kd; }

        if (k == 0){
            throw new InputException("строка не содержит математическую операцию");
        }

        if (k == 1){
            return true;
        } else {
            return false;
        }
    }

    public static String calc(String input) throws InputException {
        int oper = 0, ax = 0, ay = 0, xr = 0, yr = 0, ar = 0, rr = 0;
        String result = "";

        int indexPlus = input.indexOf("+");
        int indexMinus = input.indexOf("-");
        int indexMulti = input.indexOf("*");
        int indexDiv = input.indexOf("/");

        int pos = 0;
        if (indexPlus != -1) {
            pos = indexPlus;
            oper = 1;
        }
        if (indexMinus != -1) {
            pos = indexMinus;
            oper = 2;
        }
        if (indexMulti != -1) {
            pos = indexMulti;
            oper = 3;
        }
        if (indexDiv != -1) {
            pos = indexDiv;
            oper = 4;
        }
        String str1 = input.substring(0, pos);
        str1 = str1.trim();
        String str2 = input.substring(pos + 1);
        str2 = str2.trim();
        Operand op1 = new Operand();
        Operand op2 = new Operand();

        if (op1.is_arabic(str1) && op2.is_roman(str2)){
            throw new InputException("одновременное использование разных систем счисления");
        }

        if (op1.is_roman(str1) && op2.is_arabic(str2)){
            throw new InputException("одновременное использование разных систем счисления");
        }
/////////// анализ арабских входных цифр
        if (op1.is_arabic(str1)) {
            ax = Integer.parseInt(str1);
            if (ax<1 || ax>10) {
                throw new InputException("первый аргумент вне заданного диапазона");
            }
        };

        if (op2.is_arabic(str2)) {
            ay = Integer.parseInt(str2);
            if (ay<1 || ay>10) {
                throw new InputException("второй аргумент вне заданного диапазона");
            }
        };

/////////////////////////////////       анализ римских входных цифр
        if (op1.is_roman(str1)) {
            Rop r1 = Rop.valueOf(str1);
            if  (r1.getF()<1 || r1.getF()>10) {
                throw new InputException("первый аргумент вне заданного диапазона");
            }
        }

        if (op2.is_roman(str2)) {
            Rop r2 = Rop.valueOf(str2);
            if  (r2.getF()<1 || r2.getF()>10) {
                throw new InputException("второй аргумент вне заданного диапазона");
            }
        }

///////////////   оперирование арабскими цифрами
        if (op1.is_arabic(str1)&&op2.is_arabic(str2)){
            int x = Integer.parseInt(str1); int y = Integer.parseInt(str2);
            if (oper==1) {ar = x + y ;}
            if (oper==2) {ar = x - y ;}
            if (oper==3) {ar = x * y ;}
            if (oper==4) {ar = x / y ;}
            result = Integer.toString(ar);
        }
//////////////   оперирование римскими цифрами
        if  (op1.is_roman(str1)&&op2.is_roman(str2)) {
            Rop r1 = Rop.valueOf(str1); Rop r2 = Rop.valueOf(str2);
            if (oper==1) {rr = r1.getF() + r2.getF();}
            if (oper==2) {rr = r1.getF() - r2.getF();}
            if (oper==3) {rr = r1.getF() * r2.getF();}
            if (oper==4) {rr = r1.getF() / r2.getF();}
            if (rr<=0) {throw new InputException("в римской системе нет отрицательных чисел");}
            for (Ran r : Ran.values()){
                if (rr == r.getFF() ) { result = r.name(); };
            }
        }

        return "ответ равен " + result;
}

static class Operand{
        boolean is_arabic(String data){
            try{
                int intValue = Integer.parseInt(data);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        }
        boolean is_roman(String data){
            try {
                Rop r = Rop.valueOf(data);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
}
}
