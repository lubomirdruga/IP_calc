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
     *
     * TODO STVORCOVE DELENIE SIETE
     */

    //todo vo controlleri nacitat pole hostov, nasledne cez settter nastavit


    private int superNetPrefix, supernetHostsCount, subnetsCount;
    private int[] supernetIPv4, nextIP, subnetsArray;
    private String superNet;
    private IPv4[] subnet;



    public IPv4VLSM(String superNet, int subnetsCount, int[] subnetsArray)
    {
        this.superNet = superNet.trim();
        this.subnetsCount = subnetsCount;
        this.subnetsArray = subnetsArray;
    }

    public void start() throws Exception {
        splitAndValidate();
        countSuperNetHosts();
        createIPv4objects();
        checkSubnettingPossibility();
        sortSubnets();
        getParametersOfSupernet();

        doSubnetting();
    }


    private void splitAndValidate() throws Exception {
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
        catch (IOException e) {
            throw new IOException();
        }
        catch (NumberFormatException e)
        {
            throw new NumberFormatException();
        }
        catch (Exception e)
        {
            throw new Exception();
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

            subnet[i] = new IPv4(str(i), subnetsArray[i]);
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
        IPv4 supernetObj = new IPv4(supernetIPv4, superNetPrefix);
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

    public IPv4[] getSubnet() {
        return subnet;
    }

    private String str(int i) {
        return i < 0 ? "" : str((i / 26) - 1) + (char)(65 + i % 26);
    }
}
