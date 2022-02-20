"""""
                                   Python Project 
                          Students Records Management System
    
                                 /***********************
                                 *      ENCS3110       *
                                 *     Project#2       *
                                 *   Date: 7-1-2022    *
                                 ***********************/


          /************************        |             /*********************** 
          * Name : Kareem Afaneh *         |             * Name : Eyab Ghifari  *
          * ID : 1190359         *         |             * ID : 1190999         *
          * Section : ?          *         |             * Section : 2          *
          ***********************/         |             ***********************/
         
         
"""""
import os
import pathlib
import shutil
import matplotlib.pyplot as plt
import numpy as np
from Student import *

coursesList = []  # this list is to store all courses from courses.txt file in the computer engineering
studentsFiles = {}  # this dictionary to store all Students objects
TotalHours = 0  # this variable calculate total hours in the Computer engineering
semesters = set()  # this set will store all semesters that entered


# this function reads from courses.txt file all the courses in the computer engineering
def readCoursesFile():
    global TotalHours  # to use the global variable TotalHours
    try:
        # open the courses.txt file to read from
        with open("courses.txt") as f:
            for line in f:  # read rest of lines
                coursesList.append(line.rstrip())  # rstrip is used to delete the new line '\n' at the end
                TotalHours += int(line[5])
    # this except is to handle if the file does not exist
    except IOError as error:
        print('\x1b[6;30;41m' + str(error) + '\x1b[0m')


# this function will read all the students files and store all their informations
def ReadStudentsIDFiles():
    # read the files name in "Students" path
    for files in os.listdir(os.path.join(os.getcwd(), 'Students')):
        filePath = pathlib.Path(os.path.join(os.getcwd(), 'Students', files))
        # check if the file is a txt file with length 11 ==> 7 digits and '.txt'
        if filePath.is_file() and len(files) == 11 and files.endswith(
                ".txt") and str(files)[0:7].isdigit() and not (str(files)[0:7] in studentsFiles):
            try:
                # open the file to read the data from it
                with open(filePath) as f:
                    fileSize = os.path.getsize(filePath)
                    # if the file is empty then raise an error
                    if fileSize == 0:
                        raise "Error , file is empty !!"
                    num = int(files[:7])
                    # store the file name (the id) as an student object
                    studentsFiles[num] = Student(num)
                    # the flag is to read the first line on the file
                    flag = 1
                    for line in f:  # read file lines
                        if flag == 1:
                            flag = 0
                        else:
                            # each line will contain a new semester
                            # so we add it to the student object as a semester object
                            AddTheSemester(num, line)
            # this exception is used to handle if there is an error in opening the file
            except IOError as error:
                print('\x1b[6;30;41m' + str(error) + '\x1b[0m')


# this function contains the admin menu
def adminMenu():
    print('\x1b[6;30;42m' + '----------------------Admin Menu ----------------------' + '\x1b[0m')
    print('\x1b[6;30;32m' + '1)Add a new record' + '\x1b[0m')
    print('\x1b[6;30;32m' + '2)Add new semester with student course and grades' + '\x1b[0m')
    print('\x1b[6;30;32m' + '3)Update Student Record' + '\x1b[0m')
    print('\x1b[6;30;32m' + '4)Show Student statistics' + '\x1b[0m')
    print('\x1b[6;30;32m' + '5)Show Global statistics' + '\x1b[0m')
    print('\x1b[6;30;32m' + "6)Searching for ID's Student based on average or taken hours." + '\x1b[0m')
    print('\x1b[6;30;32m' + '7)Back to main menu' + '\x1b[0m')
    print('\x1b[6;30;32m' + '8)Exit..' + '\x1b[0m')
    print("Enter Choice :")


# this function contains the student menu
def studentMenu():
    print('\x1b[6;30;42m' + '----------------------Student Menu ----------------------' + '\x1b[0m')
    print('\x1b[6;30;32m' + '1)Show Student statistics' + '\x1b[0m')
    print('\x1b[6;30;32m' + '2)Show Global statistics' + '\x1b[0m')
    print('\x1b[6;30;32m' + '3)Back to main menu' + '\x1b[0m')
    print('\x1b[6;30;32m' + '4)Exit..' + '\x1b[0m')
    print("Enter Choice :")


