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

public class IPv6_info_Controller implements Initializable {

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


        String str = "IPv6 je verzia 6 Internet protokolu (IP),pôvodne sa volala IP Next Generation. Má nahradiť predchádzajúci štandard IPv4, ktorý podporuje \"iba\" niečo viac ako 4 miliardy (2^32) adries.\n" +
                "\n" +
                "Hlavným dôvodom vytvorenia IPv6 bol nedostatok adresného priestoru, obzvlášť v husto obývaných krajinách Ázie ako sú India a Čína.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Zloženie adresy\n" +
                "\n" +
                "IPv6 adresa sa skladá z dvoch logických častí: \n" +
                "\t64-bitového prefixu siete,\n" +
                "\t64-bitovej adresy zariadenia v sieti, ktorá sa často generuje automaticky z adresy rozhrania (MAC adresa).\n" +
                "\n" +
                "\n" +
                "\n" +
                "IPv4 adresu je jednoducho možné previesť na IPv6 adresu. \n" +
                "\n" +
                "Napríklad desiatkovo zapísaná IPv4 adresa bola 135.75.43.52 (hexadecimálne 0x874B2B34), \n" +
                "je možné ju konvertovať do:\n" +
                "\n" +
                "\t0000:0000:0000:0000:0000:0000:874B:2B34 \n" +
                "\talebo\n" +
                "\t::874B:2B34.\n" +
                "\n" +
                "\n" +
                "Potom je zasa možné použiť hybridný zápis (IPv4-kompatibilnú adresu), kedy by adresa bola ::135.75.43.52. Použitie týchto IPv4-kompatibilných adries sa neodporúča, pretože ich IPv6 prechodové mechanizmy už nepoužívajú.\n" +
                "\n" +
                "\n" +
                "Pakety pozostávajú z riadiacich informácií na adresovanie, smerovanie a z užívateľských údajov. \n" +
                "Pakety protokolu IPv6 sa zvyčajne prenášajú cez linkové protokoly, ako je napríklad Ethernet, ktorý zapúzdruje každý paket do rámca, ale môže to byť aj protokol o tunelovaní vyššej vrstvy, ako napríklad IPv4 pri použití technológií 6to4 alebo Teredo.\n" +
                "\n" +
                "\n" +
                "IPv6 je 128- bitová adresa, zapisuje sa ako 8 skupín po 4 \n" +
                "hexadecimálnych číslach\n" +
                "Počet adries je 2^128\n" +
                "\n" +
                "Formáty:\n" +
                "\n" +
                "Global unicast:\n" +
                "\tprvých 48 bitov = Global prefix\n" +
                "\tnásledujúcich 16 bitov = Subnet ID\n" +
                "\tposledných 64 bitov = Interface ID\n" +
                "\n" +
                "Link-local unicast:\n" +
                "\n" +
                "\tprvých 64 bitov = (FE80::/64)\n" +
                "\tnasledujúcich 64 bitov = Interface ID\n" +
                "\n" +
                "Multicast\n" +
                "\n" +
                "8 bitov = FF\n" +
                "4 bity = flags\n" +
                "4 bity = scope\n" +
                "112 = Group ID\n" +
                "\n" +
                "\n" +
                "\n" +
                "Hlavička tvorí prvých 40 bajtov paketu a obsahuje:\n" +
                "zdrojovú aj cieľovú adresu (každá o dĺžke 128 bitov),\n" +
                "verziu (4-bitovú verziu IP), \n" +
                "triedu premávky (8 bitov, priorita paketu), \n" +
                "značku toku (20 bitov, manažment QoS), \n" +
                "dĺžku nákladu (16 bitov),\n" +
                "offset ďalšej hlavičky (8 bitov), \n" +
                "limit skokov (8 bitov, Time To Live). \n" +
                "\n" +
                "Potom nasleduje náklad, ktorý môže mať v štandardnom režime až 64 KB alebo viac.\n" +
                "\n" +
                "Skracovanie Ipv6:\n" +
                "Hextet :0000: môžeme nahradiť :: ak nasleduje viac takýchto hextetov po sebe tak ich skrátime tiež na ::\n" +
                "Ak hextet začína nulou napr. :0123: nulu vynecháme (:123:)\n" +
                "\n" +
                "\n" +
                "\n" +
                "Typy adries: \n" +
                "\tUnicast (komunitácia 1:1)\n" +
                "\tMulticast (komunikácia 1:N)\n" +
                "\tAnycast (Adresa konfigurovaná na viacerých miestach)\n" +
                "\n" +
                "\n" +
                "\n" +
                "EUI-64 (Link-Local výpočet)\n" +
                " -počíta sa z MAC adresy zariadenia\n" +
                " -postupujeme takto:\n" +
                "\n" +
                "\t1. vezmeme MAC adresu\n" +
                "\t2. rozdelíme ju presne v polovici aby sme mali 3 a 3 hextety\n" +
                "\t3. medzi ne vložíme :FF:FE:\n" +
                "\t4. v prvom hextete prevrátime siedmy bit \n" +
                "\t5. na začiatok vložíme FE80::\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Špeciálne vyhradené adresy:\n" +
                "\n" +
                "\t::/0 default route\n" +
                "\t::/128 nešpecifikovné\n" +
                "\t::1/128 loopback\n" +
                "\t::/96 IPv4 kompatibilné adresy\n" +
                "\t::FFFF:0:0/96 IPv4 mapované adresy používa sa v Dual Stack\n" +
                "\t2001::/32 Teredo - zapuzdruje IPv6 cez UDP cez IPv4\n" +
                "\t2001:DB8::/32 Dokumentácia\n" +
                "\t2002::/16 6to4 - automatický asymetrický tunelovací mechanizmus\n" +
                "\tFC00::/7 Unikátna local adresa\n" +
                "\tFE80::/10 LinkLocal unicast - adresy slúžia pre komunikáciu so susedom\n" +
                "\tFEC0::/10 SiteLocal unicast - adresy používame vo vnútri siete - už sa nepoužíva \n" +
                "\tFF00::/8 Multicast - adresy sú ako náhrada SiteLocal a privátnych Ipv4, používajú sa vo vnútri siete pre komunikáciu, nesmie sa smerovať do internetu.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Prechodové mechanizmy\n" +
                "\n" +
                "Dual Stack - Prechod z IPv4 na IPv6. Umožňuje jednotlivým hostiteľom využívať oba protokoly naraz.\n" +
                "Tunelovanie - Ipv6 cez sieť IPv4, konfigurácia Dual-stack medzi 2 uzlami alebo 6to4 \n" +
                "Preklad (translation) - SIIT prekladá IP hlavičku, NAT Protocol Translation (NAT-PT) mapy medzi IPv6 a Ipv4 adresami.\n";


        Text text = new Text(str);
        textFlow.getChildren().add(text);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
