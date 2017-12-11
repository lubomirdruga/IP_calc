package spse;
// todo rename package

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    int prefix;



    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("spse.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        Scanner sc = new Scanner(System.in);


        //zadana ip adresa od uzivatela
        int[] ipv4DEC = new int[4];
        String[] ipv4BIN = new String[4];


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



        //rozdelenie ip adresy podla prefixu
        //first part.. sietova cast
        //second part .. hostova cast

        String firstPart = fullBinAddress.substring(0, prefix);
        String secondPart = fullBinAddress.substring(prefix, 32);

        String binOrder = secondPart;
        int decOrder = Converter.toDEC(binOrder);



        // vyrobi nam sietovu adresu siete
        secondPart = secondPart.replace("1","0");
        String binNWAddress = firstPart + secondPart;

        //rozdelenie BIN IP do pola, ktore premenime na DEC IPv4
        int[] decNW = new int[4];

        String[] binNW = new String[4];
        binNW[0] = binNWAddress.substring(0,8);
        binNW[1] = binNWAddress.substring(8,16);
        binNW[2] = binNWAddress.substring(16,24);
        binNW[3] = binNWAddress.substring(24,32);




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


        //premena cisel z BIN sustavy do DEC sustavy
        for (int i = 0; i < binBC.length; i++)
        {
            decNW[i] = Converter.toDEC(binNW[i]);
            decBC[i] = Converter.toDEC(binBC[i]);
        }


        System.out.println();
        //System.out.println("NW address in Bin: " + binNWAddress);
        System.out.println("Your BC IP in BIN: " + binNW[0] + "." + binNW[1] + "." + binNW[2] + "." + binNW[3]);

        System.out.println("Your NW IP in DEC: " + decNW[0] + "." + decNW[1] + "." + decNW[2] + "." + decNW[3]);
        System.out.println();

        //System.out.println("BC address in Bin: " + binBCAddress);
        System.out.println("Your BC IP in BIN: " + binBC[0] + "." + binBC[1] + "." + binBC[2] + "." + binBC[3]);

        System.out.println("Your BC IP in DEC: " + decBC[0] + "." + decBC[1] + "." + decBC[2] + "." + decBC[3]);
        System.out.println();



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


        //vytvorenie wildcard masky

        int[] decWildcard = new int[4];
        decWildcard[0] = 255 - decMask[0];
        decWildcard[1] = 255 - decMask[1];
        decWildcard[2] = 255 - decMask[2];
        decWildcard[3] = 255 - decMask[3];
        System.out.println("Your MASK in DEC: " + decWildcard[0] + "." + decWildcard[1] + "." + decWildcard[2] + "." + decWildcard[3]);


        //prva pouzitelna adresa
        int[] decFirstAddress = decNW.clone();
        decFirstAddress[3] += 1;
        System.out.println("Your first usable IP: " + decFirstAddress[0] + "." + decFirstAddress[1] + "." + decFirstAddress[2] + "." + decFirstAddress[3]);

        //posledna pouzitelna adresa
        int[] decLastAddress = decBC.clone();
        decLastAddress[3] -= 1;
        System.out.println("Your last usable IP: " + decLastAddress[0] + "." + decLastAddress[1] + "." + decLastAddress[2] + "." + decLastAddress[3]);


        //pocet pouzitelnych adries
        int addressCount = (int) (Math.pow(2, (32 - prefix)) - 2);


        /**
         *  vypis konkretnych parametrov IPv4 adresy pokope
         */


        System.out.println("##############################");
        System.out.println("PARAMETRE SIETE");
        System.out.println("##############################");


        System.out.println("Host adresa:"  + ipv4DEC[0] + "." + ipv4DEC[1] + "." + ipv4DEC[2] + "." + ipv4DEC[3]);
        System.out.println("Prefix siete:" + prefix);
        //todo overit poradie
        System.out.println("Poradie host adresy:" + decOrder);
        System.out.println("Maska siete:" + decMask[0] + "." + decMask[1] + "." + decMask[2] + "." + decMask[3]);
        System.out.println("Wildcard: " + decWildcard[0] + "." + decWildcard[1] + "." + decWildcard[2] + "." + decWildcard[3]);

        System.out.println();
        System.out.println("Sietova adresa:" + decNW[0] + "." + decNW[1] + "." + decNW[2] + "." + decNW[3]);
        System.out.println("Broadcast adresa:" + decBC[0] + "." + decBC[1] + "." + decBC[2] + "." + decBC[3]);

        System.out.println("Prva pouzitelna adresa: " + decFirstAddress[0] + "." + decFirstAddress[1] + "." + decFirstAddress[2] + "." + decFirstAddress[3]);
        System.out.println("Posledna pouzitelna adresa: " + decLastAddress[0] + "." + decLastAddress[1] + "." + decLastAddress[2] + "." + decLastAddress[3]);

        System.out.println("Pocet adries: " + (addressCount + 2));
        System.out.println("Pocet pouzitelnych adries: " + addressCount);
        //todo
        System.out.println("Trieda: " );
        System.out.println("Typ adresy, verejna private");












//              prejde vs DEC cisla do BIN
//        for (int i = 0; i <= 255; i++)
//        {
//            System.out.print(i +  ": ");
//            Converter.toBinary(i);
//            System.out.println();
//        }


        System.exit(0);

    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
