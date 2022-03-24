package unsw.loopmania;

public class EnemySelector {
    private static final int SLUG = 0;
    private static final int ZOMBIE = 1;
    private static final int VAMPIRE = 2;

    public Enemy getEnemy(int enemySelection, PathPosition position) {
        Enemy enemy;
        switch (enemySelection) {
            case SLUG:
                enemy = new Slug(position);
               //System.out.println("Spawned a slug");
                return enemy;
            case ZOMBIE:
                enemy = new Zombie(position);
                System.out.println("Spawned a Zombie");
                return enemy;
            case VAMPIRE:
                enemy = new Vampire(position);
                System.out.println("Spawned a vampire");
                return enemy;

        }
        return null;
    }
}
