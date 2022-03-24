package unsw.loopmania;

import java.util.List;

public class Gold extends MovingEntity {
    PathPosition position;

    public Gold(PathPosition position, Statistics stats) {
        super(position, stats);
    }

    @Override
    public void move() {
    }

    public int getGold() {
        return 100;
    }

    @Override
    public void attack(MovingEntity opponent, List<Item> equippedItems) {
        // TODO Auto-generated method stub

    }
}
