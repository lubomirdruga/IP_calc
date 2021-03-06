package sk.spse.pcoz.druga_olsavsky.Models;

// testovaie adresy
// 2001:ACAD:1000:0000:0000:0000:0000:0000 59 8
// FF:AA:AA:AA:FF:AA

import sun.net.util.IPAddressUtil;
import java.net.Inet6Address;
import java.net.UnknownHostException;

public class IPv6
{
    String [] partIp;
    int prefix, subNumber;
    String fourthOctet, fullIpBinary, ipv6Hex;
    String mac = null;

    public IPv6(String ipv6Hex, int prefix, int subNumber)  // konstruktor pre subnetting
    {
        this.ipv6Hex = ipv6Hex;
        this.prefix = prefix;
        this.subNumber = subNumber;
    }

    public IPv6(String ipv6Hex, int prefix, String mac)  // konstruktor pre parametre
    {
        this.ipv6Hex = ipv6Hex;
        this.prefix = prefix;
        this.mac = mac;
    }

    //metoda na skracovanie IPv6 adresy
    public static String getCompressedAddress(String longAddress) throws UnknownHostException
    {
        longAddress = Inet6Address.getByName(longAddress).getHostAddress();
        return longAddress.replaceFirst("(^|:)(0+(:|$)){2,8}", "::").toUpperCase();
    }