# this function is used in searching option in admin menu
# which let the user to enter what is the criteria he wants to search based on
def criteria():
    print("choose the criteria you want to search based on")
    print("1)equal (e.g 70=)")
    print("2)Larger Than (e.g 70>)")
    print("3)Smaller (e.g 70<)")
    criter = input()
    # if the user entered an invalid choice raise an error
    if criter != "1" and criter != "2" and criter != "3":
        print('\x1b[6;30;41m' + "invalid choice! Try again" + '\x1b[0m')
        criteria()
    # otherwise return the option he chose
    else:
        return criter


# this function is used to add a new semester with all its courses to the student object with (num) id
def AddTheSemester(num, line):
    # split the line using ';' to get the semester alone and the courses alone
    index = line.find(";")
    sems = line[:index].rstrip().split()
    sems = sems[0]
    # add the semester to the set of all existed semesters
    semesters.add(sems)
    studentsFiles[num].AddNewSemster(sems)
    # split the other part of the line (the courses part) based on ',' to get each course with its grade alone
    courses = (line[index + 1:].split(","))
    for i in courses:
        block = i.split()
        course = block[0]
        grade = int(block[1])
        # add the course and the grade to the student object in the specific semester
        studentsFiles[num].AddNewCourseToTheSemster(sems, course, grade)


# this function is the first option in the admin menu which will let you to add a new student
def AddNewRecord():
    print("Please enter the new ID to add its own record:")
    newID = input()
    # if the user entered an invalid input raise an error
    if not newID.isdigit():
        print('\x1b[6;30;41m' + "Error , You should enter a number!!" + '\x1b[0m')
        return
    # if the number entered is not 7 digits or not started with '1', raise an error
    if len(newID) == 7 and newID[0] == "1":
        newID = int(newID)
        try:
            # if the id entered is not existed then create a file with id as a name
            if newID not in studentsFiles.keys():
                studentsFiles[newID] = Student(newID)
                fileName = str(newID) + ".txt"
                newFile = open(fileName, "w+")
                newFile.write("Year/semester ; Courses with grades\n")
                newFile.close()
                # move the file to "Student" directory
                shutil.move(fileName, os.path.join(os.getcwd(), 'Students'))
                # ask the user if he wants to add a semester to the file
                print("do you want to add any semester to this student ?")
                print("1)yes")
                print("2)no")
                choice = input()
                if choice == '1':
                    # if he wants to add a semester go to addNewSemester function
                    addNewSemester(newID)
                    print('\x1b[6;30;32m' + 'This student has been successfully added with his info.' + '\x1b[0m')
                else:
                    print('\x1b[6;30;32m' + 'This student has been successfully added' + '\x1b[0m')
            # if the id is already exist raise an error
            else:
                raise ValueError("Error , This ID already exists !!")
        # to catch the exceptions
        except ValueError as ve:
            print('\x1b[6;30;41m' + str(ve) + '\x1b[0m')
    else:
        print('\x1b[6;30;41m' + "invalid id number" + '\x1b[0m')
        print('\x1b[6;30;41m' + "the Id must be with right format, 7 digits starting with 1" + '\x1b[0m')
        return


