
import java.util.*;

public class Final {

    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Purple = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Normal = "\033[0m";

    public static void main(String[] args) {
        Player P = new Player("SnowMan", 'S');
        GirlFriend G = new GirlFriend("SnowGirl", 'G');
        ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy("Fire"));
        Enemies.add(new Enemy("Fire"));
        Enemies.add(new Enemy("Fire"));
        Enemies.add(new Enemy("Fire"));
        Enemies.add(new Enemy("Water"));
        Enemies.add(new Enemy("Water"));
        Enemies.add(new Enemy("Water"));
        Enemies.add(new Enemy("Water"));
        Enemies.add(new Enemy("Water"));
        Enemies.add(new Enemy("Water"));

        Scanner in = new Scanner(System.in);
        String Choice = " ";

        IntroScreen I = new IntroScreen();
        System.out.print(CLS);
        boolean isBossDisplayed = false;
        while (!Choice.equals("q") && P.HP > 0 && P.HP < 10000) {
            System.out.print(CLS);
            P.PrintWorld();
            System.out.println("HP:" + Red + P.HP + Normal + " Attack:" + Red + P.Attack + Normal /*+ "Armor:" + Red + P.Armor + Normal*/);
            System.out.println("Message: " + Cyan + P.Message + Normal);
            P.Message = "";
            System.out.println("Enter your command: ");
            Choice = in.nextLine();
            if (Choice.equals("d")) {
                P.MoveRight();
            }
            if (Choice.equals("a")) {
                P.MoveLeft();
            }
            if (Choice.equals("w")) {
                P.MoveUp();
            }
            if (Choice.equals("s")) {
                P.MoveDown();
            }

            for (int i = 0; i < Enemies.size(); i++) {
                if ((Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos + 1)) ||
                        (Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos - 1)) ||
                        (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos + 1)) ||
                        (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos - 1))) {
                    Enemies.get(i).HP -= P.Attack;
                    P.HP -= Enemies.get(i).Attack;
                    P.Message += "You are being Attacked! ";
                    // Here is a more advanced attack formula that utilizes attack and armor values.
                    // P.HP -= (100 * Enemies.get(i).Attack) / (100 + P.Armor);

                    if (Enemies.get(i).HP <= 0) {
                        P.World[Enemies.get(i).Xpos][Enemies.get(i).Ypos] = ' ';
                        P.Message += "You just killed the " + Enemies.get(i).Type;
                        Enemies.remove(i);
                    }
                }
            }

            for (int i = 0; i < Enemies.size(); i++) {
                if ((Math.abs(P.Xpos - Enemies.get(i).Xpos) <= Enemies.get(i).Range) &&
                        (Math.abs(P.Ypos - Enemies.get(i).Ypos) <= Enemies.get(i).Range) &&
                        (int) (Math.random() * 3) >= 1) {

                    if (Enemies.get(i).Xpos > P.Xpos)
                        Enemies.get(i).MoveLeft();
                    else
                        Enemies.get(i).MoveRight();
                    if (Enemies.get(i).Ypos > P.Ypos)
                        Enemies.get(i).MoveUp();
                    else
                        Enemies.get(i).MoveDown();
                } else {
                    int R = (int) (Math.random() * 4);
                    if (R == 0)
                        Enemies.get(i).MoveLeft();
                    else if (R == 1)
                        Enemies.get(i).MoveRight();
                    else if (R == 2)
                        Enemies.get(i).MoveUp();
                    else
                        Enemies.get(i).MoveDown();

                }
            }
            if (!isBossDisplayed && (Enemies.size() <= 0 || (P.Xpos == 37 && P.Ypos == 17 || P.Xpos == 37 && P.Ypos == 18 || P.Xpos == 37 && P.Ypos == 19 || P.Xpos == 38 && P.Ypos == 17 || P.Xpos == 39 && P.Ypos == 17))) {
                Enemies.add(new Enemy("Boss"));
                isBossDisplayed = true;
            }
        }


        LoserScreen L;
        if (P.HP <= 0) {
            System.out.print(CLS);
            L = new LoserScreen();
        }
        WinnerScreen W;
        if ( P.HP >= 10000) {
            System.out.println(CLS);
            W = new WinnerScreen();
        }
    }
}

