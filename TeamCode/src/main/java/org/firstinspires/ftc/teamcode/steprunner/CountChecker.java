package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 2/12/2017.
 */
public class CountChecker extends Checker {
    protected int startCount;
    protected int currentCount;

    public CountChecker(int n) {
        startCount = n;
    }

    public void start() {
        currentCount = startCount;
    }

    public int check() {
        if (currentCount <= 0) {
            return Checker.FALSE_CHOICE;
        }
        currentCount--;
        return Checker.TRUE_CHOICE;
    }
}
