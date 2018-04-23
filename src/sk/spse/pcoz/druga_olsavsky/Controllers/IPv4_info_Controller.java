package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IPv4_info_Controller implements Initializable {
    public TextFlow textFlow;
    public ScrollPane scrollPane;
    public JFXHamburger hamburger;
    public JFXDrawer drawer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            VBox box = FXMLLoader.load(getClass().getResource("../Views/Drawer.fxml"));
            drawer.setSidePane(box);
        } catch (IOException ex) {
            System.out.println("File 'Drawer.fxml' not found");
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
        });

        String str = "Internet Protocol version 4 (IPv4) je štvrtá verzia internetového protokolu (IP).\n" +
                "\n" +
                "Jedná sa o jeden z hlavných protokolov, ktorý bol do produkcie nasadený v roku 1983.\n" +
                "\n" +
                "V súčasnosti stále prechádza väčšina internetového prenosu napriek nasadeniu následného protokolu IPv6. IPv4 je opísaná v publikácii IETF RFC 791 (september 1981), ktorá nahrádza predchádzajúcu definíciu (RFC 760, január 1980).\n" +
                "\n" +
                "\n" +
                "\n" +
                "Adresy\n" +
                "\n" +
                "Adresy sú dnes používané ale pomaly ich začínajú vytláčať adresy IPv6.\n" +
                "\n" +
                "Môžu byť reprezentované v ľubovoľnej notácii vyjadrujúcej 32-bitovú celočíselnú hodnotu. Najčastejšie sa píšu v desatinnej notácii, ktorá sa skladá zo štyroch oktetov adresy, vyjadrených jednotlivo v desiatkových číslach a oddelených bodkami. Norma notácie CIDR spája adresu s jej predponou smerovania v kompaktnom formáte, v ktorom je za adresou nasleduje znak lomítka (/) a počet po sebe idúcich 1 bitov v predpise smerovania (maska podsiete/prefix).\n" +
                "\n" +
                "32 bitové adresy, ktoré limitujú adresný priestor na 4 294 967 296 (2^32) adries.\n" +
                "\n" +
                "IPv4 rezervuje špeciálne bloky adries pre privátne siete (~ 18 miliónov adries) a multicast adresy (~ 270 miliónov adries).\n" +
                "\n" +
                "\n" +
                "Príklad zápisu: \n" +
                "\n" +
                "\t\tDEC 172.16.254.1\n" +
                "\t\tBIN 10101100.00010000.11111110.00000001\n" +
                "\n" +
                "\n" +
                "\n" +
                "Hlavička IPv4:\n" +
                "\n" +
                "4 bity - verzia\n" +
                "4 bity - dĺžka internetovej hlavičky (IHL)\n" +
                "8 bitov - typ služby \n" +
                "16 bitov - celková dĺžka\n" +
                "\n" +
                "\n" +
                "Privátne IPv4 adresy:\n" +
                "\n" +
                "\tA - 10.0.0.0 - 10.255.255.255\n" +
                "\tB - 172.16.0.0 - 172.31.255.255\n" +
                "\tC - 192.168.0.0 - 192.168.255.255\n" +
                "\n" +
                "\n" +
                "Sieťová adresa\n" +
                "\n" +
                "Je to úplne prvá adresa siete, ktorá sa využíva k jednoznačnej identifikácií siete. Túto adresu môžeme vypočítať pomocou zadanej IPv4 ale aj IPv6 adresy ktoré majú zadané aj masky resp. prefixy. V tejto časti je vysvetlená len  problematika IPv4 adresy z dôvodu, že postup je úplne rovnaký pri oboch IP. Princíp počítania je v podstate veľmi jednoduchý. V prvom kroku si adresu prevedieme do binárneho tvaru. Následne si odpočítame počet čísel podľa zadaného prefixu. Ak máme prefix napríklad 24 tak po 24-tom čísle všetky ostatné jednotky, ktoré nasledujú ďalej v IP jednoducho zmeníme na nuly. Adresu stačí premeniť späť do decimálneho alebo hexadecimálneho(podľa verzie IP) tvaru a tým získame hľadanú sieťovú adresu.\n" +
                "\n" +
                "\n" +
                "Broadcast adresa\n" +
                "\n" +
                "Je posledná použiteľná adresa z rozsahu siete. Taktiež je to označenie pre správu, ktorú príjmu všetky pripojené rozhrania v danej sieti. Broadcast adresu môžeme rozdeliť na MAC broadcast, IP broadcast a sieťový broadcast.\n" +
                "\n" +
                "\n" +
                "VLSM –  variable length subnet mask \n" +
                "\n" +
                "Venuje sa problematike delenia siete na základe požiadaviek t. j. na požadovanom počte zariadení.\n" +
                "Na začiatku sa požadovaným podsieťam určí veľkosť v podobe prefixu. Veľkosť sa určuje podľa počtu zariadení v danej sieti no vždy musíme pripočítať minimálne plus dve použiteľné adresy pretože broadcast  a sieťová adresa sa nemôžu používať na priradenie k zariadeniu. Následne sa zistí, či zadaná sieť je v sieťovom tvare. Ak je sieť už v sieťovom tvare tak sa hľadá broadcast prefixu hľadanej podsiete. Po nájdení broadcastu sa môže pridať plus jedna adresa ktorá urobí adresu siete ďalšej hľadanej v poradí alebo sa znova začíname hľadať sieťovú adresu celým prepočítavaním, čo je oveľa dlhší spôsob hľadania rovnakého výsledku. Celý proces začína od najväčších sietí a postupne sa prepracovávame k menším.  Toto opakujme až kým nenájdeme najmenšiu sieť.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Naznámejšie rezervované adresy:\n" +
                "\n" +
                "\t0.0.0.0/8\t\t\t0.0.0.0 - 0.255.255.255   - Používané pre boradcast\n" +
                "\t10.0.0.0/8 \t\t10.0.0.0 - 10.255.255.255  - Lokálna komunikácia\n" +
                "\t100.64.0.0/10\t\t100.64.0.0 - 100.127.255.255 - Používané pre komunikáciu klient - provider\n" +
                "\t127.0.0.0/8\t\t127.0.0.0 - 127.255.255.255 - Loopback\n" +
                "\t169.254.0.0/16\t169.254.0.0 - 169.254.255.255 - LinkLocal adresy - medzi klientami\n" +
                "\t192.0.2.0/24\t\t192.0.2.0 – 192.0.2.255     - používané pre dokumentáciu, nepoužívajú sa verejne\n" +
                "\t192.88.99.0/24 \t192.88.99.0 – 192.88.99.255   - 6to4 anycast relay adresy\n" +
                "\t224.0.0.0/4\t\t224.0.0.0 – 239.255.255.255 - rezervované pre multicast\n" +
                "\t255.255.255.255\t- Broadcast\n" +
                "\n" +
                "\n" +
                "\tPrefix \t\tMaska\t\t\t\tPočet adries\t\t\tWildcard\n" +
                "\t/32 \t\t255.255.255.255\t\t\t1\t\t\t\t\t0.0.0.0\n" +
                "\t/31 \t\t255.255.255.254\t\t\t2 \t\t\t\t\t0.0.0.1\n" +
                "\t/30 \t\t255.255.255.252\t\t\t4 \t\t\t\t\t0.0.0.3\n" +
                "\t/29 \t\t255.255.255.248\t\t\t8\t\t\t\t\t0.0.0.7\n" +
                "\t/28 \t\t255.255.255.240\t\t\t16 \t\t\t\t\t0.0.0.15\n" +
                "\t/27 \t\t255.255.255.224\t\t\t32 \t\t\t\t\t0.0.0.31\n" +
                "\t/26 \t\t255.255.255.192\t\t\t64 \t\t\t\t\t0.0.0.63\n" +
                "\t/25 \t\t255.255.255.128\t\t\t128 \t\t\t\t\t0.0.0.127\n" +
                "\t/24 \t\t255.255.255.0\t\t\t256\t\t\t\t\t0.0.0.255\n" +
                "\t/23 \t\t255.255.254.0\t\t\t512 \t\t\t\t\t0.0.1.255\n" +
                "\t/22 \t\t255.255.252.0\t\t\t1 024 \t\t\t\t0.0.3.255\n" +
                "\t/21 \t\t255.255.248.0\t\t\t2 048\t\t\t\t0.0.7.255\n" +
                "\t/20 \t\t255.255.240.0\t\t\t4 096 \t\t\t\t0.0.15.255\n" +
                "\t/19 \t\t255.255.224.0\t\t\t8 192 \t\t\t\t0.0.31.255\n" +
                "\t/18 \t\t255.255.192.0\t\t\t16 384 \t\t\t\t0.0.63.255\n" +
                "\t/17 \t\t255.255.128.0\t\t\t32 768 \t\t\t\t0.0.127.255\n" +
                "\t/16 \t\t255.255.0.0\t\t\t\t65 536 \t\t\t\t0.0.255.255\n" +
                "\t/15 \t\t255.254.0.0\t\t\t\t131 072 \t\t\t\t0.1.255.255\n" +
                "\t/14 \t\t255.252.0.0\t\t\t\t262 144 \t\t\t\t0.3.255.255\n" +
                "\t/13 \t\t255.248.0.0\t\t\t\t524 288 \t\t\t\t0.7.255.255\n" +
                "\t/12 \t\t255.240.0.0\t\t\t\t1 048 576 \t\t\t0.15.255.255\n" +
                "\t/11 \t\t255.224.0.0\t\t\t\t2 097 152 \t\t\t0.31.255.255\n" +
                "\t/10 \t\t255.192.0.0\t\t\t\t4 194 304 \t\t\t0.63.255.255\n" +
                "\t/9 \t\t255.128.0.0\t\t\t\t8 388 608 \t\t\t0.127.255.255\n" +
                "\t/8 \t\t255.0.0.0\t\t\t\t16 777 216 \t\t\t0.255.255.255\n" +
                "\t/7 \t\t254.0.0.0\t\t\t\t33 554 432 \t\t\t1.255.255.255\n" +
                "\t/6 \t\t252.0.0.0\t\t\t\t67 108 864 \t\t\t3.255.255.255\n" +
                "\t/5 \t\t248.0.0.0\t\t\t\t134 217 728 \t\t\t7.255.255.255\n" +
                "\t/4 \t\t240.0.0.0\t\t\t\t268 435 456 \t\t\t15.255.255.255\n" +
                "\t/3 \t\t224.0.0.0\t\t\t\t536 870 912 \t\t\t31.255.255.255\n" +
                "\t/2 \t\t192.0.0.0\t\t\t\t\t1 073 741 824 \t\t63.255.255.255\n" +
                "\t/1 \t\t128.0.0.0\t\t\t\t\t2 147 483 648 \t\t127.255.255.255\n" +
                "\t/0 \t\t0.0.0.0\t\t\t\t\t4 294 967 296 \t\t255.255.255.255\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Decimal to Binary\n" +
                "\n" +
                "\tSubnet Mask \t\tWildcard\n" +
                "\t255 - 1111 1111 \t0   - 0000 0000\n" +
                "\t254 - 1111 1110 \t1   - 0000 0001\n" +
                "\t252 - 1111 1100 \t3   - 0000 0011\n" +
                "\t248 - 1111 1000 \t7   - 0000 0111\n" +
                "\t240 - 1111 0000 \t15  - 0000 1111\n" +
                "\t224 - 1110 0000 \t31  - 0001 1111\n" +
                "\t192 - 1100 0000 \t63  - 0011 1111\n" +
                "\t128 - 1000 0000 \t127 - 0111 1111\n" +
                "\t0   - 0000 0000 \t255 - 1111 1111\n" +
                "\n" +
                "\n";


        Text text = new Text(str);
        textFlow.getChildren().add(text);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
