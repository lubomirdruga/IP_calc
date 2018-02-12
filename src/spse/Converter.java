package spse;


public class Converter
{


    public static String toBinary(int DECnumber)
    {
        String binaryOut = "";

        for (int i = 7; i >= 0; i--)
        {
            if(DECnumber >= Math.pow(2, i) || DECnumber == 1 && i == 0)
            {
                binaryOut += "1";
                DECnumber -= Math.pow(2, i);
            }
            else
                binaryOut += "0";
        }
        return binaryOut;
    }


    public static int toDEC(String binaryNumber)
    {
        int DECNumber = 0;
        int exponent = binaryNumber.length() - 1;

        for (int i = 0; i < binaryNumber.length(); i++)
        {
            if (binaryNumber.charAt(i) == '1')
                DECNumber += Math.pow(2, exponent);

            exponent--;
        }
        return DECNumber;
    }
}