# this is the second option in admin menu which will let you to add a new semester
def addNewSemester(IDtoEdit):
    # if the id does not exist raise an error because there is no place to add the semester
    if IDtoEdit not in studentsFiles.keys():
        print('\x1b[6;30;41m' + "Error , this id not found !!" + '\x1b[0m')
    else:
        # otherwise search for the specific file on the "Students" directory
        for files in os.listdir(os.path.join(os.getcwd(), 'Students')):
            filePath = pathlib.Path(os.path.join(os.getcwd(), 'Students', files))
            if str(files) == (str(IDtoEdit) + ".txt"):
                try:
                    # if the file is found then open it to add the new information
                    with open(filePath, "a") as f:
                        print("How many semester do want to add ?")
                        semester_s = input()
                        if not (semester_s.isdigit()):
                            raise ValueError("Error , wrong format !!")
                        for semester in range(int(semester_s)):
                            line = ""
                            print("Please Enter Year (example: 2020-2021) :")
                            year = input()
                            year = year.replace(" ", "")
                            yearList = year.split("-")
                            # this if statement is used to handle the entered year format and check it
                            if not (yearList[0].isdigit() and len(yearList[0]) == 4 and yearList[1].isdigit() and len(
                                    yearList[1]) == 4 and ((int(yearList[0]) + 1) == int(yearList[1]))):
                                raise ValueError("Error , wrong year format !!")
                            print(
                                "Please Enter the semester (1 represents first semester 2 for second semester, "
                                "3 for summer semester)")
                            semster = input()
                            # there are three allowed semesters 1 and 2 and 3, otherwise raise an error
                            if not (semster == '1' or semster == '2' or semster == '3'):
                                raise ValueError("Error , wrong semester format !!")
                            yearSem = year + "/" + semster
                            line += (yearSem + " ; ")
                            print("How many course do want to add ?")
                            courses = input()
                            coursesNewList = []
                            for course in range(int(courses)):
                                print("Please Enter #", course + 1, "Course Name :")
                                # if the course name does not exist in the available raise an error
                                courseName = input()
                                if courseName not in coursesList:
                                    raise ValueError("Error , wrong course name (ENCS or ENEE only) !!")
                                # if the course is already add to the semester raise an error
                                if courseName in coursesNewList:
                                    raise ValueError("Error , repeated course name  !!")
                                coursesNewList.append(courseName)
                                print("Please Enter #", course + 1, " Course Grade : ")
                                courseGrade = input()
                                # to handle what the user entered, he/she allowed to add numbers only
                                if not courseGrade.isdigit():
                                    raise ValueError("Error , invalid course grade!!(only numbers)")
                                courseGrade = int(courseGrade)
                                # if the user adds a negative number raise an error
                                if courseGrade < 0:
                                    raise ValueError("Error , invalid course grade!!(no negative numbers)")
                                if courseGrade > 99:
                                    courseGrade = 99
                                if courseGrade < 60:
                                    courseGrade = 55
                                    print()
                                line += (str(courseName) + " " + str(courseGrade) + ", ")
                            # add the information's to the student object
                            AddTheSemester(IDtoEdit, line[:-2])
                            # add the information's to the student file
                            f.write(line[:-2] + "\n")
                        # close the student file
                        f.close()
                # handle the exceptions
                except IOError as error:
                    print('\x1b[6;30;41m' + str(error) + '\x1b[0m')
                except ValueError as ve:
                    print('\x1b[6;30;41m' + str(ve) + '\x1b[0m')


# this function will update a student grade in a course
def updateStudentRecord():
    print("Please enter the new ID to change its record:")
    IDtoEdit = input()
    # if the user enter a non digit value raise an error
    if not IDtoEdit.isdigit():
        raise ValueError("Error , invalid id !!")
    IDtoEdit = int(IDtoEdit)
    # if the id entered does not exist raise an error
    if IDtoEdit not in studentsFiles.keys():
        print("Error , this id not found !!")
    print("Please Enter the Course name to change its grade:")
    try:
        courseName = input()
        # if the course entered does not exist in the list of all courses
        if courseName not in coursesList:
            raise ValueError("Error , wrong course name !!")
        # if the student didn't take the course raise an error
        if courseName not in studentsFiles[IDtoEdit].courses:
            raise ValueError("Error , this student didn't take this course !!")

        print("Please Enter the new grade:")
        newGrade = input()
        # if the user entered a non digit value
        if not newGrade.isdigit():
            raise ValueError("Error , invalid course grade !!")
        newGrade = int(newGrade)
        # if the new value was negative raise an error
        if newGrade < 0:
            raise ValueError("Error , invalid course grade !!")
        if newGrade > 99:
            newGrade = 99  # if the value is larger than 99 put it 99
        if newGrade < 60:
            newGrade = 55  # if the grade was as a fail put it 55
        # update the value in the student object courses
        studentsFiles[IDtoEdit].courses[courseName] = newGrade
        for semester in studentsFiles[IDtoEdit].semesters:
            for course in studentsFiles[IDtoEdit].semesters[semester].courses:
                # update the grade in the semester if and only if the semester has the course
                if course == courseName:
                    studentsFiles[IDtoEdit].semesters[semester].courses[course] = newGrade
    except ValueError as ve:
        print('\x1b[6;30;41m' + str(ve) + '\x1b[0m')
    try:
        # now we need to update the data in the value itself, so we open it and overwrite the data on it
        fileName = str(IDtoEdit) + ".txt"
        newFile = open(os.path.join(os.getcwd(), 'Students', fileName), "w")
        newFile.write("Year/semester ; Courses with grades\n")
        # start to write the semesters with all courses in the file
        for i in studentsFiles[IDtoEdit].semesters:
            line = str(i) + " ; "
            for j in studentsFiles[IDtoEdit].semesters[i].courses:
                line += str(j) + " " + str(studentsFiles[IDtoEdit].semesters[i].courses[j]) + ", "
            newFile.write(line[:-2] + "\n")
    # if the file does not exist
    except IOError as error:
        print('\x1b[6;30;41m' + str(error) + '\x1b[0m')
    print('\x1b[6;30;32m' + '>>The record has been successfully modified' + '\x1b[0m')


