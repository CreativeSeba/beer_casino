package game;

public interface Money {
    void addMoney(PlayerMoney playerMoney);

    default void removeMoney(PlayerMoney playerMoney, int amount){
        playerMoney.money -= amount;
        if(playerMoney.money<0){
            playerMoney.money=0;
        }
    }
}
