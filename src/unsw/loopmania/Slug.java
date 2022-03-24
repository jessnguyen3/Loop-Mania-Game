package unsw.loopmania;

public class Slug extends Enemy {
    private final String type = "Slug";
    private final int battleRadius = 2;
    private final int supportRadius = 3;

    /**
     * Constructor for the slug enemy type
     * 
     * @param position
     */
    public Slug(PathPosition position) {
        super(position, new Statistics(25, 5, 3, 20, 40));
    }

    /**
     * Movement for the slug
     */
    public void move() {
        moveUpPath();
    }

    /**
     * Getter for the slug type
     */
    public String getType() {
        return type;
    }

    @Override
    public void reverseDirection() {
        // TODO Auto-generated method stub

    }

    /**
     * Getter for slug's support radius
     */
    public int getSupportRadius() {
        return supportRadius;
    }

    /**
     * Getter for slug's battle radius
     */
    public int getBattleRadius() {
        return battleRadius;
    }

}
