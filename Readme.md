Introduction
------------

This example demonstrates the use of the Java Streaming API.
<br>Input data is read from a file.
<br>Each line contains one of the following:
<br>Filter Description Line
```
<Filter_Description_Line> ::= 'QF:' <Filter_Line>
            <Filter_Line> ::= <Term> | <Term> <Filter_Line>
                   <Term> ::= <String>
```
<br>Line of Log file 
```
 <Line_Of_Log>  ::= 'LOL:' <Line_Of_Words>
<Words_Of_Line> ::= <Word> | <Word> " " <Words_Of_Line>
         <Word> ::= <String> 
```
As a result, the BNF of given file is as follows:

```
<Line_of_File> ::= <Filter_Description_Line> |  <Line_Of_Log>
```

Functional Requirements
-----------------------
1) When the solution encounters a Filter Description Line, it should print the Filter Description as.
```
<Filter_Description_Response> ::= "A:" <Filter_Terms> "; FID=" <Filter_ID>
               <Filter_Terms> ::= <String> | <String> " " <Filter_Terms>
                  <Filter_ID> ::= <Integer>
```
Filter_ID: Filter identifier, starts with 1.

2) When the solution encounters a Line of Log and a filter matches, 
then it should print the following with the filter identifier of each matching filter.
```
<Line_Of_Log_Response> ::= "M:" <Line_Of_Words> "; FID=" <FILTER_IDS>
          <FILTER_IDS> ::= <Integer> | <Integer> "," <FILTER_IDS>
```

3) For QF filter to match, all the terms in the filter must be present in the given log line and comparison is case insensitive.
4) For QF filter terms, there may be multiple whitespaces between terms.
5) For LOL line, there may be multiple whitespaces between words and there maybe punctuation marks. These should not be considered in the comparison.

![Expected Execution Flow](./docs/RequiredFlow-Expected_Execution_Flow__Line_by_Line_processing.png?raw=true)<br>
Figure 1. Expected Execution Flow.

Notes
--------

This solution is implemented in the class [StreamFilter](./src/StreamFilter.java).
Filters are represented as an [IFilter](./src/IFilter.java) interface so that different filters could be added without changing the main flow (extensibility, [Bridge pattern](https://en.wikipedia.org/wiki/Bridge_pattern)).
An example filter is implemented in the class [FilterMatchAll](./src/FilterMatchAll.java) class. It matches if a log line contains all the terms in the filter regarless of their position.

StreamFilter handles Filter Description Lines in processFilter() method while handling Line of Log Lines in processLogLine() method,
leveraging parallel streams to process multiple filters on a given log line concurrently.

(performance) Because Java HashSet implementation is based on Java HashMap, 
this solution has used contains() function when checking 
whether a HashSet of log line contains all filter terms ([conjunction](https://en.wikipedia.org/wiki/Conjunctive_query)).

![SreamFilter filter() method sequence Diagram](./docs/SequenceDiagram-1.png?raw=true)<br>
Figure 2. Sequence Diagram of filter() method.

References
------------
1. [Augmented BNF for Syntax Specifications: ABNF](https://datatracker.ietf.org/doc/html/rfc5234)
1. [Bridge pattern](https://en.wikipedia.org/wiki/Bridge_pattern)
1. [PlantUML](https://en.wikipedia.org/wiki/PlantUML)