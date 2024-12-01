package game;

import java.awt.event.KeyEvent;

public interface Money {
    default void placeBets (int amount){
        PlayerMoney.money -= amount;
        if(PlayerMoney.money < 0){
            PlayerMoney.money = 0;
        }
    }
    //void interaction(KeyEvent key);
}
