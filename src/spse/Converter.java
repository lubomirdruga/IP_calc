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


    public static int binToDEC(String binaryNumber)
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


    public static String decToBin(int decNum)
    {
       return Integer.toBinaryString(Integer.parseInt(String.valueOf(decNum)));
    }


    //todo rename method
    //todo refactor this method in the IPv6 code!
    //todo Nejdze!!

    public static String decToHex(String input)
    {
        String output;
        int decNum = Integer.parseInt(input);
        int temp = 0;
        char[] decarray = String.valueOf(decNum).toCharArray();
        int [] decarrayarr = new int[decarray.length];
        String [] result = new String[decarrayarr.length];
        String hexNum = "";

        for (int i = decarray.length - 1 ; i >= 0 ; i--)
        {
            temp = decNum % 16;
            decNum = decNum / 16;

            switch (temp)
            {
                case 0 : result[i] = "0"; break;
                case 1 : result[i] = "1"; break;
                case 2 : result[i] = "2"; break;
                case 3 : result[i] = "3"; break;
                case 4 : result[i] = "4"; break;
                case 5 : result[i] = "5"; break;
                case 6 : result[i] = "6"; break;
                case 7 : result[i] = "7"; break;
                case 8 : result[i] = "8"; break;
                case 9 : result[i] = "9"; break;
                case 10 : result[i] = "A"; break;
                case 11 : result[i] = "B"; break;
                case 12 : result[i] = "C"; break;
                case 13 : result[i] = "D"; break;
                case 14 : result[i] = "E"; break;
                case 15 : result[i] = "F"; break;
            }
            decarrayarr[i] = temp;
        }

        int m = 0;
        for (String u: result)
        {
            if (result[0] == "0" && result[1] == "0") {
                String helparray[] = new String[result.length - 2];
                for (String p : helparray) {
                    helparray[m] = result[m + 2];
                    p = helparray[m];
                    hexNum = hexNum+helparray[m];
                    m++;
                }
                break;
            } else if (result[0] == "0")
            {
                String helparray[] = new String[result.length - 1];
                for (String p : helparray) {
                    helparray[m] = result[m + 1];
                    p = helparray[m];
                    hexNum = hexNum + helparray[m];
                    m++;
                }
                break;
            }
        }

        output = hexNum;

        if (Integer.parseInt(input) >= 0 && Integer.parseInt(input) <= 9)
        {
            switch (Integer.parseInt(input))
            {
                case 0:
                    output = "0";
                    break;
                case 1:
                    output = "1";
                    break;
                case 2:
                    output = "2";
                    break;
                case 3:
                    output = "3";
                    break;
                case 4:
                    output = "4";
                    break;
                case 5:
                    output = "5";
                    break;
                case 6:
                    output = "6";
                    break;
                case 7:
                    output = "7";
                    break;
                case 8:
                    output = "8";
                    break;
                case 9:
                    output = "9";
                    break;
            }
        }

        return output;
    }


    public static String binToHex(String binary)
    {

        //BIN TO DEC
        int decimal = Integer.parseInt(binary,2);

        //dec to HEX
        return Integer.toString(decimal,16);
    }


    public static String hexToDec(String hexadecimalInput) throws Exception
    {
        String [] arr = new String[hexadecimalInput.length()];

        for (int x = 0; x < arr.length ; x++)
        {
            char p = hexadecimalInput.charAt(x);

            if (p != 'A' && p!= 'B'  && p != 'C' && p != 'D' && p!= 'E'  && p != 'F' && p != '1' && p!= '2'  && p != '3' && p != '4' && p!= '5'  && p != '6' && p != '7' && p!= '8'  && p != '9' && p != '0' )
            {
                throw new Exception();
            }
            else
            {
                for (int j = 0; j < arr.length; j++)
                {
                    arr [j] = String.valueOf(hexadecimalInput.charAt(j));
                }
            }
        }
        double temp = 0;
        int exponent = 0;

        for (int a = arr.length - 1; a >= 0 ; a--)
        {
            switch (arr[a])
            {
                case "1" : temp = temp + (1 * Math.pow(16 , exponent)); break;
                case "2" : temp = temp + (2 * Math.pow(16 , exponent)); break;
                case "3" : temp = temp + (3 * Math.pow(16 , exponent)); break;
                case "4" : temp = temp + (4 * Math.pow(16 , exponent)); break;
                case "5" : temp = temp + (5 * Math.pow(16 , exponent)); break;
                case "6" : temp = temp + (6 * Math.pow(16 , exponent)); break;
                case "7" : temp = temp + (7 * Math.pow(16 , exponent)); break;
                case "8" : temp = temp + (8 * Math.pow(16 , exponent)); break;
                case "9" : temp = temp + (9 * Math.pow(16 , exponent)); break;
                case "A" : temp = temp + (10 * Math.pow(16 , exponent)); break;
                case "B" : temp = temp + (11 * Math.pow(16 , exponent)); break;
                case "C" : temp = temp + (12 * Math.pow(16 , exponent)); break;
                case "D" : temp = temp + (13 * Math.pow(16 , exponent)); break;
                case "E" : temp = temp + (14 * Math.pow(16 , exponent)); break;
                case "F" : temp = temp + (15 * Math.pow(16 , exponent)); break;
            }
            exponent++;
        }

        int vysledok = (int) temp;
        return Integer.toString(vysledok);
    }



    public static String hexToBin (String hexadecimal)
    {
        char [] hexarray = String.valueOf(hexadecimal).toUpperCase().toCharArray();
        String[] bin = new String[hexarray.length];
        int temp = 0;
        String fullIp = "";

        for (int x:hexarray)
        {
            switch (hexarray[temp])
            {
                case '0' : bin[temp] = "0000"; break;
                case '1' : bin[temp] = "0001"; break;
                case '2' : bin[temp] = "0010"; break;
                case '3' : bin[temp] = "0011"; break;
                case '4' : bin[temp] = "0100"; break;
                case '5' : bin[temp] = "0101"; break;
                case '6' : bin[temp] = "0110"; break;
                case '7' : bin[temp] = "0111"; break;
                case '8' : bin[temp] = "1000"; break;
                case '9' : bin[temp] = "1001"; break;
                case 'A' : bin[temp] = "1010"; break;
                case 'B' : bin[temp] = "1011"; break;
                case 'C' : bin[temp] = "1100"; break;
                case 'D' : bin[temp] = "1101"; break;
                case 'E' : bin[temp] = "1110"; break;
                case 'F' : bin[temp] = "1111"; break;
            }
            fullIp = fullIp + bin[temp];
            temp++;
        }
        return fullIp;
    }


}
