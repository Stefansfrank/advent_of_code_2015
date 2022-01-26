# ADVENT OF CODE 2015

This is the 6th year I am doing Advent of Code and the second year I do it in Kotlin. Since 2015 seems to be a somewhat easy year, the solutions are all well documented and should be decently architected solutions. I am becoming more familiar with Kotlin and the language is fun.

I started to add reusable code blocks but did not encapsulate them into libraries so the idea is that every day can be independently run with the following in the same directory:
- ```AoC2015.kt``` (the main class that can call day n given as a command line parameter via reflection), also provides the Ansii codes for the color in the result print
- ```Int.kt``` for some integer manipulation and stuff
- ```FileAcc.kt``` for the file access routines
- ```2D.kt``` for points, maps, masks and other often used stuff for 2D geometry

You can start the ```solve``` function on every day directly with the filename as a parameter or use main in AoC2015 and provide the number of the day and the dataset name (my convention is ```d<n>.<dataset>.txt``` with ```<dataset>``` either "input" or "test")
  
