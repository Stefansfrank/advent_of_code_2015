# ADVENT OF CODE 2015

This is the 6th year I am doing Advent of Code and the second year I do it in Kotlin. Since 2015 seems to be a somewhat easy year, the solutions are all well documented and should be decently architected solutions. I am becoming more familiar with Kotlin and the language is fun.

I started to add reusable code blocks but did not encapsulate them into libraries so the idea is that every day can be independently run with the following in the same directory:
- ```AoC2015.kt``` the main class that can call day n given as a command line parameter via reflection - see below how to avoid it if you run into problems with it as it might require build script changes for the reflection
- ```Int.kt``` for some integer manipulation (primes and such)
- ```Coll.kt``` for some helpers on collections such as permutations and combinations
- ```FileAcc.kt``` for the file access routines
- ```2D.kt``` for points, maps, masks and other often used stuff for 2D geometry
- ```Ansi.kt``` for ANSI codes to pretty print my results :)

You can directly use the ```solve``` function on every day by renaming it to main() followed by declaring the file name of the input as ```val file = "xx"```. If you use main in AoC2015 and provide the number of the day and the dataset name as I do (my convention for the file naming of data files is ```d<n>.<dataset>.txt``` with ```<dataset>``` either "input" or "test"), you might need to change the build script to add reflection (I had to do that in IntelliJ). I added that since I work on different days on multiple computers and this allows things to run without all days being present.
  
