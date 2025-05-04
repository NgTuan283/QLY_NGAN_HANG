
public class Main extends Menu {
    private TransactionHistory transactionService;
    private LoanManager loanManager;
    
    public Main() {
        super(new String[] {
            "HE THONG NGAN HANG",
            "QUAN LY KHOAN VAY",
            "Xem lich su giao dich",
            "Quan ly khoan vay",
            "Thoat"
        });
        this.transactionService = new TransactionHistory();
        this.loanManager = new LoanManager(transactionService);
    }
    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                transactionService.viewTransactionHistory();
                break;
            case 2:
                loanManager.run();
                break;
            case 3:
                System.out.println("Cam on ban da su dung dich vu");
                System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}