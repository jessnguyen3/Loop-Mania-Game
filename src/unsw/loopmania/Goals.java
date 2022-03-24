package unsw.loopmania;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Statistics for each entity
 */
public class Goals {
    /**
     * basic attributes for Goals
     */
    private int ANDLoops;
    private int ANDGold;
    private int ANDExp;
    private int ORLoops;
    private int ORGold;
    private int ORExp;
    private JSONObject allGoals;
    private boolean ORGoalsActive;
    /**
     * SimpleIntegerProperty varibles for ANDGoals
     */
    private SimpleIntegerProperty ANDLoopsValue = new SimpleIntegerProperty(this, "ANDLoops");
    private SimpleIntegerProperty ANDGoldValue = new SimpleIntegerProperty(this, "ANDGold");
    private SimpleIntegerProperty ANDExpValue = new SimpleIntegerProperty(this, "ANDExp");
    /**
     * SimpleIntegerProperty varibles for ORGoals
     */
    private SimpleIntegerProperty ORLoopsValue = new SimpleIntegerProperty(this, "ORLoops");
    private SimpleIntegerProperty ORGoldValue = new SimpleIntegerProperty(this, "ORGold");
    private SimpleIntegerProperty ORExpValue = new SimpleIntegerProperty(this, "ORExp");

    /**
     * constructor for Goals
     * 
     * @param goal_condition
     */
    public Goals(JSONObject goal_condition) {
        // intialise AND and OR loops
        this.ANDLoops = 0;
        this.ANDGold = 0;
        this.ANDExp = 0;
        this.ORLoops = 0;
        this.ORGold = 0;
        this.ORExp = 0;
        // this.ANDLoopsValue.set(0);
        // this.ANDGoldValue.set(0);
        // this.ANDExpValue.set(0);
        this.ORGoalsActive = false;
        allGoals = new JSONObject();
        int quantity = 0;

        // create JSONObject containing goals
        String goal = goal_condition.getString("goal");
        if (goal.equals("AND")) {
            JSONArray ANDGoals = goal_condition.getJSONArray("subgoals");
            for (int i = 0; i < ANDGoals.length(); i++) {
                JSONObject ANDGoal = ANDGoals.getJSONObject(i);
                goal = ANDGoal.getString("goal");
                if (goal.equals("OR")) {
                    JSONArray ORGoals = ANDGoal.getJSONArray("subgoals");
                    ORGoalsActive = true;
                    for (int j = 0; j < ORGoals.length(); j++) {
                        JSONObject ORGoal = ORGoals.getJSONObject(j);
                        goal = "OR" + ORGoal.getString("goal");
                        quantity = ORGoal.getInt("quantity");
                        this.setGoals(goal, quantity);
                    }
                } else {
                    quantity = ANDGoal.getInt("quantity");
                    this.setGoals(goal, quantity);
                }
            }
        } else {
            quantity = goal_condition.getInt("quantity");
            this.setGoals(goal, quantity);
        }
        // put ALL and OR conditions into allGoals
        allGoals.put("ANDLoops", ANDLoops);
        allGoals.put("ANDExp", ANDExp);
        allGoals.put("ANDGold", ANDGold);
        allGoals.put("ORLoops", ORLoops);
        allGoals.put("ORExp", ORExp);
        allGoals.put("ORGold", ORGold);

        // set the valueProperty for each AND and OR conditions
        setANDLoopsValueProperty(this.ANDLoops);
        setANDGoldValueProperty(this.ANDGold);
        setANDExpValueProperty(this.ANDExp);

        setORLoopsValueProperty(this.ORLoops);
        setORGoldValueProperty(this.ORGold);
        setORExpValueProperty(this.ORExp);

    }

    /**
     * sets goals for the character to achieve
     * 
     * @param goal
     * @param quantity
     */
    public void setGoals(String goal, int quantity) {
        // switch statement for type of goals
        switch (goal) {
            case "cycles":
                this.ANDLoops = quantity;
                // this.ANDLoopsValue.set(quantity);
                // setANDLoopsValueProperty(quantity);
                break;
            case "gold":
                this.ANDGold = quantity;
                // this.ANDGoldValue.set(quantity);
                // setANDGoldValueProperty(quantity);
                break;
            case "experience":
                this.ANDExp = quantity;
                // this.ANDExpValue.set(quantity);
                // setANDExpValueProperty(quantity);
                break;
            case "ORcycles":
                this.ORLoops = quantity;
                break;
            case "ORgold":
                this.ORGold = quantity;
                break;
            case "ORexperience":
                this.ORExp = quantity;
                break;
        }
    }

