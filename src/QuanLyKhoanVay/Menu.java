

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {
    private List<String> luaChon = new ArrayList<>();
    protected static final Scanner sc = new Scanner(System.in);

    public Menu() {}

    public Menu(String[] inLuaChon) {
        setMenu(inLuaChon);
    }

    public void setMenu(String[] inLuaChon) {
        luaChon.clear();
        for (String lc : inLuaChon) {
            luaChon.add(lc);
        }
    }

    public void display() {
        if (luaChon.size() < 2) {
            System.out.println("Menu khong hop le.");
            return;
        }

        System.out.println("\n\n\t\t\t" + luaChon.get(1) + "\n");
        System.out.println("-------- " + luaChon.get(0) + " --------");

        for (int i = 2; i < luaChon.size(); i++) {
            System.out.println((i - 1) + ". " + luaChon.get(i));
        }
        System.out.println("---------- *** ----------");
    }

    public int getSelected() {
        int choice;
        while (true) {
            display();
            System.out.print("Moi chon muc: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice > 0 && choice < luaChon.size() - 1) {
                    return choice;
                } else if (choice == luaChon.size() - 1) {
                    System.out.println("\n\t\tHen gap lai quy khach!");
                    System.exit(0);
                } else {
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Phai nhap vao so nguyen.");
            }
        }
    }

    public abstract void execute(int n);

        public void run() {
            while (true) {
                try {
                    int lc = getSelected();
                    execute(lc);
                } catch (MenuExitException e) {
                    return;
                }
            }
        }
    
    public List<String> getLuaChon() {
        return luaChon;
    }

    public void setLuaChon(List<String> luaChon) {
        this.luaChon = luaChon;
    }
}

class MenuExitException extends RuntimeException {
    public MenuExitException() {
        super("MENU_EXIT");
    }
}