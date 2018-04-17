package spse;

public class IPv4
{
    //static
    private final int IPV4_LENGTH = 4; //konstanta

    //ipv4 host adresa zadana uzivatelom
    private int[] ipv4DEC = new int[IPV4_LENGTH];
    private String[] ipv4BIN = new String[IPV4_LENGTH];
    private String fullBinAddress, firstPart, secondPart, binOrder, classIP, typeIP;
    private int decOrder, addressCount;
    private int[] decNW, decBC, decMask, decWildcard, decFirstAddress, decLastAddress;
    private int prefix;

    //VLSM params
    private int neededSize, allocatedSize;
    String name;


    //IPv4 zakkladne parametre konstruktor
    public IPv4 (int[] ipv4DEC, int prefix)
    {
        this.ipv4DEC = ipv4DEC;
        this.prefix = prefix;
    }

    //VLSM konstruktor
    public IPv4 (String name, int neededSize)
    {
        this.name = name;
        this.neededSize = neededSize + 2;
    }

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

    //metoda na zistenie LEN POTREBNYCH PARAMETROV VLSM supernetu/subnetu
    public void makeVLSMNwParameters()
    {
        convertToBIN();
        splitIP();

        networkIPaddress();
        broadcastIPaddress();
        maskIPaddress();
        firstUsableIPaddress();
        lastUsableIPaddress();
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

        int[] decArray = new int[IPV4_LENGTH];

        for (int i = 0; i < binArray.length; i++)
            decArray[i] = Converter.binToDEC(binArray[i]);

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
        String[] dividedBinaryIP = new String[IPV4_LENGTH];

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
         decOrder = Converter.binToDEC(binOrder) + 1;
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
        decWildcard = new int[IPV4_LENGTH];

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
        //pocet vsetkych adries
        addressCount = (int) (Math.pow(2, (32 - prefix)));
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

    public int getAddressCount()
    {
        return addressCount;
    }

    public int getDecOrder()
    {
        return decOrder;
    }

    public String getClassIP()
    {
        return classIP;
    }

    public String getTypeIP()
    {
        return typeIP;
    }


    //VLSM metody
    public void allocateCorrectSize()
    {
        int x;
        for (int i = 0; i < 32; i++)
        {
            x = (int) Math.pow(2, i);

            if (x > neededSize)
            {
                this.prefix = 32 - i;

                allocatedSize = x;
                break;
            }
        }
    }

    // getters/setters for VLSM
    public String getName() {
        return name;
    }

    public int getAllocatedSize()
    {
        return allocatedSize;
    }

    public int getNeededSize() {
        return neededSize;
    }

    public int[] getintNW()
    {
        return decNW;
    }

    public int[] getintBC()
    {
        return decBC;
    }

    public void setIpv4DEC(int[] ipv4DEC)
    {
        this.ipv4DEC = ipv4DEC;
    }
}
