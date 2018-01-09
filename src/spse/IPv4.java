package spse;

import java.util.Scanner;

public class IPv4
{
    //static
    final int IPv4length = 4; //konstanta

    //ipv4 host adresa zadana uzivatelom
    int[] ipv4DEC = new int[IPv4length];
    String[] ipv4BIN = new String[IPv4length];
    String fullBinAddress, firstPart, secondPart, binOrder, classIP, typeIP;
    int decOrder, addressCount;
    int[] decNW, decBC, decMask, decWildcard, decFirstAddress, decLastAddress;


    int prefix;

    //todo SIDEBAR


    public IPv4 (int[] ipv4DEC, int prefix)
    {
        // todo boolean vlsm
        //todo toto alebo dva rozne konstruktory, bude to mat vacsi zmysel! :)

        this.ipv4DEC = ipv4DEC;
        this.prefix = prefix;

        calculateAllIPinformations();
        // calucluate needed for VLSM();
    }


    // todo dve public metody na zakladne zistenie parametrov IP
    // a to jedno pre vlsm a druhe pre zakladne parametre/vsetky

    //ostatne budu private


    public void calculateAllIPinformations()
    {
        convertToBIN();
        splitIP();
        IPorder();
        networkIPaddress();
        broadcastIPaddress();
        maskIPaddress();
        wildcardIPaddress();
        firstUsableIPaddress();
        lastUsableIPaddress();
        IPadressCount();
        IPtype();
        IPclass();
    }

    private void convertToBIN()
    {
        for (int i = 0; i < ipv4BIN.length; i++)
            ipv4BIN[i] = Converter.toBinary(ipv4DEC[i]);

        fullBinAddress = ipv4BIN[0] + ipv4BIN[1] + ipv4BIN[2] + ipv4BIN[3];

    }

    private int[] convertToDEC(String[] binArray)
    {
        //premena cisel z BIN sustavy do DEC sustavy

        int[] decArray = new int[IPv4length];

        for (int i = 0; i < binArray.length; i++)
            decArray[i] = Converter.toDEC(binArray[i]);

        return decArray;
    }

    private void splitIP()
    {
        //rozdelenie ip adresy podla prefixu
        //first part.. sietova cast
        //second part .. hostova cast

        firstPart = fullBinAddress.substring(0, prefix);
        secondPart = fullBinAddress.substring(prefix, 32);
    }

    private String[] substringBinaryIP(String binaryIP)
    {

        String[] dividedBinaryIP = new String[IPv4length];

        dividedBinaryIP[0] = binaryIP.substring(0,8);
        dividedBinaryIP[1] = binaryIP.substring(8,16);
        dividedBinaryIP[2] = binaryIP.substring(16,24);
        dividedBinaryIP[3] = binaryIP.substring(24,32);

        return dividedBinaryIP;
    }

    private void IPorder()
    {
        //poradie adresy
         binOrder = secondPart;
         decOrder = Converter.toDEC(binOrder) + 1;
    }

    private void networkIPaddress()
    {
        // vyrobi nam sietovu adresu siete
        secondPart = secondPart.replace("1","0");
        String binNWAddress = firstPart + secondPart;

        String[] binNW = substringBinaryIP(binNWAddress);
        //vysledna sietova IP adresa v DEC tvare
        decNW = convertToDEC(binNW);
    }



    private void broadcastIPaddress()
    {
        // vyrobi nam BC adresu danej siete
        secondPart = secondPart.replaceAll("0", "1");
        String binBCAddress = firstPart + secondPart;

        String[] binBC = substringBinaryIP(binBCAddress);

        //rozdelenie BIN IP do pola, ktore premenime na DEC IPv4
        decBC = convertToDEC(binBC);
    }

    private void maskIPaddress()
    {
        //vytvorenie masky

        String binMaskfull = "";

        for (int i = 0; i < 32; i++)
        {
            if (i < prefix)
                binMaskfull += "1";
            else
                binMaskfull += "0";
        }

        String[] binMask = substringBinaryIP(binMaskfull);

        decMask = convertToDEC(binMask);
    }


    private void wildcardIPaddress()
    {
        //vytvorenie wildcard masky
        decWildcard = new int[IPv4length];

        for (int i = 0; i < decWildcard.length; i++)
            decWildcard[i] = 255 - decMask[i];
    }


    private void firstUsableIPaddress()
    {
        //prva pouzitelna adresa
        decFirstAddress = decNW.clone();
        decFirstAddress[3] += 1;
    }


    private void lastUsableIPaddress()
    {
        //posledna pouzitelna adresa
        decLastAddress = decBC.clone();
        decLastAddress[3] -= 1;
    }


    private void IPadressCount()
    {
        //pocet pouzitelnych adries
        addressCount = (int) (Math.pow(2, (32 - prefix)) - 2);

    }

    private void IPclass()
    {
        if (decNW[0] <= 127)
            classIP = "A";
        else if (decNW[0] <= 191)
            classIP = "B";
        else if (decNW[0] <= 223)
            classIP = "C";
        else if (decNW[0] <= 239)
            classIP = "D";
        else
            classIP = "E";
    }

    private void IPtype()
    {
        if (ipv4DEC[0] == 10 && prefix >= 8 ||
                ipv4DEC[0] == 172 && ipv4DEC[1] >= 16 && ipv4DEC[1] >= 32 && prefix >= 12 ||
                ipv4DEC[0] == 192 && ipv4DEC[1] == 168 && prefix >= 16)
            typeIP = "privátna";
        else if (ipv4DEC[0] == 127 && prefix >= 8)
            typeIP = "localhost";
        else
            typeIP = "verejná";
    }


    //getters to GUI

    public String getDecNW()
    {
        return decNW[0] + "." + decNW[1] + "." + decNW[2] + "." + decNW[3];
    }

    public String getPrefix()
    {
        return Integer.toString(prefix);
    }

    public String getDecBC()
    {
        return decBC[0] + "." + decBC[1] + "." + decBC[2] + "." + decBC[3];
    }

    public String getDecMask()
    {
        return decMask[0] + "." + decMask[1] + "." + decMask[2] + "." + decMask[3];
    }

    public String getDecWildcard()
    {
        return decWildcard[0] + "." + decWildcard[1] + "." + decWildcard[2] + "." + decWildcard[3];
    }

    public String getDecFirstAddress()
    {
        return decFirstAddress[0] + "." + decFirstAddress[1] + "." + decFirstAddress[2] + "." + decFirstAddress[3];
    }

    public String getDecLastAddress()
    {
        return decLastAddress[0] + "." + decLastAddress[1] + "." + decLastAddress[2] + "." + decLastAddress[3];
    }

    public String getAddressCount()
    {
        return Integer.toString(addressCount);
    }

    public String getDecOrder()
    {
        return Integer.toString(decOrder);
    }

    public String getClassIP()
    {
        return classIP;
    }

    public String getTypeIP()
    {
        return typeIP;
    }

}
