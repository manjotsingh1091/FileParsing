The basic requirement is to provide a program that can convert text into either XML format-ted data or comma separated (CSV) data. 

The text is to be parsed, broken into sentences and words and the words have to be sorted.

The program must be able to input text like:

"Mary had a little lamb. Peter called for the wolf, and Aesop came. Cinderella likes shoes."

and parse this text into relevant model classes, and be able to convert the structure to both XML and CSV format.

The parsing must break the text into sentences and words. The parser should allow some whitespace around words and delimiters, e.g. the following is allowed as input and should produce the same result as the first example:

" Mary had a little lamb .

Peter called for the wolf , and Aesop came . Cinderella likes shoes."

###############################################################################################

## Output

In both cases the XML result should be like:

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<text>
    <sentence>
        <word>a</word>
        <word>had</word>
        <word>lamb</word>
        <word>little</word>
        <word>Mary</word>
    </sentence>
    <sentence>
        <word>Aesop</word>
        <word>and</word>
        <word>called</word>
        <word>came</word>
        <word>for</word>
        <word>Peter</word>
        <word>the</word>
        <word>wolf</word>
    </sentence>
    <sentence>
        <word>Cinderella</word>
        <word>likes</word>
        <word>shoes</word>
    </sentence>
</text>


CSV should be: 
, Word 1, Word 2, Word 3, Word 4, Word 5, Word 6, Word 7, Word 8
Sentence 1, a, had, lamb, little, Mary
Sentence 2, Aesop, and, called, came, for, Peter, the, wolf
Sentence 3, Cinderella, likes, shoes


###############################################################################################

## Ways to run this application

Assuming you already have 

1. JDK8 installed and JAVA_HOME set
2. MAVEN installed and MAVEN_HOME set

#### 1. Using Spring Tool Suite

1. Clone the project from git or download as zip file
2. Open the project in any IDE and Install as Maven project.
3. Use maven clean and install for building the project
4. Run it as a Spring boot Application from FileParserApp.java


###############################################################################################

## Run Junit test cases

1. Run FileParserTests.java to run all the junit test cases.


###############################################################################################

Note: Path of the sample input and output files should be given in the application.properties file under resources folder

1. Input file path
text.file.path=C:\\Project\\sample-files\\small.in

2. Output Xml folder path
xml.file.path=C:\\Project\\sample-files\\Output_XML

3. Output Csv folder path
csv.file.path=C:\\Project\\sample-files\\Output_CSV

Output xml and csv files will be generated under these folders as output.xml and output.csv respectively.


