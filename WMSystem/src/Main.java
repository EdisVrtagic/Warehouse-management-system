import com.edisvrtagicipia.example.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginP lp = new LoginP();
                lp.setVisible(true);
                lp.setLocationRelativeTo(null);
            }
        });
    }
}