The application calculates average latency for the sequence of services
number of traces from a service to another service with maximum or exact number of hops
the length of shortest traces from a service to a service
the number of traces from a service to a service with latency less than specified number
File format
This application processes files in CSV format. The file extension does not matter, file should contain lines separated by line breaks. Each line must contain service names with length of one letter for the service connection was opened from to a service it is accessing. Third parameter is a number which describes latency between these services.

##Example:

AB5, CD6, DE8, AE17, CB22

##Using application
To build an application you must have maven installed.

Go to application folder and build a project using command:

&#8594; mvn clean package
Now after build was successfully created run it using command:

&#8594; java -jar target/ResourceRouteTrace-1.0-SNAPSHOT.jar <filename>
You can use test-data.csv file provided in the root of the project. If it was impossible to read the file you will receive an error message.

* Output
  For each line of traces from the input file you will receive 10 lines of output:

* The average latency of the trace A-B-C.
* The average latency of the trace A-D.
* The average latency of the trace A-D-C.
* The average latency of the trace A-E-B-C-D.
* The average latency of the trace A-E-D.
* The number of traces originating in service C and ending in service C with a maximum of 3 hops.
* The number of traces originating in A and ending in C with exactly 4 hops.
* The length of the shortest trace (in terms of latency) between A and C.
* The length of the shortest trace (in terms of latency) between B and B.
* The number of different traces from C to C with an average latency of less than 30.
  If path between two services does not exist you will receive NO SUCH TRACE message.