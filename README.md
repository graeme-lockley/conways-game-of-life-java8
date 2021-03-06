After having implemented Conway's Game of Life a number of times as part of a code retreat, I became a little concerned 
that I was writing unit tests for an algorithm which I had merely transcribed.  This seemed pointless.  What was even more 
discouraging is how the language was manipulated from the underlying algorithm definition to what is typically written 
at the code retreats that I have been part of.

What I wanted to do was code the algorithm as accurately as I could
using both the explicit and implicit language.  The results of this exercise is contained in this project.  Further to that I have also written a note describing this exercise [here](http://graeme-lockley.github.io/posts/20160227-conways-game-of-life).


Note: If you would like to build this project I have made use of a property based testing library which I have not yet
released into a public repo.  You will therefore need to install [https://github.com/graeme-lockley/pbt-java8](https://github.com/graeme-lockley/pbt-java8)
into your local repo.

---

The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

* Any live cell with fewer than two live neighbours dies, as if caused by under-population.
* Any live cell with two or three live neighbours lives on to the next generation.
* Any live cell with more than three live neighbours dies, as if by over-population.
* Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

The initial pattern constitutes the seed of the system. The first generation is created by applying the above rules simultaneously to every cell in the seed—births and deaths occur simultaneously, and the discrete moment at which this happens is sometimes called a tick (in other words, each generation is a pure function of the preceding one). The rules continue to be applied repeatedly to create further generations.
