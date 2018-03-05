package spse;

import java.io.IOException;
import java.util.regex.Pattern;

public class IPv4VLSM
{
    /**
     *
     *      VSTUP
     *
     * pocet sieti
     * DONE / check / todo pocet hostov bude vstup jedneho objeku IPv4!
     * supernet
     * prefix
     *
     * todo v pripade chyb urobit dialogove okna podla typu chyby !
     *
     * todo na zaciatku dialog s poctom sieti nasledne generovanie textfieldov
     *
     * TODO STVORCOVE DELENIE SIETE
     */

    //todo vo controlleri nacitat pole hostov, nasledne cez settter nastavit


    private int superNetPrefix, supernetHostsCount, subnetsCount;
    private int[] supernetIPv4, nextIP;
    private String superNet;
    //todo lepsie nazvy premennych, urobit poriadok!
    private IPv4 supernetObj;
    private IPv4[] subnet;



    public IPv4VLSM(String superNet, int subnetsCount)
    {
        this.superNet = superNet.trim();
        this.subnetsCount = subnetsCount;
    }

    public void start()
    {
        splitAndValidate();
        countSuperNetHosts();
        createIPv4objects();
        checkSubnettingPossibility();
        sortSubnets();
        getParametersOfSupernet();

        doSubnetting();
    }


    private void splitAndValidate()
    {
        try
        {
            String ip = superNet.substring(0, superNet.indexOf("/"));
            superNetPrefix = Integer.parseInt(superNet.substring(superNet.indexOf("/") + 1, superNet.length()).trim());

            String [] splitedIP = ip.split(Pattern.quote("."));
            supernetIPv4 = new int[splitedIP.length];


            for (int i = 0; i < supernetIPv4.length; i++)
            {
                supernetIPv4[i] = Integer.parseInt(splitedIP[i].trim());
                if (supernetIPv4[i] > 255 || supernetIPv4[i] < 0)
                    throw new IOException();

                System.out.println(supernetIPv4[i]);
            }

            System.out.println(superNetPrefix);
        }
        //todo vsekty mozne exceptions
        catch (IOException e)
        {
            System.out.println("zadane cislo nie je z rozsahu IPv4 (0 - 255)");
        }
        catch (NumberFormatException e)
        {
            System.out.println("chyba pri parsovani cisla ");
        }
        catch (Exception e)
        {
            System.out.println("chyba");
        }
    }

    private void countSuperNetHosts ()
    {
        supernetHostsCount = (int) Math.pow(2, 32 - superNetPrefix);
    }

    private void createIPv4objects()
    {
        subnet = new IPv4[subnetsCount];

        for (int i = 0; i < subnet.length; i++)
        {
            //todo docasne
            int[] subnetHostsCount = {2,2,2,2};

            subnet[i] = new IPv4("Siet " + i, subnetHostsCount[i]);
            subnet[i].allocateCorrectSize();
        }
    }

    private void checkSubnettingPossibility()
    {
        int spaceNeeded = 0;

        for (int i = 0; i < subnet.length; i++)
            spaceNeeded += subnet[i].getAllocatedSize();

        if (supernetHostsCount > spaceNeeded)
            System.out.println("je mozne subnetovat");
        else
            System.out.println("NIE je mozne subnetovat");
    }

    private void sortSubnets()
    {
        for (int i = 0; i < subnet.length - 1; i++)
        {
            for (int j = 0; j < subnet.length - i - 1; j++)
            {
                if(subnet[j].getNeededSize() < subnet[j+1].getNeededSize())
                {
                    IPv4 tmp = subnet[j];
                    subnet[j] = subnet[j+1];
                    subnet[j+1] = tmp;
                }
            }
        }
    }

    private void getParametersOfSupernet()
    {
        supernetObj = new IPv4(supernetIPv4, superNetPrefix);
        supernetObj.makeVLSMNwParameters();

        nextIP = supernetObj.getintNW().clone();
    }

    //real VLSM starts here :D
    private void doSubnetting()
    {
        for (int i = 0; i < subnet.length; i++)
        {
            subnet[i].setIpv4DEC(nextIP);
            subnet[i].makeVLSMNwParameters();

            nextIP = subnet[i].getintBC().clone();

            if (nextIP[3] != 255)
                nextIP[3] += 1;
            else if (nextIP[2] != 255)
            {
                nextIP[3] = 0;
                nextIP[2] += 1;
            }
            else if (nextIP[1] != 255)
            {
                nextIP[3] = 0;
                nextIP[2] = 0;
                nextIP[1] += 1;
            }
            else
            {
                nextIP[3] = 0;
                nextIP[2] = 0;
                nextIP[1] = 0;
                nextIP[0] += 1;
            }
        }

        //todo remove
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("## FINAL SUBNETS ##");

        for (int i = 0; i < subnet.length; i++) {

            System.out.println();
            System.out.println(subnet[i].name);
            subnet[i].supernetInfo();
        }
    }
}