# this function will print a student statistics like taken hours, and remaining courses
def getStudentStatistics(Id):
    try:
        # if the id entered is not a digit
        if not (Id.isdigit()):
            raise ValueError("Error , invalid id !!")
        Id = int(Id)
        # if the id does not exist
        if not (Id in studentsFiles.keys()):
            raise ValueError("Error , not existed id !!")
        index = 1
        coursesList.sort()
        # this loop will print all the remaining courses fot the student with this 'id'
        for i in coursesList:
            if i not in studentsFiles[Id].courses:
                print(index, ")", i)
                index += 1
        print("taken hours :", studentsFiles[Id].getTotalHours())
        print("average per semester :")
        # this loop will print the semesters average each alone
        for i in studentsFiles[Id].semesters:
            print(i, " : ", format(studentsFiles[Id].semesters[i].getAverage(), '.2f'))
        # here we will print the average for all courses he/she takes
        print("overall average :", format(studentsFiles[Id].getCumulativeAverage(), '.2f'))
    except ValueError as ve:
        print('\x1b[6;30;41m' + str(ve) + '\x1b[0m')


# this function will print all the students statistics and make a plot to show that
def getGlobalStatistics():
    Sum = 0
    # to sum all the students averages in all time
    for i in studentsFiles.keys():
        Sum += studentsFiles[i].getCumulativeAverage()
    # to divide the averages on the total number of students and then print the result
    print("overall students average : ", format(Sum / Student.numberOfStudents, '.2f'))
    allAvg = []
    for i in sorted(semesters):
        totH = 0
        num = 0
        # to get all the students that register for that semester and sum their hours
        for j in studentsFiles.keys():
            if i in studentsFiles[j].semesters:
                totH += studentsFiles[j].semesters[i].TotalHoursForSemster
                num += 1
        # print the average of taken hours in the specific semester
        print(i, " : ", format(totH / num, '.2f'))
    # get all students cumulative averages
    for i in studentsFiles.keys():
        allAvg.append(studentsFiles[i].getCumulativeAverage())
    # to plot the histogram based on (allAvg) which is a list that contains students cumulative averages
    plt.hist(allAvg, bins=20, color='lightgreen', ec='black')
    plt.title("Distribution of Students grades")
    plt.xlabel("Average")
    plt.ylabel("Number of Students")
    plt.grid()
    plt.xticks(np.arange(0, 105, 5))
    plt.show()


# this function will do the last option of the admin menu which is search for a specific student
def searching():
    print("Please Enter the Search Method based on:")
    print("1)Average")
    print("2)Taken hours")
    based = input()
    index = 1
    try:
        # if he chose to search based on Average
        if based == "1":
            # go to criteria function to get what is the condition he wants to search based on
            choice = criteria()
            number = input("Please enter the value you want to search based on:")
            number = float(number)
            # if he wants to find all students with an averages equals to 'number'
            if choice == "1":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getCumulativeAverage() == number:
                        print(index, ")", "ID :", i, " GPA:", format(studentsFiles[i].getCumulativeAverage(), '.2f'))
                        index += 1
            # if he wants to find all students with an averages larger than 'number'
            elif choice == "2":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getCumulativeAverage() > number:
                        print(index, ")", "ID :", i, " GPA:", format(studentsFiles[i].getCumulativeAverage(), '.2f'))
                        index += 1
            # if he wants to find all students with an averages less than 'number'
            elif choice == "3":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getCumulativeAverage() < number:
                        print(index, ")", "ID :", i, " GPA:", format(studentsFiles[i].getCumulativeAverage(), '.2f'))
                        index += 1
        # if he chose to search based on taken hours
        elif based == "2":
            # go to criteria function to get what is the condition he wants to search based on
            choice = criteria()
            number = input("Please enter the value you want to search based on:")
            number = int(number)
            # if he wants to find all students with an taken hours equals to 'number'
            if choice == "1":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getTotalHours() == number:
                        print(index, ")", "ID:", i, " Total hours:", studentsFiles[i].getTotalHours())
                        index += 1
            # if he wants to find all students with an taken hours larger than 'number'
            elif choice == "2":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getTotalHours() > number:
                        print(index, ")", "ID:", i, " Total hours:", studentsFiles[i].getTotalHours())
                        index += 1
            # if he wants to find all students with an taken hours less than 'number'
            elif choice == "3":
                for i in studentsFiles.keys():
                    if studentsFiles[i].getTotalHours() < number:
                        print(index, ")", "ID:", i, " Total hours:", studentsFiles[i].getTotalHours())
                        index += 1
        # if the user entered an invalid choice
        else:
            print('\x1b[6;30;41m' + "invalid option! Try again." + '\x1b[0m')
            searching()
    except ValueError:
        print('\x1b[6;30;41m' + "invalid option! Try again." + '\x1b[0m')


