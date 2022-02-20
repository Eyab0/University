from Semester import *


# this class is the student class which have all attributes that related to the student
class Student:
    # save the total number of students
    numberOfStudents = 0

    # the constructor of the student class
    def __init__(self, Id):
        self.Id = Id  # the id of this student object
        self.semesters = {}  # to save all semester to this student object
        self.courses = {}  # to save all courses in all semester for this student object
        Student.numberOfStudents += 1

    # to add a new semester to this student object
    def AddNewSemster(self, newSemster):
        self.semesters[newSemster] = Semseter(newSemster)

    # to add a new course to a specific semester with the course grade
    def AddNewCourseToTheSemster(self, semster, course, grade):
        self.semesters[semster].AddNewCourseToTheSemster(course, grade)
        self.courses[course] = grade

    # to get the student average for a specific semester
    def getSemsterAvg(self, semster):
        return self.semesters[semster].getAverage()

    # to get the student average for all courses (cumulative average)
    def getCumulativeAverage(self):
        Sum = 0
        for i in self.courses:
            hours = int(i[5])
            Sum += hours * self.courses[i]
        return Sum / self.getTotalHours() if self.getTotalHours() != 0 else 0

    # to get the total hours the student takes
    def getTotalHours(self):
        TotalHoursForStudent = 0
        for i in self.courses:
            hours = int(i[5])
            TotalHoursForStudent += hours
        return TotalHoursForStudent