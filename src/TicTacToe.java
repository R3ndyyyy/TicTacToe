import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TicTacToe extends JComponent {
    public static final int FIELD_EMPTY = 0;
    public static final int FIELD_X = 10;
    public static final int FIELD_0 = 200;
    int[][] field;
    boolean isXturn, is0Turn = false;

    public TicTacToe() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3];
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                field[i][j] = FIELD_EMPTY;
            }
        }
        isXturn = true;
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == mouseEvent.BUTTON1) {// проверяем что нажата левая клавиша мыши
            int x = mouseEvent.getX(); // коордтната Х клика
            int y = mouseEvent.getY();// координата У клика
            //переводим координаты в индексы ячейки в массиве field
            int i = (int) ((float) x / getWidth() * 3);
            int j = (int) ((float) y / getHeight() * 3);
            //проверяем что выбранная ячейка пуста и туда можно сходить
            if (field[i][j] == FIELD_EMPTY) {
                //проверяем чей ход, если Х - ставим крестик, если 0 - ставим нолик
                if (isXturn) {
                    field[i][j] = FIELD_X;
                } else
                    field[i][j] = FIELD_0;
                isXturn = !isXturn; //меняем флаг хода.
                repaint(); // перерисовка компонента, это вызоыкт метод paintCompanent();
                int res = checkState();
                if (res != 0) {
                    if (res == FIELD_0 * 3) {
                        JOptionPane.showMessageDialog(this, "Zerous win", "Win", JOptionPane.INFORMATION_MESSAGE);
                    } else if (res == FIELD_X * 3) {
                        JOptionPane.showMessageDialog(this, "X win", "Win", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "draw", "draw", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame();
                    repaint();
                }
            }
        }
    }

    void drawX(int i, int j, Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        // линия от верхнего левого угла в правый нижний угол
        graphics.drawLine(x, y, x + dw, y + dh);
        // линия от верзнего правго в левый нижний
        graphics.drawLine(x, y + dh, x + dw, y);
    }

    void draw0(int i, int j, Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        //магия  мепонятным умножением и делением для того чтобы налик был чуть вытянут по вертикали и не касался "стенок" ячейки
        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawXO(Graphics graphics) {
        // вложенные
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                //если в данной ячейке екрести то мы рисуем его
                if (field[i][j] == FIELD_X) {
                    drawX(i, j, graphics);
                    // то же для нолика
                } else if (field[i][j] == FIELD_0) {
                    draw0(i, j, graphics);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //очищаем холст
        graphics.clearRect(0, 0, getWidth(), getHeight());
        //рисуем сетку из линий
        drawGrid(graphics);
        //рисуем текущие крестики и нолики
        drawXO(graphics);

    }

    void drawGrid(Graphics graphics) {
        int w = getWidth(); // ширина игрового поля
        int h = getHeight();// высота игрового поля
        int dw = w / 3; //делим щирину на 3 и получаем ширину одной ячейки
        int dh = h / 3;// делим длинну на 3 и получаем длинну одно ячейки
        graphics.setColor(Color.BLUE);
        for (int i = 0; i < 3; i++) {
            graphics.drawLine(0, dh * i, w, dh * i); // горизонтальная линия
            graphics.drawLine(dw * i, 0, dw * i, h); // вертикальная линия
        }
    }

    int checkState() {
        //проверяем диагонали
        int diag = 0;
        int diag1 = 0;
        for (int i = 0; i < 3; i++) {
            //сумма значений по диагонали от левого угла
            diag += field[i][i];
            // сумма значений по диагонали от правго угла
            diag1 += field[i][2 - i];
        }
        //если по диагонали стоят одни крестики или одни нолики то мы выходим из метода
        if (diag == FIELD_0 * 3 || diag == FIELD_X * 3) {
            return diag;
        }
        //тоже самое и для второй диагонали
        if (diag1 == FIELD_0 * 3 || diag1 == FIELD_X * 3) {
            return diag1;
        }
        int check_i, check_j;
        boolean hasEmpty = false;
        //будем бегать по всем рядам как от военкомата
        for (int i = 0; i < 3; i++) {
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < 3; j++) {
                // суммируем знаки в текущем ряду
                if (field[i][j] == 0) {
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
            }
            if (check_i == FIELD_0 * 3 || check_i == FIELD_X * 3) {
                return check_i;
            }
            if (check_j == FIELD_0 * 3 || check_j == FIELD_X * 3) {
                return check_j;
            }
        }
        if (hasEmpty) {
            return 0;
        } else return -1;
    }
}
