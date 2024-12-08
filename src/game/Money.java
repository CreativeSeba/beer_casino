package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface Money {
    void placeBets(int amount);
    void interaction(Player player);
    default void updateGamePanelMessages(int moneyBefore, int amount) {
        int result = PlayerMoney.money - moneyBefore;
        if (result > 0) {
            GamePanel.getInstance().setResultMessage("+" + result, Color.GREEN);
        } else if (result < 0) {
            GamePanel.getInstance().setResultMessage(String.valueOf(result), Color.RED);
        }
        GamePanel.getInstance().setPaidMessage("-" + amount); // Set the paid message
    }
}
