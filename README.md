## Nominet Java Technical Test - Towers
* This github repository consist of an Eclipse project with a solution to the Nominet Java Technical Test. The solution can be tested by running the OptimalOptimiserTest.java in Eclipse, that will run all provided test cases against the implemented OptimalOptimiser class.
* The implemented solution works the following way:
	1. It takes in a given scenario, and finds all the possible ways the transmitters could be turned up (only one transmitter at a time) to provide more coverage.
	2. From these it selects the boost to a given transmitter with the lowest (extra power)/(extra coverage) ratio, and applies that to the current scenario.
	3. It calls itself with this new scenario, until we reach full coverage, at which point it returns with the result.
* To model the possible 'boosts' given to a given scenario, a Boost class was created. Other options such as creating instead a BoostedTransmitter class, or just generating a list of possible next scenarios were considered too, but none of those showed to be definitely better while experimenting.
* To calculate the distance of receivers and towers, a DistanceCalculator interface was created, with one implementation for it in the ChebyshevDistanceCalculator. It was created this way, so the distance calculation method could be changed easily.
* To decide which receiver has signal, and other signal related calculations, a SignalCalculator class was created. The option of putting some methods like hasSignal inside the Receiver class, or fullCoverage inside the Scenario class was weighed as well, but it was decided to keep these together in their own class.
* Some extra tests were added for these new classes. The ChebyshevDistance calculator has it's own complete set of tests, while the SignalCalculator tries to use the test cases from the optimiser tests.
* These added test still won't cover the whole program as few functions like the PossibleBoosts inside OptimalOptimiser is left out, but the main functions inside it are covered, and the test for the optimise function can show any remaining problems.
* The aim was to use as meaningful variable and function names as possible.
* Also the comments are probably a bit more verbose than it would be necessary, but it was done this way to make it as easy to understand as possible.


## Original ReadMe:
## Introduction

The Tiny Island Radio Company have a number of radio transmitter and receiver towers on a series of tiny islands. Some of the receivers are currently unable to receive a signal, and so they need you to write an application to calculate how the transmitters should be modified to ensure full coverage.

We estimate that this problem will take 2-3 hours of your time.

## Problem

* Each island scenario consists of a collection of transmitter towers and receiver towers.
* Each tower has an integer-only (X, Y) location denoting its position on the island.
* Each transmitter tower also has an integer transmit power denoting the range of its transmissions.
* If the [Chebyshev distance](https://en.wikipedia.org/wiki/Chebyshev_distance) separating a receiver and a transmitter is equal to or less than that transmitter's power, then the receiver is within that transmitter's range and can pick up a good signal.
* All transmitters emit the same signal, so if a receiver is within range of one or more transmitters then it is defined to be 'within coverage'.
* Some receiver towers in a scenario will be out of coverage.
* The locations of all towers are fixed.
* The power of a transmitter can be increased or left unchanged, but cannot be decreased.
* The specific dimensions of the island are not relevant.
* Your application must increase the power of one or more towers so that all receivers on the island are in coverage, while minimising the total power of the transmitters on the island.  

## Provided Materials

* You will have been provided with a small codebase providing a basic maven build, some basic model objects, some test scenarios, and the correct answers to most of the test scenarios.
* The purpose of this codebase is to provide a starting-point for your implementation so as to reduce the amount of time you need to spend on it.
* You may replace or refactor any of the provided code if you choose, it is not intended to be a perfect implementation (nor is it well-commented).
* A very poor sample solution *AddTenOptimiser* is provided, along with a sample test class *AddTenOptimiserTest* (which will not pass and so is annotated @Ignore).
* Note there is no main function, the provided codebase is intended to run only within unit tests.
* In the img directory are some ASCII maps showing the trickier scenarios.

## Your Solution

* Write a class which implements the *PowerOptimiser* interface.
* Add as many other classes as you see fit. 
* Write a test class for your optimiser which runs each of the island scenarios in *TestScenarios*.
* The last two test scenarios do not have the correct answer provided; you must add this yourself.
* You may write any additional tests for any classes as you see fit, including the provided classes.
* Your solution should pass all the given test scenarios, but you can still receive credit if not.
* You may assume that there are no tight performance constraints.
* You may use the provided starting materials, or you may start from scratch if you wish; your solution must be a Java 8 application which is built and invoked using maven.
* You may use existing open-source libraries which don't directly address the problem.
* Your code should be well-formatted and easily-readable, with appropriate comments.
* Send us your full solution, either in a zip or via link to Github/Bitbucket etc.
