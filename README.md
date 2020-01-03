**Language Detector**
2019 OOP project. The purpose of this program is to output the language of a given query file


**User Guide**
(1) Run JAR
(2) Enter Wili Dataset location - Large or Small, both included in project
(3) Enter Query file Location, examples included in project

User will be prompted with a Language that best matches theier query


**Description/Process**
Both the Language dataset and the query are broken into kmers/ngrams, placed into thier own maps
and are compared against each other to find the smallest out of place metric which should indicate 
the language in which the text is written. We only need the top 300 kmers in the query file to get an
accurate analysis of the language type. This project uses 4mers throughout.

**Author**
Darragh Lally - G00220290
