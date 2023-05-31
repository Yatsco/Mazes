# Mazes

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites
Requires a running copy of Java 8 on the local machine.

### Installation
* Copy Mazes jar to wherever you wish to store the app.
* Run it from the command line with the desired configuration file as an argument.
  java -jar mazes.jar <config-file-path>

### How to Use
* Create desired configuration file according to Mazes Project document.
* Launch application with config file according to Installation instructions.
* The empty maze specified by the config file appears.
* There are five buttons below the maze.
* Click "Config" button to launch the search specified in the config file.
* Click "Mouse" button to launch a random mouse search.
* Click "Wall" button to launch a right-wall follower search.
* Click "Wall Thread" button to launch a two wall follower searches from different corners of the maze.
* Click "Animate Maze" to see an animation of the maze genearation specified in the config file.

### Notes on Use
* You can run all the button actions simultaneously. (Maybe pause a bit before clicking each button.)
* Random Mouse search appears as a light orange circle.
* Wall follower search mouse appears as a light green circle.
* Wall Thread search mice appear as light red and light blue circles.
* With large mazes or with Random Mouse, the search may fail. Often you can ignore the failure and try the earch again.

### Known Bugs and Incompletions
* There is no multi-threaded random mouse search.
* There is no extra search.
* Searches may fail or crash with large mazes or particularly with random mouse searches.

### Possible Improvements
* Add more maze generation methods.
* Add more maze search methods.
* Rewrite according to "Programming Notes."

### Programming Notes
Jack Trainor: I had the idea I could multi-thread the searches by having a blockingqueue at each cell, then, when solving the multi-threaded random search, I could broadcast search events to all the connected cells which would broadcast events to all their connected cells and all the search paths could be explored by threads. Each search event would append the new cell id to its search path. It's a valid idea and my code works like that.

Based on the idea Brooke mentioned when introducing the project, I decided I could use the recorded path when solving the maze as a script to play when it came to visually displaying the search. That works too, though I guess not in  the dynamic way Joe and Brooke had in mind.

After thinking it over I believe the real plan was for us to write straight, non-theaded solver code, which would queue Executor threads to animate the mouse moves on the screen. The solver calls would finish quickly and the Executor threads would play out in human time using PathTransitions for a simple animation from one cell to the next until complete.

What I'm doing is using the recorded path in the final SolverEvent as a script to build a multi-segment PathTransition to play out from start to finish in the JavaFx view.

I did think long and hard for the first several days of the project. In retrospect I picked the wrong strategy. The deadline was short and it was too much work and too risky to rewrite.

As I see it now, I would rip out the blockingqueues at each cell and the threaded calls in the search methods, then implement the ExecutorService described above.

Jarek Yatsco: I did all of the maze generation/animating the maze gens. I also had set up the generic classes of cells, mazes,
and walls. I implemented these generators:

dfs
aldous
kruskal

What did I not get to or would have done better:

I would have liked to implement Prims gen structure, but
I had ran out of time. I think I did the parser pretty
ineficcient, but functional. Overall I think on the generation side
if I were to do something different it would be resturcturing everything.

### Built With

* [IntellJ IDEA 2020.3 (Community Edition)] - www.jetbrains.com/idea

## Authors

* **Jack Trainor, Jarek Yatsco** - UNM CS351 "Design of Large Programs," Prof. Joseph Haugh, Prof. Brooke Chenoweth