class GameObject {

    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Purple = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Normal = "\033[0m";

    static char  World[][] = new char[41][21];
    int Xpos, Ypos;
    char Avatar;
    static String Message = "";

    void PrintWorld() {
        for (int y = 1; y <= 20; y++) {
            for (int x = 1; x <= 40; x++) {

                if (World[x][y] == ' ')
                    System.out.print(' ');
                else if (World[x][y] == '*')
                    System.out.print(Yellow + '*' + Normal);
                else if (World[x][y] == 'W')
                    System.out.print(Cyan + 'W' + Normal);
                else if (World[x][y] == 'F')
                    System.out.print(Red + 'F' + Normal);
                else if (World[x][y] == 'S')
                    System.out.print(Blue + 'S' + Normal);
                else if (World[x][y] == 'G')
                    System.out.print(Blue + 'G' + Normal);
                else if (World[x][y] == '+')
                    System.out.print(Green + '+' + Normal);
                else if (World[x][y] == '@')
                    System.out.print(Green + '@' + Normal);
                else if (World[x][y] == 'B')
                    System.out.print(Green + 'B' + Normal);
                else
                    System.out.print(World[x][y]);

                if (x < 40) System.out.print(" ");
            }
            System.out.println();
        }
    }

    void MoveRight() {
        if (World[Xpos + 1][Ypos] == ' ') {
            World[Xpos][Ypos] = ' ';
            Xpos++;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveLeft() {
        if (Xpos > 0 && World[Xpos - 1][Ypos] == ' ') {
            World[Xpos][Ypos] = ' ';
            Xpos--;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveUp() {
        if (Ypos > 0 && World[Xpos][Ypos - 1] == ' ') {
            World[Xpos][Ypos] = ' ';
            Ypos--;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveDown() {
        if (World[Xpos][Ypos + 1] == ' ') {
            World[Xpos][Ypos] = ' ';
            Ypos++;
            World[Xpos][Ypos] = Avatar;
        }
    }
}

class Enemy extends GameObject {
    String Type;
    int HP, Attack, Armor, Range;

    Enemy(String theType) {
        Type = theType;
        Xpos = (int) (Math.random() * 34) + 6;
        Ypos = (int) (Math.random() * 14) + 6;

        if (Type.equals("Water")) {
            Avatar = 'W';
            HP = 50;
            Attack = 10;
            Armor = 30;
            Range = 8;
        }
        if (Type.equals("Fire")) {
            Xpos = (int) (Math.random() * 10) + 29;
            Ypos = (int) (Math.random() * 6) + 14;
            Avatar = 'F';
            HP = 80;
            Attack = 20;
            Armor = 40;
            Range = 5;
        }
        if (Type.equals("Boss")){
            Xpos = 38;
            Ypos = 18;
            Avatar = 'B';
            HP = 110;
            Attack = 30;
            Range = 3;
        }
        World[Xpos][Ypos] = Avatar;

    }
}

class Player extends GameObject {
    int HP, Attack, Armor, Gold;
    String Name;

    Player(String theName, char theAvatar) {
        HP = 200;
        Attack = 20;
        Armor = 30;
        Name = theName;
        Avatar = theAvatar;
        Xpos = 2;
        Ypos = 2;
        for (int y = 1; y <= 20; y++) {
            for (int x = 1; x <= 40; x++) {
                World[x][y] = ' ';
            }
        }
        for (int x = 1; x <= 40; x++) {
            World[x][1] = '*';
            World[x][20] = '*';
        }
        for (int y = 1; y <= 20; y++) {
            World[1][y] = '*';
            World[40][y] = '*';
        }
        for (int i = 1; i <= 5; i++) {
            World[(int) (Math.random() * 36) + 4][(int) (Math.random() * 14) + 6] = '+';
        }
        for (int i =1; i <= 3; i++){
            World[(int) (Math.random() * 36) + 4][(int) (Math.random() * 14) + 6] = '@';
        }
        World[Xpos][Ypos] = Avatar;
    }


    void MoveRight() {
        if (World[Xpos + 1][Ypos] == ' ' || World[Xpos + 1][Ypos] == '+' || World[Xpos+1][Ypos] == 'G' || World[Xpos +1][Ypos] == '@') {
            if (World[Xpos + 1][Ypos] == '+') {
                HP += 20;
                Message += "Health Pack +20 HP ";
            }
            if (World[Xpos + 1][Ypos] == '@') {
                Attack += 10;
                Message += "Attack Buff +10 Attack ";
            }
            else if (World[Xpos+1][Ypos] == 'G')
                HP += 10500;
            World[Xpos][Ypos] = ' ';
            Xpos++;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveLeft() {
        if (World[Xpos - 1][Ypos] == ' ' || World[Xpos - 1][Ypos] == '+'|| World[Xpos-1][Ypos] == 'G'|| World[Xpos -1][Ypos] == '@') {
            if (World[Xpos - 1][Ypos] == '+') {
                HP += 20;
                Message += "Health Pack +20 HP ";
            }
            if (World[Xpos - 1][Ypos] == '@') {
                Attack += 10;
                Message += "Attack Buff +10 Attack ";
            }
            else if (World[Xpos-1][Ypos] == 'G')
                HP += 10500;
            World[Xpos][Ypos] = ' ';
            Xpos--;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveUp() {
        if (World[Xpos][Ypos - 1] == ' '|| World[Xpos][Ypos-1] == '+'|| World[Xpos][Ypos-1] == 'G'|| World[Xpos][Ypos-1] == '@') {
            if (World[Xpos][Ypos-1] == '+') {
                HP += 20;
                Message += "Health Pack +20 HP ";
            }
            if (World[Xpos ][Ypos-1] == '@') {
                Attack += 10;
                Message += "Attack Buff +10 Attack ";
            }
            else if (World[Xpos][Ypos-1] == 'G')
                HP += 10500;
            World[Xpos][Ypos] = ' ';
            Ypos--;
            World[Xpos][Ypos] = Avatar;
        }
    }

    void MoveDown() {
        if (World[Xpos][Ypos + 1] == ' '|| World[Xpos][Ypos+1] == '+'|| World[Xpos][Ypos+1] == 'G'|| World[Xpos][Ypos+1] == '@') {
            if (World[Xpos][Ypos+1] == '+') {
                HP += 20;
                Message += "Health Pack +20 HP ";
            }
            if (World[Xpos ][Ypos+1] == '@') {
                Attack += 10;
                Message += "Attack Buff +10 Attack ";
            }
            else if (World[Xpos][Ypos+1] == 'G')
                HP += 10500;
            World[Xpos][Ypos] = ' ';
            Ypos++;
            World[Xpos][Ypos] = Avatar;
        }
    }
}

class GirlFriend extends GameObject {
    String Name;
    GirlFriend(String theName, char theAvatar){
        Xpos = 39;
        Ypos = 19;
        Name = theName;
        Avatar = theAvatar;
        World[Xpos][Ypos] = Avatar;
    }
}

class IntroScreen {
    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Purple = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Normal = "\033[0m";

    IntroScreen(){
        Scanner in = new Scanner(System.in);

        System.out.println(Yellow+ "  ________  _____  ___      ______    __   __  ___      ___      ___       __      _____  ___   ");
        System.out.println("  /\"       )(\\\"   \\|\"  \\    /    \" \\  |\"  |/  \\|  \"|    |\"  \\    /\"  |     /\"\"\\    (\\\"   \\|\"  \\  ");
        System.out.println(" (:   \\___/ |.\\\\   \\    |  // ____  \\ |'  /    \\:  |     \\   \\  //   |    /    \\   |.\\\\   \\    | ");
        System.out.println("  \\___  \\   |: \\.   \\\\  | /  /    ) :)|: /'        |     /\\\\  \\/.    |   /' /\\  \\  |: \\.   \\\\  | ");
        System.out.println("   __/  \\\\  |.  \\    \\. |(: (____/ //  \\//  /\\'    |    |: \\.        |  //  __'  \\ |.  \\    \\. |  ");
        System.out.println("  /\" \\   :) |    \\    \\ | \\        /   /   /  \\\\   |    |.  \\    /:  | /   /  \\\\  \\|    \\    \\ | ");
        System.out.println(" (_______/   \\___|\\____\\)  \\\"_____/   |___/    \\___|    |___|\\__/|___|(___/    \\___)\\___|\\____\\)  " + Normal);

        System.out.println(Blue +"                     .------, " +Normal);
        System.out.println(Blue +"       .\\/.          |______| " +Normal);
        System.out.print(Blue +"     _\\_}{_/_       _|_Ll___|_ " +Normal);    System.out.println("                   To win this game, the snowman needs to meet his girlfriend.");
        System.out.print(Blue +"      / }{ \\       [__________]          .\\/. " +Normal);  System.out.println("    The enemies will block his way. Get rid of them!");
        System.out.println(Blue +"       '/\\'        /          \\        _\\_\\/_/_ " +Normal);
        System.out.print(Blue +"                  ()  o  o    ()        / /\\ \\ " +Normal);  System.out.println(Red+"   Move key"+Normal);
        System.out.print(Blue +"                   \\ ~~~   .  /          '/\\' " +Normal);  System.out.println(Purple+"    D"+Normal+" Move to the right" + Purple+ "  A" +Normal +" Move to the left");
        System.out.print(Blue +"              _\\/   \\ '...'  /    \\/_ " +Normal);  System.out.println(Purple+"            W"+Normal+" Move up" + Purple+ "            S" +Normal +" Move down");
        System.out.println(Blue +"               \\\\   {`------'}    // " +Normal);
        System.out.print(Blue +"                \\\\  /`---/',`\\\\  // " +Normal);  System.out.println(Red + "              Enemies" + Normal);
        System.out.print(Blue +"                 \\/'  o  | |\\ \\`// " +Normal);  System.out.println(Purple+"               W" + Normal + " Water (HP: 50  Attack: 10)");
        System.out.print(Blue +"                 /'      | | \\/ /\\ " +Normal);  System.out.println(Purple+"               F" + Normal + " Fire  (HP: 80  Attack: 20)");
        System.out.print(Blue +"    __,. -- ~~ ~|    o   `\\|      |~ ~~ -- . __ " +Normal);  System.out.println(Purple+"  B" + Normal + " Boss  (HP: 110 Attack: 30)");
        System.out.println(Blue +"                |                 | " +Normal);
        System.out.print(Blue +"           jgs  \\    o            / " +Normal);  System.out.println(Red+ "              Items" + Normal);
        System.out.print(Blue +"                 `._           _.' " +Normal);  System.out.println(Purple+"              +" + Normal + " Health Pack (HP +20)");
        System.out.print(Blue +"                    ^~- . -  ~^ " +Normal);  System.out.println(Purple+"                 @" + Normal + " Attack Buff (Attack +10)");

        System.out.println();
        System.out.println(Green+"Press <Enter> to start"+Normal);
        in.nextLine();
    }


}

class WinnerScreen {

    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Purple = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Normal = "\033[0m";

    WinnerScreen(){

        System.out.println(Blue+"                           ::::::    .-~~\\        ::::::  "+ Normal);
        System.out.println(Blue+"                           |::::|   /     \\ _     |::::| "+ Normal);
        System.out.println(Blue+"                   _ _     l~~~~!   ~x   .-~_)_   l~~~~! "+ Normal);
        System.out.println(Blue+"                .-~   ~-.   \\  /      ~x\".-~   ~-. \\  / "+ Normal);
        System.out.println(Blue+"         _     /         \\   ||    _  ( /         \\ || "+ Normal);
        System.out.println(Blue+"         ||   T  o  o     Y  ||    ||  T o  o      Y|| "+ Normal);
        System.out.println(Blue+"       ==:l   l   <       !  (3  ==:l  l  <        !(3 "+ Normal);
        System.out.println(Blue+"          \\\\   \\  .__/   /  /||     \\\\  \\  ._/    / || "+ Normal);
        System.out.println(Blue+"           \\\\ ,r\"-,___.-'r.//||      \\\\,r\"-,___.-'r/|| "+ Normal);
        System.out.println(Blue+"            }^ \\.( )   _.'//.||      }^\\. ( )  _.-//|| "+ Normal);
        System.out.println(Blue+"           /    }~Xi--~  //  ||     /   }~Xi--~  // ||\\ "+ Normal);
        System.out.println(Blue+"          Y    Y I\\ \\    \"   ||    Y   Y I\\ \\    \"  || Y "+ Normal);
        System.out.println(Blue+"          |    | |o\\ \\       ||    |   | |o\\ \\      || | "+ Normal);
        System.out.println(Blue+"          |    l_l  Y T      ||    |   l_l  Y T     || | "+ Normal);
        System.out.println(Blue+"          l      \"o l_j      |!    l     \"o l_j     || ! "+ Normal);
        System.out.println(Blue+"           \\                 ||     \\               ||/ "+ Normal);
        System.out.println(Blue+"         .--^.     o       .^||.  .--^.     o       ||--. "+ Normal);
        System.out.println(Blue+"              \"           ~  `'        \"           ~`' "+ Normal);

        System.out.println(Yellow+"You Won!!!"+Normal);
        System.out.println(Yellow+"They lived together happily ever after.");

    }

}

class LoserScreen {

    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Purple = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Normal = "\033[0m";

    LoserScreen(){

        System.out.println(White+" , ,    ,      ,    ,     ,     ,   ,      ,     ,     ,      ,      , " + Normal);
        System.out.println(White+",       ,     ,    ,       ,   .____. ,   ,     ,      ,       ,      , " + Normal);
        System.out.println(White+" ,    ,   ,    ,     ,   ,   , |   :|         ,   , ,   ,   ,       , " + Normal);
        System.out.println(White+"   ,        ,    ,     ,     __|====|__ ||||||  ,        ,      ,      , " + Normal);
        System.out.println(White+" ,   ,    ,   ,     ,    , *  / o  o \\  ||||||,   ,  ,        ,    , " + Normal);
        System.out.println(White+",   ,   ,         ,   ,     * | -=   |  \\====/ ,       ,   ,    ,     , " + Normal);
        System.out.println(White+"   ,  ,    ,   ,           , U==\\__//__. \\\\//    ,  ,        ,    , " + Normal);
        System.out.println(White+",   ,  ,    ,    ,    ,  ,   / \\\\==// \\ \\ ||  ,   ,      ,          , " + Normal);
        System.out.println(White+" ,  ,    ,    ,     ,      ,|    o ||  | \\||   ,      ,     ,   ,     , " + Normal);
        System.out.println(White+",      ,    ,    ,      ,   |    o \"\"  |\\_|B),    ,  ,    ,       , " + Normal);
        System.out.println(White+"  ,  ,    ,   ,     ,      , \\__  --__/   ||  ,        ,      ,     , " + Normal);
        System.out.println(White+",  ,   ,       ,     ,   ,  /          \\  ||,   ,   ,      ,    ,    , " + Normal);
        System.out.println(White+" ,      ,   ,     ,        |            | ||      ,  ,   ,    ,   , " + Normal);
        System.out.println(White+",    ,    ,   ,  ,    ,   ,|            | || ,  ,  ,   ,   ,     ,  , " + Normal);
        System.out.println(White+" ------_____---------____---\\__ --_  __/__LJ__---------________-----___ " + Normal);

        System.out.println(Yellow+"You lost!!!"+Normal);
        System.out.println(Yellow+ "The snowman waited for his girl friend his whole life in the snow. ");

    }

}

