package bsu.rfe.java.group6.lab6.Litvinenko.varC1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    // Константы, задающие размер окна приложения, если оно
    // не распахнуто на весь экран
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JMenuItem startMenuItem;
    private JMenuItem resetMenuItem;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;

    // Поле, по которому прыгают мячи
    private Field field = new Field();

    // Конструктор главного окна приложения
    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
        // Установить начальное состояние окна развѐрнутым на весь экран
        setExtendedState(MAXIMIZED_BOTH);
        // Создать меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Игра");
        Action startAction = new AbstractAction("Начать/Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                field.resume();
                if(!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    // Ни один из пунктов меню не являются
                    // доступными - сделать доступным "Паузу"
                    pauseMenuItem.setEnabled(true);
                }
                resetMenuItem.setEnabled(true);
            }
        };

        Action add10BallAction = new AbstractAction("Добавить 10 мячей") {
            public void actionPerformed(ActionEvent event) {
                for(int i = 1; i <= 10; i++)
                    field.addBall();
            }
        };
        ballMenu.add(add10BallAction);

        Action resetAction = new AbstractAction("Сброс") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(false);
                resetMenuItem.setEnabled(false);
                field.reset();
            }
        };
        menuBar.add(ballMenu);
        startMenuItem = ballMenu.add(startAction);
        resetMenuItem = ballMenu.add(resetAction);
        resetMenuItem.setEnabled(false);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);

        Action resumeAction = new AbstractAction("Возобновить движение") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        // Добавить в центр граничной компоновки поле Field
         getContentPane().add(field, BorderLayout.CENTER);
    }
    // Главный метод приложения
    public static void main(String[] args) {
        // Создать и сделать видимым главное окно приложения
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}