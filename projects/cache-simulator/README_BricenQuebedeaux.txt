COSC 4310 - Cache Project
=========================

Files
-----
SimpleCache.java - Java source code for my cache simulator.
Cache_Project_Report_Student_Version.docx - Project report.

What the program does
---------------------
My program simulates four cache types:
1. Direct mapped
2. 2-way set associative using LRU
3. 4-way set associative using LRU
4. Fully associative using LRU

How to compile
--------------
You need a Java JDK installed.

Open a terminal or Command Prompt in the folder with SimpleCache.java and type:

javac SimpleCache.java

How to run
----------
After it compiles, type:

java SimpleCache

The program will ask for the number of cache blocks, the cache option, the number
of memory block references, and the addresses.

Test from the assignment
------------------------
Use 4 cache blocks and this address sequence:

0 8 0 6 8

Direct mapped:
Option = 1
Expected result = 5 misses, 0 hits, 100.00% miss rate

2-way set associative:
Option = 2
Expected result = 4 misses, 1 hit, 80.00% miss rate

4-way set associative:
Option = 3
With only 4 total blocks, this has one 4-way set, so it gives the same result as
the fully associative test for this address sequence.

Fully associative:
Option = 4
Expected result = 3 misses, 2 hits, 60.00% miss rate

Screenshots
-----------
I need to run the program three times for the assignment and take screenshots of:
1. Direct-mapped output
2. 2-way set-associative output
3. Fully associative output

The screenshots should show the inputs, hits/misses, final cache contents, and
miss rate.
