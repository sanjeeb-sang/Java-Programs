{
    Written by Sanjeeb Sangraula
    
    SUMMARY:
    This is a program to help calculate the points that a student gets in a 
    quiz. This program is written to satisfy the needs of Professor Lotta
    Greef who has asked you to write a program that grades her multiple
    choice quizzes.  Each quiz always has 10 questions.

    INPUT:
    For a given quiz, the professor will enter data according to the following specifications:
    1. The first line will be the correct answers to the quiz (10 characters – no spaces).
    2. The second line will be the number of students who took the quiz.
    3. Each subsequent line in the file will contain the responses from one student (10 characters – no spaces).  
    This is the input for the program.
    
    OUTPUT:
    The output of the program is in the form:
    Student <student_number>:  <num_of_correct_answers> correct <num_of_incorrect_answers> incorrect <percentage_of_correct_answers> percent 
}
Program QuizGrader(output);

{
    START of type declaration
}
type 
    {
        The vector type is an array with 10 elements that are real
    }
    Vector = array[1 .. 10] of real;
    {
        The StringArray type is an array with 10 elements that are strings
    }
    StringArray = array[1 .. 10] of string;
    {
        The IntegerArray type is an array with 10 elements that are Integers
    }
    IntegerArray = array[1 .. 10] of Integer;
{
    END of Type Declaration
}

{
    START of variable declarations
}
var 
    {
        correctAnswers stores the number of correctAnswers,
        studentsAnswer stores the answers of the user
    }
    correctAnswers, studentAnswer : string;
    {
        numOfStudents stores the number of student or the number of answers,
        studentNumber stores the current student number, the first student with the input value is student 1 and so on
    }
    numOfStudents, studentNumber : Integer;
    {
        correctAnswersArray is an StringArray that will hold all the correct answers
    }
    correctAnswersArray : StringArray;
    {
        missedQuestionsArray is an Integer array that contians the number of missed responses for each question
    }
    missedQuestionsArray : IntegerArray;
{
    END of Variable declarations
}


{
    START of Function Declarations
}
{
    START of GetVectorFromString function declaration.
    This is a function that takes a String named aString as a parameter 
    and returns a StringArray containing the elements of the string.
    Each character of the string is split and added to the array that is returned.
    @param aString a string which is to be spilit into an array
}
Function GetVectorFromString(aString: String) : StringArray;
{
    START of the variable declarations.
    It contains two variables: 
        anArray which is a StringArray that contains the aString in an Array form; 
        i which is an integer used in the for loop.
}
var 
    anArray : StringArray;
    i : Integer;

begin
    {
        Looping through all the characters in aString and assigning it to anArray
    }
    for i := 1 to 10 do
        anArray[i] := aString[i];

    {
        Returning anArray from the GetVectorFromString function
    }
    GetVectorFromString := anArray;

end;
{
    END of the GetVectorFromString declaration
}


{
    START of the GetCorrectAnswerNumber declaration.
    This is a function that takes the answerVector as input and 
    returns the total number of correct answers as output. This function calculates the total number of correct answers.
    @param answerVector a StringArray containing all the answers of an student
}
Function GetCorrectAnswerNumber(answerVector: StringArray) : Integer;
{
    The variable declarations start.
    The first variable is correct which is an integer that stores the total 
    number of correct answers and the second variable is k, an integer that 
    is used in the for loop in this function.
}
var 
    correct, k : Integer;

begin

    {
        Initializing correct to 0
    }
    correct := 0;
    
    {
        Looping through all the answers and checking if it is correct or not.
    }
    for k := 1 to 10 do
    begin
        {
            If the value at k index of the answerVector is equal to the 
            value at k index of the correctAnswersArray then the answer
            is correct, otherwise not.
        }
        if ( answerVector[k] = correctAnswersArray[k] ) then
            {
                If the answer is correct, increase correct by 1
            }
            correct += 1
        else 
            {
                If the answer is not correct, then add 1 to the missedQuestionsArray at index k
            }
            missedQuestionsArray[k] += 1;
        
    end;
    
    {
        Return the correct number of answers from this function
    }
    GetCorrectAnswerNumber := correct;

end;
{
    END of the GetCorrectAnswerNumber declaration
}


{
    START of Procedure Declarations
}
{
    START of declaration of StoreCorrectAnswers Procedure.
    This is a procedure that stores the correct answers in the 
    correctAnswersArray.
    @param correctAnswersString a string containing the correct answers to all questions
}
Procedure StoreCorrectAnswers(correctAnswersString: string);
{
    The procedure begins.
}
begin
    {
        Calling GetVectorFromString(correctAnswersString) which takes a string and returns 
        an array containing the correct answers.
    }
    correctAnswersArray := GetVectorFromString(correctAnswersString);

end;
{
    END of declaration of StoreCorrectAnswers Procedure
}


{
    START of DisplayStudentsData Procedure.
    This is a procedure that displays the data associated with a student.
}
Procedure DisplayStudentsData(i: Integer; studentAnswers: String);
{
    The variables declaration start.
}
var 
    {
        correct - an integer that contains the correct answers of a student
        incorrect - an integer that contains the incorrect answers of a student
        percent - an integer that conains the percentage of correct score of the student
    }
    correct, incorrect, percent : Integer;
    {
        answerVector - a StringArray that contains the answers given by the student
    }
    answerVector : StringArray;

begin
    {
        initialize correct to 0
    }
    correct := 0;
    
    {
        Get the answer vector from the studentAnswers String
    }
    answerVector := GetVectorFromString(studentAnswers);
    
    {
        Get the number of correct answers from the answerVector
    }
    correct := GetCorrectAnswerNumber(answerVector);
     
     {
         Calculating the incorrect and percent from the correct answers
     }
    incorrect := 10 - correct;
    percent := correct * 10;
    
    {
        Printing the output
    }
    writeln('Student ', i, ': ', correct, ' correct ', incorrect, ' incorrect ', percent, ' percent');
end;
{
    END of the DisplayStudentsData Procedure
}


{
    START of the DisplayIncorrectAnswers procedure.
    This is a procedure ti display the missed answers by the students in the 
    quizzes
}
Procedure DisplayIncorrectAnswers();
{
    Variable declaration starts
}
var 
    {
        i - an integer that is used for looping through the missedAnswersArray array
    }
    i: Integer;

{
    The procedure begins
}
begin
    {
        Printing the question numbers
    }
    writeln('Q1      Q2      Q3      Q4      Q5      Q6      Q7      Q8      Q9      Q10');
    
    {
        looping through the missedAnswersArray and printing the number of missed answers
    }
    for i := 1 to 10 do
    begin
        {
            Print the number of missed answers
        }
        write(missedQuestionsArray[i], '       ');
    
    end;

end;
{
    END of the DisplayIncorrectAnswers procedure
}


{
    The main program begins
}
begin

    {
        Reading the first line that contains the correct answers
    }
    readln(correctAnswers);
    
    {
        Storing the just read correctAnswers in an array
    }
    StoreCorrectAnswers(correctAnswers);
    
    {
        Reading the second line that has the number of students whose data is given
    }
    readln(numOfStudents);
    
    {
        Going through all the students data 
    }
    for studentNumber := 1 to numOfStudents do
    begin
    
        {
            Read one students data
        }
        readln(studentAnswer);
        
        {
            Display one student's data
        }
        DisplayStudentsData(studentNumber, studentAnswer);
    end;
    {
        End of the loop that goes through all of the students data
    }
    
    {
        Displaying the incorrect answers
    }
    DisplayIncorrectAnswers();
    
    
end.
{
    The main program ends
}