    /**
     * method to printAllGoals to STDOUT
     */
    public void printAllGoals() {
        System.out.println("Mandatory goals");
        System.out.println("Loops required: " + ANDLoops);
        System.out.println("Experience required: " + ANDExp);
        System.out.println("Gold required: " + ANDGold);
        System.out.println();
        System.out.println("Optional goals");
        System.out.println("Loops: " + ORLoops);
        System.out.println("Experience : " + ORExp);
        System.out.println("Gold : " + ORGold);
        System.out.println();
    }

    /**
     * method if character has met goals
     * 
     * @param stats
     * @param numLoops
     * @return
     */
    public boolean checkGoalsMet(Statistics stats, int numLoops) {
        int exp = stats.getExp();
        int gold = stats.getGold();
        // if goals have been met, return true and print the game has been won
        if (checkANDGoals(exp, gold, numLoops)) {
            if (ORGoalsActive) {
                if (checkORGoals(exp, gold, numLoops)) {
                    // System.out.println("====================");
                    // System.out.println("You've won the game!");
                    // System.out.println("====================");
                    return true;
                }
            } else {
                // System.out.println("====================");
                // System.out.println("You've won the game!");
                // System.out.println("====================");
                return true;
            }
        }
        // otherwise return false
        return false;
    }

    /**
     * method to check if player stats have achieved the goal requirement
     * 
     * @param current
     * @param target
     * @return boolean
     */
    public boolean checkStats(int current, int target) {
        return current >= target;
    }

    /**
     * method to check if ORGoals have been met
     * 
     * @param exp
     * @param gold
     * @param numLoops
     * @return boolean
     */
    public boolean checkORGoals(int exp, int gold, int numLoops) {
        if ((ORLoops > 0 && checkStats(numLoops, ORLoops)) || (ORExp > 0 && checkStats(exp, ORExp))
                || (ORGold > 0 && checkStats(gold, ORGold))) {
            return true;
        }
        return false;
    }

    /**
     * method to check if ANDGoals have been met
     * 
     * @param exp
     * @param gold
     * @param numLoops
     * @return boolean
     */
    public boolean checkANDGoals(int exp, int gold, int numLoops) {
        if (checkStats(exp, ANDExp) && checkStats(gold, ANDGold) && checkStats(numLoops, ANDLoops)) {
            return true;
        }
        return false;
    }

    /**
     * method to getAllGoals
     * 
     * @return JSONObject
     */
    public JSONObject getAllGoals() {
        return allGoals;
    }

    /**
     * method to get ADDLoopsValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty ANDLoopsValueProperty() {
        return ANDLoopsValue;
    }

    /**
     * method to get ADDGoldValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty ANDGoldValueProperty() {
        return ANDGoldValue;
    }

    /**
     * method to get ADDExpValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty ANDExpValueProperty() {
        return ANDExpValue;
    }

    /**
     * setter for ANDLoopsValueProperty
     * 
     * @param loops
     */
    public void setANDLoopsValueProperty(int loops) {
        this.ANDLoopsValueProperty().set(loops);
    }

    /**
     * setter for ANDGoldValueProperty
     * 
     * @param loops
     */
    public void setANDGoldValueProperty(int gold) {
        this.ANDGoldValueProperty().set(gold);
    }

    /**
     * setter for ANDExpValueProperty
     * 
     * @param loops
     */
    public void setANDExpValueProperty(int exp) {
        this.ANDExpValueProperty().set(exp);
    }

    /**
     * method to get ORLoopsValueProperty+
     * 
     * @return
     */
    public IntegerProperty ORLoopsValueProperty() {
        return ORLoopsValue;
    }

    /**
     * method to get ORGoldValueProperty
     * 
     * @return
     */
    public IntegerProperty ORGoldValueProperty() {
        return ORGoldValue;
    }

    /**
     * method to get ORExpValueProperty
     * 
     * @return
     */
    public IntegerProperty ORExpValueProperty() {
        return ORExpValue;
    }

    /**
     * setter for ORLoopsValueProperty
     * 
     * @param loops
     */
    public void setORLoopsValueProperty(int loops) {
        this.ORLoopsValueProperty().set(loops);
    }

    /**
     * setter for ORGoldValueProperty
     * 
     * @param loops
     */
    public void setORGoldValueProperty(int gold) {
        this.ORGoldValueProperty().set(gold);
    }

    /**
     * setter for ORExpValueProperty
     * 
     * @param loops
     */
    public void setORExpValueProperty(int exp) {
        this.ORExpValueProperty().set(exp);
    }
}
