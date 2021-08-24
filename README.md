# Stef's Asteroid Challenge Game

Hey there! Welcome. :wink:  
This project is an example of using [LibGDX framework](https://libgdx.com/) to create a standalone game app.  
In this game you are in control of a spaceship, that finds itself in an asteroid belt.  
My attempt was to keep the physics and movement of the spaceship as 'space-like' as possible.  
The ship can only be accelerated forward, but it can be rotated left and right.  
There are no breaks, but if you are not pressing the gas then the ship decelerates on it's own.   
I have made numerous comments in the code to try and explain/analyse the code and give reasoning as to what is used, where and why.   
While most of the explanations can be found in comments, here's a short overview of the components i've used in the game's architecture.

**Actors**   
I've started out by creating the class RootActor which extends the Actor(well Group* , to be more precise, which extends Actor) to add some more functionality that i found necessary to reduce repetitive code( like setting the animation, moving, positioning, etc..) and then each of the Actors that i needed for the game inherit from RootActor. 

**Screens**  
The game is divided into screens which can switch to and from one another.  
To simplify their creation and reduce boilerplate code, as for actors** i've made the AbstractScreen class, that all the screens in the game inherit from.

**Util**  
The Ui package contains the helper classes i've made along the way for the parts of the code that i noticed could be separated from the main logic, again, for brevity and clarity of code.

**Ui**   
The Ui package contains custom Ui objects i've designed for ease of use and design of the interface. Like the navigation button, which i've used in almost all of the game screens to transition between screens. 

**Game class**  
The AsteroidGame is the main game class, but you will notice that all that is done witin it is 
+ declaration and initialization of a number of static elements that are reused on multiple places within various screen implementations
+ Input processor setup
+ saving/loading highscores

Everything else is delegated to the individual screens. 

An interesting thing to note however, is that it maintains a static reference to itself.
```
	private static AsteroidGame game;
```
To make it possible to switch to another screen from within the current one, by using the static method defined in AsteroidGame
```java
public static void setActiveScreen(AbstractScreen screen){
		game.setScreen(screen);
	}
```

*Extending the Group class(from libGDX libraries) to give the Actor the ability to act as a container to which other actors can be added - like the spaceship that contains thrusters, and the shield, which then move along with it.

** With a little difference as RootActor is not abstract and can be instantiated for a generic Actor, while AbstractScreen is abstract and must be implemented.

## Game 
Your ship has been travelling across the space, when it flew into an asteroid belt, will you be able to clear the asteroids and get back on course? :)

### Tutorial 

The objective of the game is to destroy all asteroids.  
Six lasers are initially available and they recharge over time.  
If you are in a tight spot try to warp out of it.  
Warping is not without risk however, as there is a chance to warp to the same position where an Asteroid is, and collide with it.  
At the start of the game you have a shield that can take 3 asteroid hits before it gets destroyed  
When the shield is down, the next asteroid that hits the ship will destroy it, be carefull.

**Controls:**

Left arrow/right arrow - rotate left/right  
Up arrow - accelerate forward  
Space - shoot a laser in the direction that the ship is facing  
r - warp to a random location

**Tip** - _try to destroy the smaller asteroids first, if you destroy a bunch of bigger ones they will break into smaller ones, the more asteroids there are in the field, the harder it is to avoid them._

## Download
You can clone the project and open it in Android Studio, or [click here](https://github.com/StefanOffice/stefs-asteroid-challenge-game/releases/tag/1.0) to download the executable jar file and run it to play the game.

### Thank you!

Thank you for checking out my project. I hope you enjoyed this game, as much as i enjoyed making it. :wink: 

***All the best,  
Stefan Stefanovic***
