package ua.edu.chmnu.ki.networks.mouse;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MouseRobot {

    public static void main(String[] args) {
        try(Scanner in = new Scanner(System.in)) {
            Robot robot = new Robot();
            while (true) {
                try {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    robot.mouseMove(x, y);
                } catch (InputMismatchException ex) {
                    System.out.println("By!!!");
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
