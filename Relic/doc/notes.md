# Developer notes

------------------------------------------------------------------------
2017-10-12 (JG)
------------------------------------------------------------------------

- merged junior-teleop-start branch back into develop
- started branch junior-steprunner for StepRunner port
- try to build current projects on ECR-Silver
    - don't have phone/robot so just go for a clean build

# Review of current repo structure
- FtcRobotController is the FIRST-provided base code, including examples
- ECRLib is our reusable components shared year-to-year
	- we *may* make changes to ECRLib that are not backward compatible
		with previous year's robots
- K9, Relic, Velocity are robot-specific
- Sandbox is an area to experiment with generic Java
- moved our code for ECRLib and Relic to new ECR-specific package
	org.eastcobbrobotics.ftc


## M1: find and review StepRunner code
- moved Relic-specific steps over to its own project
- renamed package to org.eastcobbrobotics.ftc.ecrlib.steprunner
- note that "soft" Steps (ones that don't need robot hardware, like ParallelStep)
	can be tested by inclusion in StepRunnerTest (right-click and run it)
- robot-specific steps should live in teamcode.autonomous.steps

## M2: find and review StepRobot class used by SR

## M3: create RelicRobot class based on StepRobot

## M4: create or clone basic SR-based autonomous opmode in 'relic'


------------------------------------------------------------------------
Questions
------------------------------------------------------------------------
- how to merge FIRST-provided updates from the 'beta' branch into our repo?
