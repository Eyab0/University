# this is the semester class which save the semester with the courses and grades
class Semseter:
    # the constructor of the semester class
    def __init__(self, semster):
        self.semster = semster
        self.courses = {}  # to save all the courses in this object semester
        self.TotalHoursForSemster = 0  # to save the total number of hours in this object semester

    # this function is used to add a new course to the current semester object
    def AddNewCourseToTheSemster(self, course, grade):
        # if the course is not in the courses of the semester add its hours
        if not (course in self.courses):
            self.TotalHoursForSemster += int(course[5])
        # save the grade of the course
        self.courses[course] = grade

    # to return the average of this semester
    def getAverage(self):
        avg = 0
        for i in self.courses:
            hours = int(i[5])
            avg += hours * self.courses[i]
        return avg / self.TotalHoursForSemster if self.TotalHoursForSemster != 0 else 0