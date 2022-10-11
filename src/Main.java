import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Game start");
        JFrame window = new JFrame("TicTacToe"); // наше главное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // добавляем кнопку "Х", закрываюзую окно
        window.setSize(400,400); // указываем размер окна в пикселях
        window.setLayout(new BorderLayout()); // менедже компановки
        window.setLocationRelativeTo(null); // делаем так чтобы окно было по центру экрана
        window.setVisible(true); // ключаем видимость окнка
        TicTacToe game = new TicTacToe();
        window.add(game); // добавляем обект в окно
        System.out.println("Final");
    }
}
