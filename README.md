# Data Visualization

The project implements an all inclusive application to generate novel visualizations for existing data sets. Some novel visualizations recreated are as follows: 

1. [Nightingales CoxCombs](https://understandinguncertainty.org/node/214)
2. [Minard's Map of Napoleans 1812 Russian Campaign](https://en.wikipedia.org/wiki/File:Minard.png)

Details regarding each of these visualizations are provided in the links above.
Data for these visualization are also attached in the project, under the data folder.

The project has been implemented using Processing 3.0, Unfolding Maps and ControlP5. The jar files for each library is attached in the project as well as the classpath.


The Nightingale's Rose Chart visualization contains two separate cox combs that display the same data, however one can be zoomed for a better view and is rotated differently.
The average army size per month is also displayed above the month slider controller. All 24 months data can be displayed at once, or any range of choice can be selected.

![Image1](https://github.com/scrntnstrnglr/Visualization/blob/master/screens/COXCOMB1.png)


Minards map is recreated over a dynamic Map Provider and is thus fully zoom-capable and traversable over a limited period. This has been implemented with the idea of the reader being able to see the cities and physical map features of the location. The advance path, retreat path and temperature line during retreat are all toggle-enabled.

![Image2](https://github.com/scrntnstrnglr/Visualization/blob/master/screens/Minards1.png)

# System Requirements:
1. You should have java jdk version 1.8 and above. 
2. To check if java is enable in your system, open a terminal (Linux), command prompt (Windows) and run the following command:

```
java -version
```
3. If a definite java version is visible, you're good to go.
4. Else, [install java](https://java.com/en/download/manual.jsp) 

# How to run:

The project inlucdes a launcher application/jar  file which initiates the visualizer. The data has been preloaded. Clone the repository, or just download the 'target' folder.

1. Clone repository:
```
git clone https://github.com/scrntnstrnglr/Visualization
```
2. Go to the target foloder.
2. Double click "VISUALIZER.jar" file to run.

Alternatively

3. Open a terminal/cmd and execute the following after navigating to the folder containing the executable jar.
```
java -jar VISUALIZER.JAR
```

A log file is generated to keep track of errors and debug lines.