# this function is the student Dashboard to let the user choose what he/she wants to do
def studentDashboard():
    # read the id of the student before shows the menu
    print("Please enter Your ID first:")
    studentID = input()
    while 1:
        # go to student menu to print the available options
        studentMenu()
        choice = input()
        if choice == "1":
            getStudentStatistics(studentID)
        elif choice == "2":
            getGlobalStatistics()
        elif choice == "3":
            break
        # to exit the program
        elif choice == "4":
            print('\x1b[6;30;41m' + 'Good bye !!' + '\x1b[0m')
            print('\033[42m' + 'Done by : Eyab & Kareem' + '\033[0m')
            exit()
        # if he/she entered an invalid option
        else:
            print('\x1b[6;30;41m' + "invalid choice!! \nTry again." + '\x1b[0m')


# this function is the admin Dashboard to let the user choose what he/she wants to do
def adminDashboard():
    while 1:
        try:
            # go to admin menu to print the available options
            adminMenu()
            choice = input()
            if choice == "1":
                AddNewRecord()
            elif choice == "2":
                print("Please enter the new ID to add its own record:")
                IDtoEdit = input()
                if not IDtoEdit.isdigit():
                    raise ValueError("Error , invalid id !!")
                IDtoEdit = int(IDtoEdit)
                addNewSemester(IDtoEdit)
            elif choice == "3":
                updateStudentRecord()
            elif choice == "4":
                print("Please enter the new ID to add its own record:")
                Id = input()
                getStudentStatistics(Id)
                print()
            elif choice == "5":
                getGlobalStatistics()
            elif choice == "6":
                searching()
            elif choice == "7":
                break
            # to exit the program
            elif choice == "8":
                print('\x1b[6;30;41m' + 'Good bye !!' + '\x1b[0m')
                print('\033[42m' + 'Done by : Eyab & Kareem' + '\033[0m')
                exit()
            # if he/she entered an invalid option
            else:
                print('\x1b[6;30;41m' + "invalid choice!! \nTry again." + '\x1b[0m')
        except ValueError as ve:
            print('\x1b[6;30;41m' + str(ve) + '\x1b[0m')


# this is the main of the program
def main():
    # first read all courses in computer engineering (ENCS and ENEE courses)
    readCoursesFile()
    # read all existed students
    ReadStudentsIDFiles()
    while 1:
        # the main menu of the system
        print('\x1b[6;30;42m' + 'Please Login to the System' + '\x1b[0m')
        print('\x1b[6;30;32m' + '  1)Admin                 ' + '\x1b[0m')
        print('\x1b[6;30;32m' + '  2)Student               ' + '\x1b[0m')
        print('\x1b[6;30;32m' + '  3)Exit..                ' + '\x1b[0m')
        print("Enter Choice :")
        option = input()
        if option == "1":
            adminDashboard()
        elif option == "2":
            studentDashboard()
        # to exit the program
        elif option == "3":
            print('\x1b[6;30;46m' + 'Good bye !!' + '\x1b[0m')
            print('\033[42m' + 'Done by : Eyab & Kareem' + '\033[0m')
            exit()
        # if he/she entered an invalid option
        else:
            print('\x1b[6;30;41m' + "invalid choice!! \nTry again." + '\x1b[0m')


# here the program will start
if __name__ == '__main__':
    print("\n")
    print('\x1b[6;30;44m' + '-------------Students Records Management System-------------' + '\x1b[0m')
    print("\n")
    main()
    print("--------------------------------------------------------------")
