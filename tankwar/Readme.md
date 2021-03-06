## Mid-Term Project: Tank War

![](src/main/resources/tank.png)

You are required to implement a simple Tank War game using OOP concepts in Java programming
language. Below are the specifications:

### Player Tank & Keyboard Control
* There should be one and only one tank controlled by player using keyboard. You can use Up, Down,
Left, Right to move the tank in eight directions, which means, you should include LeftUp, UpRight,
RightDown, DownLeft also. Image files of the tank in eight directions will be provided under directory
`src\main\resources\images\tank${direction}.gif`
* When you press CTRL, your tank would fire and images of missile in eight directions are also
provided, `src\main\resources\images\missile${direction}.gif`, direction of the missile should
be same as that of Tank. Sound effect can be simulated using audio files `shoot.wav`
* When you press A, you tank would trigger a SUPER-FIRE: fire to 8 directions at the same time!
Sound effect can be simulated using either `supershoot.aiff` or `supershoot.wav` randomly
* Player tank has a initial maximum HP of 100. Please draw a blood bar with tank in the upper
to display player tank's health status

### Enemy Tanks
* There will be 12 enemy tanks in the beginning, the images are same with player tank,
but without blood bar
* Enemy tanks will move and fire randomly. Fire sound effect same as player tank. Enemy
tank will not trigger SUPER-FIRE forever, or it will be too difficult for you to survive
* If enemy tank was hit by a missile fired from player tank, it dies immediately and would
disappear from the screen
* If enemy tank fire a missile and hit player tank, it will reduce 20HP from player tank
* If all enemy tanks were dead, reproduce 12 enemy tanks again so that game can continue

### Explosion
* If a missile from player tank hit any enemy tank, or a missile from any enemy tank hit
player tank, please simulate a explosion
* There are 10 images file for you to simulate a simple animation effect under directory
`src\main\resources\images`, from `0.gif` to `9.gif`
* Explosion sound effect audio file `src\main\resources\explode.wav`

### Wall
* Simulate 3 walls, one in upper-middle, two in left and right
* Enemy tank cannot penetrate wall, as it should be, player tank can be allowed
* If missile hit all, it simply disappears

### Blood
* In the beginning of the game there will be a blood jumping in the center. If player tank
eats the blood its HP will be restored to 100 immediately!
* If blood was eaten it will disappear
* If player tank HP was lower than 50%, blood will appear again so that player tank can be
healed completely, however, blood will appear with a chance of 66.7%

### Game Rule
* Tank will move with speed of 5, which means its x and y will either plus of minus 5
* Missile will fly with speed of 10, which means its x and y will either plus of minus 10
* When player tank and enemy tank move and meet, they cannot penetrate each other as it should be
* If HP of player tank was lower or equal to 0, GAME OVER! You should end the game, play the
`death.mp3` audio file as an indicator and print message in the screen like this:
>     GAME OVER  
>     PRESS F2 TO RESTART
* After user press F2 game will restart with initial settings
* Display these data in the left-upper side, for example:
```
Missiles: 50
Explodes: 10
Our Tank HP: 80
Enemies Left: 8
Enemies Killed: 452 
```

### Implementation Tips
* Design classes of `Tank`, `Missile`, `Wall`, `Blood`, `Explode`, and `Blood`
* You can simply using `#fillRect` to draw walls with gray color, just calculate their locations
* To detect object collision, you can using `Rectange#intersects`, you can define a method `getRectangle`
for game objects for simple collision detection
* Write a enum `Direction` with 8 directions there
* Write a game client `TankWar`, with 800 * 600 screen, title and icon(image file provided `src\main\resources\icon.png`)
* Using a infinite loop to repaint the screen with interval of 50MS
* Change location of tank, missile, explode and implement draw method within these classes
* Develop step by step, write a little and test it soon, use Version Control to manage your
development stage and for easy roll back also
* For your convenience, `Tools.java` and a initial `TankWar.java` as game client was provided.
Basic methods like generate random number, play audio file and etc are there already. The general
idea to draw game objects are also included. However, you still need many work to achieve the goal
we set, feel free to modify the code, as they are just a very rough start and even would have issues
* Read javadoc or browse the source code when you get stuck, ask help when you get stuck more than
one hour, as this mid-term project is somewhat challenging for beginners

### Honor Code & Advice
* Make sure you are doing the work independently. Just Be Honest. It's acceptable that you cannot
figure out how to finish this project, but it's TOTALLY UNACCEPTABLE that you did not do what you
should do and what you can do
* It's never allowed to copy&paste code either from web or other students' solution directly.
If you are inspired please add them to your reference list
* To make sure you fully understand what you are doing, in the presentation class, you will be
asked to implement some minor adjustment feature within 10-20 minutes, or at least you should
know where to change it. Those who failed to do this will be possibly treated as cheating
* You will gain most benefit when you struggles with implementation, to improve your coding skills,
you need to set up a challenging goal, and achieve it by all means.
* Keep coding, keep coding, and keep coding

### When You Get Depressed
* This project would be challenging and difficult for many beginners, it will consume you tons of time,
it will hurt your feelings, make you feel uncomfortable, be depressed and so on. Negative emotions
will accumulate so that you would hate your instructor or even curse him - though he doesn't care
* You may ask for counselling service from Church Pastor or an old, wise man
* But still, you need to calm down and keep going, after many sleepless nights, after you conquered this
little stupid game, you will be proud of what you have achieved and congratulations! You are in a higher
level now, and there are many challenging problems waiting for you to resolve, and make a difference

### Happy Coding!
![](../projects/happy-coding.jpg)