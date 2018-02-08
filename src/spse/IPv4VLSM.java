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
     * ----
     * DONE vypocet moznosti VLSM
     * todo v pripade chyb urobit dialogove okna podla typu chyby !
     *
     * ---
     *
     *  zoradenie poctu host adries od najvacsej DONE
     * zistenie poctu adries v supernete
     *
     *
     * --
     * validacia ipv4 adresy   !>255 DONE
     *
     * --
     * todo vypocet danych adries na zaklade velkosti siete a prefixu
     * todo urcenie NW a BC adresy + prefix
     *
     *
     * todo na zaciatku dialog s poctom sieti nasledne generovanie textfieldov
     *
     *
     *
     * TODO STVORCOVE DELENIE SIETE
     */

    //todo vo controlleri nacitat pole hostov, nasledne cez settter nastavit





    int superNetPrefix, supernetHostsCount, subnetsCount;
    int[] supernetIPv4, nextIP;
    String superNet;
    //todo lepsie nazvy premennych, urobit poriadok!
    IPv4 supernetObj;
    IPv4[] subnet;






    // todo, tu zavolat IPV4 metodu 'VLSM' na zistenie NW adresy



    public IPv4VLSM(String superNet, int subnetsCount)
    {
        this.superNet = superNet.trim();
        this.subnetsCount = subnetsCount;
        //todo allthe stuff();
//        magic();
    }

    public void magic()
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

        System.out.println("celkovy pocet hostov: " + supernetHostsCount );

    }

    private void createIPv4objects()
    {
        subnet = new IPv4[subnetsCount];

        for (int i = 0; i < subnet.length; i++)
        {
            //todo docasne
//            int[] subnetHostsCount = {64,3,11,56,333,128};
            int[] subnetHostsCount = {1234,213,3213,5623,21312,23128};



            subnet[i] = new IPv4("Siet " + i, subnetHostsCount[i]);
            subnet[i].doVLSM();

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

        System.out.println("hosts supernet: " + supernetHostsCount);
        System.out.println("hosts subnets total: " + spaceNeeded);
    }

    private void sortSubnets()
    {
        for (int i = 0; i < subnet.length - 1; i++)
        {
            for (int j = 0; j < subnet.length - i - 1; j++)
            {
//                if(subnet[j].getAllocatedSize() < subnet[j+1].getAllocatedSize())
                if(subnet[j].getNeededSize() < subnet[j+1].getNeededSize())
                {
                    IPv4 tmp = subnet[j];
                    subnet[j] = subnet[j+1];
                    subnet[j+1] = tmp;
                }
            }
        }


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("!!!!!!!");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        //just to test subnets sorting
        for (int i = 0; i < subnet.length; i++) {

            subnet[i].printInfoVLSM();
        }
    }

    private void getParametersOfSupernet()
    {
        supernetObj = new IPv4(supernetIPv4, superNetPrefix);
        supernetObj.supernetVLSM();

        nextIP = supernetObj.getintNW().clone();
//        System.out.println(nextIP[0]);
//        System.out.println(nextIP[1]);
//        System.out.println(nextIP[2]);
//        System.out.println(nextIP[3]);

//        System.exit(0);

    }

    //real VLSM starts here :D

    private void doSubnetting()
    {
        for (int i = 0; i < subnet.length; i++)
        {
            subnet[i].setIpv4DEC(nextIP);

            //todo premenovanie metody aby davala vacsi zmysel
            subnet[i].supernetVLSM();

            nextIP = subnet[i].getintBC().clone();

            //TODO osetrit vsetky stavy ip!!!!
            if (nextIP[3] != 255)
            {
                nextIP[3] += 1;
            }
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
