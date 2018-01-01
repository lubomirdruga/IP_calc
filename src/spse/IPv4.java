package spse;

import java.util.Scanner;

public class IPv4
{
    //static
    final int IPv4length = 4; //konstanta

    //ipv4 host adresa zadana uzivatelom
    int[] ipv4DEC = new int[IPv4length];
    String[] ipv4BIN = new String[IPv4length];
    String fullBinAddress, firstPart, secondPart, binOrder, classIP;
    int decOrder, addressCount;
    int[] decNW, decBC, decMask, decWildcard, decFirstAddress, decLastAddress;


    int prefix;

    //todo premenovat dec --> decimal           bin --> binary !!

    // todo metoda na konvertovanie ipv4 dec pola na string vo forme IP(string), na nasledny vypis do grafiky/Label


    public IPv4 (int[] ipv4DEC, int prefix)
    {
        // todo boolean vlsm

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
         //todo dec order +1 kvoli nultej IP
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

        System.out.println("binary mask: " + binMaskfull);

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

    public String getClassIP()
    {
        return classIP;
    }

    //todo dorobit gettre na triedu a typ IP

    //todo doplnit do GUI poradie!
    public int getDecOrder() {
        return decOrder;
    }




















    public void doMagic()
    {
//        Scanner sc = new Scanner(System.in);
//
//        //zadana ip adresa od uzivatela
//        int[] ipv4DEC = new int[4];
//        String[] ipv4BIN = new String[4];

/**
        //todo pri VLSM osetrit mensie siete
        // todo delete after graphics implementation
        for (int i = 0; i < ipv4DEC.length; i++)
        {
            System.out.println("Type here IPv4 octets separated by enter:");
            System.out.print(i + 1 + ": ");

            do {
                //System.out.println("Number in IPv4 octet cant be greater than 255! ");
                ipv4DEC[i] = sc.nextInt();

            }
            while (ipv4DEC[i] > 255);

            ipv4BIN[i] = Converter.toBinary(ipv4DEC[i]);
        }

        System.out.println("Your IP in DEC: " + ipv4DEC[0] + "." + ipv4DEC[1] + "." + ipv4DEC[2] + "." + ipv4DEC[3]);
        System.out.println("Your IP in BIN: " + ipv4BIN[0] + "." + ipv4BIN[1] + "." + ipv4BIN[2] + "." + ipv4BIN[3]);

*/


/**
        // NW address
        System.out.println("******************************************");


        do {
            System.out.print("Zadaj prefix: ");
            prefix = sc.nextInt();

            if (prefix > 32)
                System.out.println("ZLy prefix! Zadaj znova");
        }
        while (prefix > 32);




        System.out.println("Prefix: " + prefix);


        String fullBinAddress = ipv4BIN[0] + ipv4BIN[1] + ipv4BIN[2] + ipv4BIN[3];
        // System.out.println(fullBinAddress);

*/





        //todo hereee

/**
        //rozdelenie ip adresy podla prefixu
        //first part.. sietova cast
        //second part .. hostova cast

        String firstPart = fullBinAddress.substring(0, prefix);
        String secondPart = fullBinAddress.substring(prefix, 32);
*/

/**
        //poradie adresy
        String binOrder = secondPart;
        int decOrder = Converter.toDEC(binOrder);

 */


   /**     // vyrobi nam sietovu adresu siete
        secondPart = secondPart.replace("1","0");
        String binNWAddress = firstPart + secondPart;

        //rozdelenie BIN IP do pola, ktore premenime na DEC IPv4
        int[] decNW = new int[4];

        String[] binNW = new String[4];
        binNW[0] = binNWAddress.substring(0,8);
        binNW[1] = binNWAddress.substring(8,16);
        binNW[2] = binNWAddress.substring(16,24);
        binNW[3] = binNWAddress.substring(24,32);

*/

/**
        // vyrobi nam BC adresu danej siete
        secondPart = secondPart.replaceAll("0", "1");
        String binBCAddress = firstPart + secondPart;

        //rozdelenie BIN IP do pola, ktore premenime na DEC IPv4
        int[] decBC = new int[4];

        String[] binBC = new String[4];
        binBC[0] = binBCAddress.substring(0,8);
        binBC[1] = binBCAddress.substring(8,16);
        binBC[2] = binBCAddress.substring(16,24);
        binBC[3] = binBCAddress.substring(24,32);

*/


/**
        //hotova samostatna metoda, vseobecna
        //premena cisel z BIN sustavy do DEC sustavy
        for (int i = 0; i < binBC.length; i++)
        {
            decNW[i] = Converter.toDEC(binNW[i]);
            decBC[i] = Converter.toDEC(binBC[i]);
        }

*/


/**
 *  todo vypisy

 System.out.println();
        //System.out.println("NW address in Bin: " + binNWAddress);
        System.out.println("Your BC IP in BIN: " + binNW[0] + "." + binNW[1] + "." + binNW[2] + "." + binNW[3]);

        System.out.println("Your NW IP in DEC: " + decNW[0] + "." + decNW[1] + "." + decNW[2] + "." + decNW[3]);
        System.out.println();

        //System.out.println("BC address in Bin: " + binBCAddress);
        System.out.println("Your BC IP in BIN: " + binBC[0] + "." + binBC[1] + "." + binBC[2] + "." + binBC[3]);

        System.out.println("Your BC IP in DEC: " + decBC[0] + "." + decBC[1] + "." + decBC[2] + "." + decBC[3]);
        System.out.println();

*/
/**
        //vytvorenie masky

        String binMaskfull = "";

        for (int i = 0; i < 32; i++)
        {
            if (i < prefix)
                binMaskfull += "1";
            else
                binMaskfull += "0";
        }

        System.out.println("binary mask: " + binMaskfull);

        //todo  create method to do this
        String[] binMask = new String[4];
        binMask[0] = binMaskfull.substring(0,8);
        binMask[1] = binMaskfull.substring(8,16);
        binMask[2] = binMaskfull.substring(16,24);
        binMask[3] = binMaskfull.substring(24,32);

        int[] decMask = new int[4];

        for (int i = 0; i < binMask.length; i++)
        {
            decMask[i] = Converter.toDEC(binMask[i]);
        }


        System.out.println("Your MASK in DEC: " + decMask[0] + "." + decMask[1] + "." + decMask[2] + "." + decMask[3]);
*/


/**
        //vytvorenie wildcard masky
        //todo metoda a cyklus
        int[] decWildcard = new int[4];
        decWildcard[0] = 255 - decMask[0];
        decWildcard[1] = 255 - decMask[1];
        decWildcard[2] = 255 - decMask[2];
        decWildcard[3] = 255 - decMask[3];
        System.out.println("Your MASK in DEC: " + decWildcard[0] + "." + decWildcard[1] + "." + decWildcard[2] + "." + decWildcard[3]);
*/

/**
        //prva pouzitelna adresa
        int[] decFirstAddress = decNW.clone();
        decFirstAddress[3] += 1;
        System.out.println("Your first usable IP: " + decFirstAddress[0] + "." + decFirstAddress[1] + "." + decFirstAddress[2] + "." + decFirstAddress[3]);
*/

 /**
        //posledna pouzitelna adresa
        int[] decLastAddress = decBC.clone();
        decLastAddress[3] -= 1;
        System.out.println("Your last usable IP: " + decLastAddress[0] + "." + decLastAddress[1] + "." + decLastAddress[2] + "." + decLastAddress[3]);

*/

 /**
        //pocet pouzitelnych adries
        int addressCount = (int) (Math.pow(2, (32 - prefix)) - 2);
*/



 /////////
        /**
         *  vypis konkretnych parametrov IPv4 adresy pokope
         */

//
//        System.out.println("##############################");
//        System.out.println("PARAMETRE SIETE");
//        System.out.println("##############################");
//
//
//        System.out.println("Host adresa:"  + ipv4DEC[0] + "." + ipv4DEC[1] + "." + ipv4DEC[2] + "." + ipv4DEC[3]);
//        System.out.println("Prefix siete:" + prefix);
//        //todo overit poradie
//        System.out.println("Poradie host adresy:" + decOrder);
//        System.out.println("Maska siete:" + decMask[0] + "." + decMask[1] + "." + decMask[2] + "." + decMask[3]);
//        System.out.println("Wildcard: " + decWildcard[0] + "." + decWildcard[1] + "." + decWildcard[2] + "." + decWildcard[3]);
//
//        System.out.println();
//        System.out.println("Sietova adresa:" + decNW[0] + "." + decNW[1] + "." + decNW[2] + "." + decNW[3]);
//        System.out.println("Broadcast adresa:" + decBC[0] + "." + decBC[1] + "." + decBC[2] + "." + decBC[3]);
//
//        System.out.println("Prva pouzitelna adresa: " + decFirstAddress[0] + "." + decFirstAddress[1] + "." + decFirstAddress[2] + "." + decFirstAddress[3]);
//        System.out.println("Posledna pouzitelna adresa: " + decLastAddress[0] + "." + decLastAddress[1] + "." + decLastAddress[2] + "." + decLastAddress[3]);
//
//        System.out.println("Pocet adries: " + (addressCount + 2));
//        System.out.println("Pocet pouzitelnych adries: " + addressCount);
//        //todo
//        System.out.println("Trieda: " );
//        System.out.println("Typ adresy, verejna private");
//


    }










}
