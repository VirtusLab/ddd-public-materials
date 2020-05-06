# DDD Koans

This project contains source for the **DDD Koans** course on Stepik.

## Domain overview

In this course, we would like to focus on the domain of music. Specifically speaking, on music creation.
 
* How musician creates new songs?
* What is needed to achieve it?
* What is the song lifecycle?
* How collaboration looks like?
* and many more...

## Theoretical background

This section describes a simplified music theory terms. 

### Time signatures

![3over4](images/3over4.png)

Time signatures are symbols which describe repeating beat rhythm in a piece of music.

They consist of two numbers: 
 * The upper number is the count of beats in the meter - **3** in the above example.  
 * The lower number is the symbol length used to represent each beat - **4** in the above example.
 
Together they tell us the total length of all symbols in a bar.

In text time signatures are written using slash sign **/** like **3/4**.

#### The upper number - numerator

The upper number (numerator) has one limitation - it needs to be greater than 0 integer.
However, for purpose of this example let's say that maximum number can be equal to **32**.


#### The lower number - denominator

The lower number (denominator) always corresponds to the beat unit which is a value of music note.

Available values are (limited for the purpose of course):

|Note symbol|Name|Written in text as|Beat unit value in time signature|
|---|---|---|---|
|![whole-note](images/whole-note.png)|whole note|**1/1**|1|
|![half-note](images/half-note.png)|half note|**1/2**|2|
|![quarter-note](images/quarter-note.png)|quarter note|**1/4**|4|
|![eight-note](images/eight-note.png)|eight note|**1/8**|8|
|![sixteenth-note](images/16th-note.png)|sixteenth note|**1/16**|16|
|![thirtysecond-note](images/32th-note.png)|thirty second note|**1/32**|32|

Mathematically speaking one whole note (**1/1**) is equivalent to two half notes (**1/2**)(**1/1** == 2***1/2**). 
Same rule applies for the rest of values.

What is more, its value is a power of 2.

### Bars

![bars](images/bars.png)

A written up piece of music is divided into chunks called **bars**.

Each bar follows a **time signature** assigned at the beginning of bar until new **time signature** occurs.

In the picture above, there are 2 **bars**, each in **3/4** time signature. It means that, each **bar** has length of 3 quarter notes (**3/4**).

Notes in bars:

* 1st bar: **1/4** + **1/4** + **1/16** + **1/16** + **1/16** + **1/16** => **3/4**
* 2nd bar: **1/8** + **1/8** + **1/4** + **1/4** => **3/4**

A bar cannot have less or more beats than it is indicated by assigned **time signature**. Where a beat is a note value.

### Tempo

![tempo](images/tempo.png)

A **tempo** indicates how fast the piece of music as a whole should be played. It is express in *beats per minutes* (**bpm** or **BPM**).

The note value of a beat will typically be that indicated by the denominator of the time signature i.e. **4** in the **3/4** time signature.