    //metoda na subnetting
    public String[] subnetting()
    {
        int counthelp = 0;          // premenna ktora pomoze aby sa siete subnetovali podla mocnin 1,2,4,8 ...
        for (; (int)Math.pow(2,counthelp) < subNumber ; counthelp++) {}           //cyklus na pocet podsieti
        subNumber = (int)Math.pow(2,counthelp);           // premenna countsubent bude mocninou cisla 2

        this.partIp = ipv6Hex.split(":");           // rozdelena IPv6 - jednotlive oktety su v poli
        this.fourthOctet = partIp[3];               // stvrty oktet
        fourthOctet = hexbin(fourthOctet);          // stvrty oktet premeneny na BIN

        for (int i = 0; i < partIp.length ; i++)            // premena HEX IP na BIN IP do pola
        {
            fullIpBinary = fullIpBinary + hexbin(partIp[i]);
        }

        int countAvailableBits = 0;         //pocet bitov ktore musim pouzit aby sa dal vypocitat pocet kolko IP sa ma vypisat

        for (; subNumber > (int)Math.pow(2,countAvailableBits); countAvailableBits++)
        { }

        String [][] BinaryDoubleArray = new String [subNumber][countAvailableBits];// pomocne pole k vypoctom, naplna sa 1,2,4,8,16 ...

        int temp = 1;           // pomocna premenna
        int spy = 1;            // sledovacia premenna
        //cyklus na naplenie pola binarne
        for (int i = countAvailableBits -1; i >= 0 ; i--)
        {
            for (int j = 0 ; j < subNumber ; j++)
            {
                if(temp >= spy)
                {
                    BinaryDoubleArray[j][i] = "0";
                }
                else
                {
                    BinaryDoubleArray[j][i] = "1";
                    if (spy == temp * 2)
                    {
                        spy = 0;
                    }
                }
                spy++;
            }
            spy = 1;
            temp = temp * 2;
        }

        String partIpHex = "";
        String fullSubnettingArray[] = new String[subNumber]; // na konci tam budu vsetky IP ktore chce od nas uzivatel

        // rozdelenie 4 oktetku na 3 casti 2 cast sa nahradza vypocitanym polom. 1 a 2 cast sa opisuje
        String fourthOctetPartOne = fourthOctet.substring(0,prefix - 48);     // prva cast
        String fourthOctetPartThree="";         // tretia cast
        // ulozena tretia cast co je od konecneho prefixu - 48 + potrebnych bitov az po koniec
        fourthOctetPartThree = fourthOctet.substring(prefix-48 + countAvailableBits, fourthOctet.length());

        for (int i = 0; i < BinaryDoubleArray.length; i++)
        {
            fourthOctet = partIp[3];
            fourthOctet = hexbin(fourthOctet);
//            partIpBin = "";         // pomocna premenna
            String fourthOctetPartTwo = "";            // sluzi na premazanie
            for (int j = 0; j < BinaryDoubleArray[i].length; j++)       // spojenie subnetovaneho BIN oktetu s novym vypoctom a dopoctom celeho subnetu (16 BIN)
            {
                fourthOctetPartTwo = fourthOctetPartTwo + BinaryDoubleArray[i][j];
            }
            fourthOctet = fourthOctetPartOne + fourthOctetPartTwo + fourthOctetPartThree;       // spoja sa casti a vytvori sa jeden cely oktet
            partIpHex = binhex(fourthOctet);
            partIp[3] = partIpHex;          // zapise sa HEX na 4 oktet
            String fullIpHexAndHex4Octet = "";          //  do tejto premennej sa uklada cela IP v hex.

            for (int j = 0; j < partIp.length ; j++)            // vypisanie celej IP adresy
            {
                if (j == partIp.length-1)           //osetrenie aby sa nepridala ":" na koniec
                {
                    fullIpHexAndHex4Octet =  fullIpHexAndHex4Octet + partIp[j] + "/" + (prefix+countAvailableBits);
                    fullSubnettingArray[i] = fullIpHexAndHex4Octet;
                }
                else            // ak to nie je koniec ip medzi oktety sa prida ":"
                {
                    fullIpHexAndHex4Octet = fullIpHexAndHex4Octet + partIp[j] + ":";
                }
            }
        }

        String FullBinaryIpOnChangeToFinishConformation = "";       // finalna uprava aby sa odstranili 0
        String ipAddressHex = "";           // pomocny string ku BIN vypoctu adries

        for (int i = 0; i < fullSubnettingArray.length ; i++)
        {
            // vypocita sa network adresa a ulozi sa do premennej
            FullBinaryIpOnChangeToFinishConformation = Nw(fullSubnettingArray[i],prefix+countAvailableBits);
            ipAddressHex = binhex(FullBinaryIpOnChangeToFinishConformation);

            int helpWithFour = 0;           //tato premenna sluzi na oddelenie po 4 HEX cisla lebo dostavame celu HEX adresu vsetko spolu
            String IpOnSubnets = "";            //pomoc ked sa String opat rozlozi na Subnety
            String FullAddressHexWithPrefixAndDoubleDot = "";       //konecna upravena finalna zobrazena verzia

            for (int j = 0; j < 8 ; j++)
            {
                //berie postupne 4 cisla a dava za nich :
                IpOnSubnets = ipAddressHex.substring(helpWithFour, helpWithFour+ 4);
                if(j < 7)
                {
                    IpOnSubnets = IpOnSubnets + ":";
                }
                //Ak sa pridala : prida sa do CELKOVEJ IP ak nie tak sa pridaju len 4 hex cisla
                FullAddressHexWithPrefixAndDoubleDot = FullAddressHexWithPrefixAndDoubleDot + IpOnSubnets;
                // pripocita sa 4 aby sa posunul pocet ratania o 4
                helpWithFour = helpWithFour + 4;
            }
            // zavola sa metoda na kompresiu udajov
            try {
                FullAddressHexWithPrefixAndDoubleDot = getCompressedAddress(FullAddressHexWithPrefixAndDoubleDot);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            // prida sa prefix na koniec celej IP
            FullAddressHexWithPrefixAndDoubleDot = FullAddressHexWithPrefixAndDoubleDot + " /"+(prefix + countAvailableBits);
            // prida sa do pola adresa su tam vsetky adresy ktore chce po nas uzivatel
            fullSubnettingArray[i] = FullAddressHexWithPrefixAndDoubleDot;
        }

        // vrati naplnene pole adresami ktore pouzivatel chce
        return fullSubnettingArray;
    }

    //Broadcast
    public static String Bc(String IP, int prefix)
    {
        String partIP [] = IP.split(":");           // jeden oktet = jedno pole

        String temp = "";           // docasna premenna
        String fullIp = "";         // cela Ip adresa

        // spojenie bitov do adresy
        for (String x: partIP)
        {
            temp = hexbin(x);
            fullIp = fullIp + temp ;
        }

        // kazda 0 je nahradena 1
        String firstHalf = fullIp.substring(0,prefix);
        String secondHalf = fullIp.substring(prefix, fullIp.length());
        secondHalf = secondHalf.replaceAll("0","1");
        fullIp = firstHalf + secondHalf;

        return fullIp;
    }

    //metoda na pocitanie sietovej adresy
    public String Nw (String IP, int prefix)
    {
        String partIP [] = IP.split(":");
        String temp = "";
        String fullIp = "";
        // spojenie bitov  do plnej IP
        for (String x: partIP)
        {
            temp = hexbin(x);
            fullIp = fullIp + temp ;
        }
        String firstHalf = fullIp.substring(0,prefix);
        String secondHalf = fullIp.substring(prefix, fullIp.length());

        secondHalf = secondHalf.replaceAll("1","0");
        fullIp = firstHalf + secondHalf;
        return fullIp.toUpperCase();
    }

    // hexadecimal to decimal
    public static double hexdec(String hexNumber)           // hexNumber = Hexadecimal Number
    {
        String [] arr = new String[hexNumber.length()];
        for (int x = 0; x < arr.length ; x++)
        {
            char p = hexNumber.charAt(x);

            // input fix
            if (p != 'A' && p!= 'B'  && p != 'C' && p != 'D' && p!= 'E'  && p != 'F' && p != '1' && p!= '2'  && p != '3' && p != '4' && p!= '5'  && p != '6' && p != '7' && p!= '8'  && p != '9' )
            {
                break;
            }
            else
            {
                for (int j = 0; j < arr.length; j++)
                {
                    arr [j] = String.valueOf(hexNumber.charAt(j));
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
        return temp;
    }

    // Decimal to Hexadecimal
    public static String dechex(int decNum)         // decNumber = decimal Number
    {
        int temp = 0;
        char[] decarray = String.valueOf(decNum).toCharArray();         // decNumber do pola
        int [] decarrayarr = new int[decarray.length];          // docasne pole
        String [] result = new String[decarrayarr.length];      // vysledok
        String hexNum = "";

        for (int i = decarray.length - 1 ; i >= 0 ; i--)
        {
            temp = decNum % 16;
            decNum = decNum / 16;

            //zmena intu na String + convertovat decimal number to hexadecimal
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

        // oprava ak je 0 na prvej alebo prvej a druhej pozicii
        int m = 0;
        for (String u: result)
        {
            if (result[0] == "0" && result[1] == "0")
            {
                String helparray[] = new String[result.length-2];
                for (String p:helparray)
                {
                    helparray[m] = result[m+2];
                    p = helparray[m];
                    hexNum = hexNum + p;
                    m++;
                }
                break;
            }

            else if (result[0] == "0")          // ak 0 = 1 na prvej pozicii
            {
                String helparray[] = new String[result.length-1];
                for (String p:helparray)
                {
                    helparray[m] = result[m+1];
                    p = helparray[m];
                    hexNum = hexNum + p;
                    m++;
                }
                break;
            }
            hexNum = hexNum + u;
        }
        return hexNum;
    }

    // hexadecimal number to binary number
    public static String hexbin (String hex)            // hex = Hexadecimal number
    {
        char [] hexarray = String.valueOf(hex).toUpperCase().toCharArray();         // konvertovane pole + upper case
        String[] bin = new String[hexarray.length];         // finalne pole
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

    // Binary to Hexadecimal number
    public static String binhex(String bin)
    {
        String zero = "0";          // prida sa 0 na zaciatok ak nie je cislo delitlne 4
        for (int i = 0; bin.length() % 4 != 0 ; i++)
        {
            bin = zero + bin;
        }

        String fullIp = "";
        String fullBin = bin;
        int x = 0;
        for (int i = 0; i < fullBin.length()/4 ; i++)           // rozdelenie lebo 4 bin = 1 hex
        {
            bin = fullBin.substring(x,x+4);         // kontroluje kazde 4 bity
            x = x+4;
            switch (bin)
            {
                case "0000": bin = "0"; break;
                case "0001": bin = "1"; break;
                case "0010": bin = "2"; break;
                case "0011": bin = "3"; break;
                case "0100": bin = "4"; break;
                case "0101": bin = "5"; break;
                case "0110": bin = "6"; break;
                case "0111": bin = "7"; break;
                case "1000": bin = "8"; break;
                case "1001": bin = "9"; break;
                case "1010": bin = "A"; break;
                case "1011": bin = "B"; break;
                case "1100": bin = "C"; break;
                case "1101": bin = "D"; break;
                case "1110": bin = "E"; break;
                case "1111": bin = "F"; break;
            }
            fullIp = fullIp + bin;
        }
        return fullIp;
    }

    public  String isglobalUnicast(String ip)
    {
        String ipBin = hexbin(ip);
        if (ipBin.startsWith("001"))
        {
            return "áno";
        }
        return "nie";
    }

    public String isLoopback(String ip, int prefix)
    {
        if( ip == "::" && prefix == 128)
        {
            return "áno";
        }
        return "nie";
    }

    public static String linkLocal(String mac)
    {
        String[] macAdd;
        macAdd = mac.split(":");
        String[] dividedMac = new String[9];        // 9 preto lebo 0 = FE80: + FF + FE

        for (int i = 0; i < macAdd.length; i++) {
            dividedMac[i+1] = macAdd[i];
        }

        //posuvanie policok v poli
        dividedMac[8] = dividedMac[6];
        dividedMac[7] = dividedMac[5];
        dividedMac[6] = dividedMac[4];

        dividedMac[0] = "FE80:";
        dividedMac[4] = "FF";
        dividedMac[5] = "FE";

        // invertovanie 7 bitu
        String firstEightBits;
        firstEightBits = hexbin(dividedMac[1]);

        char firstEightBitsArr[] = firstEightBits.toCharArray();

        if (firstEightBitsArr[6] == '1')
        {
            firstEightBitsArr[6] = '0';
        }
        else if (firstEightBitsArr [6] == '0')
        {
            firstEightBitsArr[6] = '1';
        }

        // konecna MAC
        String fullMac = "";
        for (int i = 0; i <firstEightBitsArr.length ; i++)
        {
            fullMac = fullMac + String.valueOf(firstEightBitsArr[i]);

        }

        fullMac = binhex(fullMac);
        dividedMac[1] = fullMac;
        fullMac = "";

        for (int i = 0; i <dividedMac.length ; i++)
        {
            if (i < 8)
                fullMac = fullMac + dividedMac[i] + ":";
            else
                fullMac = fullMac +dividedMac[i];
        }
        return fullMac;
    }

    public static String siteLocal(String ip) {
        String BinAdd[] = ip.split(":");
        String FirstOctet = hexbin(BinAdd[0]);

        if (FirstOctet.startsWith("1111111011"))
            return "áno";
        else
            return "nie";
    }

    public String getPrefix ()
    {
        return Integer.toString(prefix);
    }

    public String getNwBin(String ip, int prefix) throws UnknownHostException
    {
        // poskalda IPv6 adresu ktoru compressuje
        String NwHexAddress = Nw(ip, prefix);
        NwHexAddress = binhex(NwHexAddress);
        String FinalNwHexAddress = "";

        int help = 0;
        for (int i = 0; i < 8; i++) {
            if (i < 7) {
                FinalNwHexAddress = FinalNwHexAddress + NwHexAddress.substring(help, help + 4) + ":";
            } else {
                FinalNwHexAddress = FinalNwHexAddress + NwHexAddress.substring(help, help + 4);
            }
            help = help + 4;
        }
        FinalNwHexAddress = getCompressedAddress(FinalNwHexAddress);
        return FinalNwHexAddress;
    }

    // vsetky parametre
    public String[] allIPv6Param() throws UnknownHostException {
        String [] returnAllParams = new String[10];
        returnAllParams [0] =  getCompressedAddress(ipv6Hex);
        returnAllParams [1] = getNwBin(ipv6Hex,prefix);
        returnAllParams [2] = getPrefix();
        returnAllParams [3] = isglobalUnicast(ipv6Hex);

        if (mac != null)
            returnAllParams [4] = linkLocal(mac);
        else
            returnAllParams[4] = "nebola zadaná MAC adresa";
        returnAllParams [5] = siteLocal(ipv6Hex);
        returnAllParams [6] = isLoopback(ipv6Hex,prefix);

        return returnAllParams;
    }

    //overovanie adresy
    public void validateIPv6Address()
    {
        partIp = ipv6Hex.split(":");
        if (partIp.length != 8)
            throw new IllegalArgumentException();

        for (String aPartIp : partIp)
        {
            if (aPartIp.length() != 4)
                throw new IllegalArgumentException();
        }
        // overi ci je adresa 0-9 A-F
        if (!IPAddressUtil.isIPv6LiteralAddress(ipv6Hex))
        {
            throw new IllegalArgumentException();
        }
    }
}
