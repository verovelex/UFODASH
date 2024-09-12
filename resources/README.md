# UFODASH
I created a game in Java that's a remake of Flappy Bird, but with my own custom graphics and additional features. The game includes power-ups that spawn when the player is down to one life, and there are also enemies that can be defeated by pressing the Enter key to shoot.
I originally coded this game in IntelliJ, but if you're using Visual Studio Code (VSCode) as your IDE, you might encounter issues with loading the graphics. To ensure the game runs properly with the graphics in VSCode, follow these steps:
1. Open the terminal and compile the game by typing:
   javac -d bin src/**/*.java
   Press Enter.
2. Then, to run the game with the graphics, type:
   java -cp "bin:resources" UfoDash.GameLoop
   Press Enter again.
Now, the game's graphics should load correctly.
