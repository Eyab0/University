#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include<string.h>
#include <ctype.h>
#include <limits.h>
#include<math.h>
/***********************
 *      COMP2421       *
 *     Project#1       *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Section : 1         *
 ***********************/

#define MAX_DLL 1000
#define MAX_STRING 4095
typedef int boolean;
#define true 1
#define false 0
int numOfPolys = 0;
typedef char string[MAX_STRING];


/*creat linked-list*/
typedef struct node *pntr;
typedef struct node {
    double coefficient;
    int power;
    pntr next; // Pointer to next node
    pntr prev; // Pointer to previous node
} Node;
typedef pntr List;

/*global initializing of linked list array*/
List Ploy[MAX_DLL];

//*******************************   Function   ******************************************
void PrintList(Node *L);

int str_len(string s);

double setXvalue(const List ply, const double X);

void removeCH(char **str, int remove, int strSize);

void addCH(char **str, char add, int place, int strSize);

void assignValues(char *str, double *coef, int *power);

void sortList(Node *l);

char *stringToTrim(char *str);

void stringToPoly(const char *str_in, const List *ply);

void displayMenu();

void displayAuthorInfo();

void readFile(const List *Poly[]);

boolean IsEmpty(Node *L);

boolean IsLast(struct node *n, struct node *L);

void PrintPolyOnScreen(Node *nd);

void PrintPolyOnFile(Node *nd, int ASM);

void PrintList(Node *L);

void addPoly(const List result, Node *f[]);

void subPoly(const List result, Node *f[]);

struct node *multiply2poly(struct node *f1, struct node *f2);

struct node *multiplyArrayOfPoly();

void deleteList();


//**************************************************************************************


//_________________________________Main Function________________________________________
int main() {


    for (int i = 0; i < MAX_DLL; ++i) {
        Ploy[i] = (pntr) malloc(sizeof(Node));
        Ploy[i]->next = NULL;
        Ploy[i]->prev = NULL;
    }


    const List resultAdd = (pntr) malloc(sizeof(Node));
    resultAdd->next = NULL;
    resultAdd->prev = NULL;
    const List resultSub = (pntr) malloc(sizeof(Node));
    resultSub->next = NULL;
    resultAdd->prev = NULL;

    int choice, ch;
    double x;
    do {
        system("cls");
        displayMenu();
        scanf("%d", &choice);
        printf("\n");
        switch (choice) {
            case 1:
                system("cls");
                readFile(Ploy);
                printf("The file has been read successfully...\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 2:
                system("cls");
                for (int i = 0; i < numOfPolys; ++i) {
                    printf("F(x%d) = ", i + 1);
                    PrintPolyOnScreen(Ploy[i]);
                    printf("\n");
                }
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 3:
                system("cls");
                addPoly(resultAdd, Ploy);
                printf("the result of addition : ");
                PrintPolyOnScreen(resultAdd);
                printf("\n");
                printf("please select your choice :\n"
                       "1)set a value of the variable term in the equation to substitute. \n"
                       "2)Back to main menu. ");
                printf("\n>");
                scanf("%d", &ch);
                if (ch == 1) {
                    printf("enter the value of x : ");
                    scanf("%lf", &x);
                    printf("f(%.2f) = %.2f \n", x, setXvalue(resultAdd, x));
                    printf("\n\nPress any key to back to menu...");
                    getch();

                }
                break;
            case 4:
                system("cls");
                subPoly(resultSub, Ploy);
                printf("the result of subtraction : ");
                PrintPolyOnScreen(resultSub);
                printf("\n");
                printf("please select your choice :\n"
                       "1)set a value of the variable term in the equation to substitute. \n"
                       "2)Back to main menu. ");
                printf("\n>");
                scanf("%d", &ch);
                if (ch == 1) {
                    printf("enter the value of x : ");
                    scanf("%lf", &x);
                    printf("f(%.2f) = %.2f \n", x, setXvalue(resultSub, x));
                    printf("\n\nPress any key to back to menu...");
                    getch();
                }

                break;
            case 5:
                system("cls");
                printf("the result of multiplication : ");
                PrintPolyOnScreen(multiplyArrayOfPoly());
                printf("\n");
                printf("please select your choice :\n"
                       "1)set a value of the variable term in the equation to substitute. \n"
                       "2)Back to main menu. ");
                printf("\n>");
                scanf("%d", &ch);
                if (ch == 1) {
                    printf("enter the value of x : ");
                    scanf("%lf", &x);
                    printf("f(%.2f) = %.2f \n", x, setXvalue(multiplyArrayOfPoly(), x));
                    printf("\n\nPress any key to back to menu...");
                    getch();
                }
                break;
            case 6:
                system("cls");
                addPoly(resultAdd, Ploy);
                subPoly(resultSub, Ploy);
                PrintPolyOnFile(resultAdd, 1);
                PrintPolyOnFile(resultSub, 2);
                PrintPolyOnFile(multiplyArrayOfPoly(), 3);
                printf("All the results of all operations have been stored in the file \'results.txt\'");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 7:
                displayAuthorInfo();
                break;
            default:
                system("cls");
                printf("Invalid choice !!\n");
                printf(">>Make sure you choose number (1 to 7) only from the available options.\n");
                printf("\n\nPress any key to back to menu and try again ...");
                getch();

                break;


        }
    } while (choice != 7);

    deleteList();

    return 0;
}

/*get length of string*/
int str_len(string s) {
    int i = 0;
    while (s[i] != '\0') {
        i++;
    }
    return i;
}

double setXvalue(const List ply, const double X) {
    pntr tmp;
    double sumRESULT = 0;
    tmp = ply->next;
    while (tmp != NULL) {
        sumRESULT += ((tmp->coefficient) * pow(X, (tmp->power)));

        tmp = tmp->next;
    }

    return sumRESULT;
}

void removeCH(char **str, int remove, int strSize) {
    char *ch = (char *) calloc(strSize - 1, sizeof(char));

    for (int j = 0; j < strSize; j++) {
        if (j < remove) {
            ch[j] = (*str)[j];
        } else if (j == remove) {
            // do nothing
        } else if (j > remove) {
            ch[j - 1] = (*str)[j];
        }
    }

    free(*str);
    *str = ch;
}

void addCH(char **str, char add, int place, int strSize) {
    char *ch = (char *) calloc(strSize + 1, sizeof(char));

    for (int j = 0; j < strSize + 1; j++) {
        if (j < place) {
            ch[j] = (*str)[j];
        } else if (j == place) {
            ch[j] = add;
        } else if (j > place) {
            ch[j] = (*str)[j - 1];
        }
    }

    free(*str);
    *str = ch;
}

void assignValues(char *str, double *coef, int *power) {

    sscanf(str, "%lfx%d", coef, power);

}

void sortList(Node *l) {


    List pntLIST = l;
    pntr tempLIST, prev, powerMIN, powerPREmin;
    int powerMin;

    while (pntLIST->next != NULL) {

        tempLIST = pntLIST->next;
        prev = pntLIST;
        powerMin = INT_MAX;

        while (tempLIST != NULL) {
            if (!(int) tempLIST->coefficient) {
                prev->next = tempLIST->next;
                free(tempLIST);
                tempLIST = prev->next;
                continue;
            }

            if (tempLIST->power < powerMin) {
                powerMIN = tempLIST;
                powerPREmin = prev;
                powerMin = powerMIN->power;
            } else if (tempLIST->power == powerMin) {
                powerMIN->coefficient += tempLIST->coefficient;

                prev->next = tempLIST->next;
                free(tempLIST);
                tempLIST = prev->next;

                if (!(int) powerMIN->coefficient) {
                    powerPREmin->next = powerMIN->next;
                    free(powerMIN);

                    tempLIST = pntLIST->next;
                    prev = pntLIST;
                    powerMin = -1;

                    continue;
                }

                continue;
            }

            prev = tempLIST;
            tempLIST = tempLIST->next;
        }

        //replaces the Min Node to the top of the list
        // repeat the process
        powerPREmin->next = powerMIN->next;
        tempLIST = pntLIST->next;
        powerMIN->next = tempLIST;
        pntLIST->next = powerMIN;
        pntLIST = pntLIST->next;
    }


}

char *stringToTrim(char *str) {
    while (isspace((unsigned char) *str)) {
        str++;
    }
    if (*str == 0)
        return str;

    char *last;
    last = str + str_len(str) - 1;
    while ((last > str) && (isspace((unsigned char) *last))) {
        last--;
    }

    last[1] = '\0';

    /*return new trimmed string */
    return str;
}

void stringToPoly(const char *str_in, const List *ply) {


    char *stringCopy;
    pntr tempLIST = ply;
    int counter, strSize;
    strSize = str_len(str_in);
    boolean isSign = true;
    boolean isNumber = false;
    boolean isPower = false;
    boolean isDecimal = false;
    stringCopy = (char *) calloc(strSize, sizeof(char));
    for (counter = 0; counter < strSize; counter++) {

        stringCopy[counter] = str_in[counter];

    }

    counter = 0;
    while (stringCopy[counter] != '\0') {
        if (stringCopy[counter] == ' ' || stringCopy[counter] == '\t') {
            removeCH(&stringCopy, counter--, strSize--);
        }
        ++counter;
    }

    /*if string have no sign on the beginning add to it "+" sign */
    if (stringCopy[0] != '+' && stringCopy[0] != '-') {
        addCH(&stringCopy, '+', 0, strSize++);
    }

    /* Preparing the string*/
    counter = 1;
    while (stringCopy[counter] != '\0') {
        if (isSign) {

            if (stringCopy[counter] >= '0' && stringCopy[counter] <= '9') {
                isSign = false;
                isNumber = true;
            } else if (stringCopy[counter] == 'x') {
                addCH(&stringCopy, '1', counter--, strSize++);
                isSign = false;
                isNumber = true;
            }
        } else if (isNumber && !isPower) // coefficient numbers
        {
            if (stringCopy[counter] >= '0' && stringCopy[counter] <= '9') {
                //do nothing
            } else if (stringCopy[counter] == '.' && !isDecimal) {
                isDecimal = true;
            } else if (isDecimal && stringCopy[counter] == '.') // remove extra decimal points
            {
                removeCH(&stringCopy, counter--, strSize--);
            } else if (stringCopy[counter] == 'x') {
                stringCopy[counter] = 'x';
                isNumber = false;
                isPower = true;
                isDecimal = false;
            } else if ((stringCopy[counter] == '-' || stringCopy[counter] == '+')) {
                addCH(&stringCopy, 'x', counter++, strSize++);
                addCH(&stringCopy, '0', counter++, strSize++);
                addCH(&stringCopy, ' ', counter++, strSize++);
                isNumber = false;
                isPower = false;
                isDecimal = false;
                isSign = true;
            }
        } else if (isPower) {
            /*removes '^' signs if didn't reach a number (the power)*/
            if (stringCopy[counter] == '^' && !isNumber) {
                removeCH(&stringCopy, counter--, strSize--);
                isNumber = true;
            } else if (stringCopy[counter] >= '0' && stringCopy[counter] <= '9') {
                isNumber = true;
            }
                /*reach the end of term -> add space */
            else if (stringCopy[counter] == '-' || stringCopy[counter] == '+') {
                if (!isNumber) {
                    addCH(&stringCopy, '1', counter++, strSize++);
                }
                addCH(&stringCopy, ' ', counter++, strSize++);

                isNumber = false;
                isPower = false;
                isSign = true;
            }
        } else {
            free(stringCopy);
            printf("Error !! ,invalid Polynomial String !! \n");
            exit(1);
        }

        ++counter;
    }

    //checks the end of the string
    if ((stringCopy[strSize - 1] >= '0' && stringCopy[strSize - 1] <= '9')) {
        /*nds with a number with no "x" */
        if (isNumber && !isPower) {
            addCH(&stringCopy, 'x', strSize - 1, strSize++);
            addCH(&stringCopy, '0', strSize - 1, strSize++);
        }
    }
        /*ends with x^1 but it's x only*/
    else if (stringCopy[strSize - 1] == 'x') {
        addCH(&stringCopy, '1', strSize - 1, strSize++);
    }

    /*Creating the polynomial as a List*/
    char *term_token = strtok(stringCopy, " ");
    int power;
    double coef;
    pntr tempNode = NULL;
    while (term_token) {
        assignValues(stringToTrim(term_token), &coef, &power);

        tempNode = (pntr) malloc(sizeof(Node)); //creates a new Node
        /*store coefficient and power*/
        tempNode->coefficient = coef;
        tempNode->power = power;
        tempNode->next = NULL;
        /*connects it to the List*/
        tempLIST->next = tempNode;
        tempNode->prev = tempLIST;
        tempLIST = tempLIST->next;
        tempNode = NULL;
        term_token = strtok(NULL, " ");
    }
    sortList(ply);

}

void displayMenu() {

    system("cls");
    system("COLOR F0");
    printf("\n\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB                   << Menu >>                           \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB 1.Read File and store polynomial equations             \xDB\n");
    printf("\t\t\t\xDB 2.Show polynomial equations on screen                  \xDB\n");
    printf("\t\t\t\xDB 3.implement addition on polynomial equations           \xDB\n");
    printf("\t\t\t\xDB 4.implement subtraction on polynomial equations        \xDB\n");
    printf("\t\t\t\xDB 5.implement multiplication on polynomial equations     \xDB\n");
    printf("\t\t\t\xDB 6.store the results of all operations in results file  \xDB\n");
    printf("\t\t\t\xDB 7.Exit..                                               \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t\t Enter Your Choice >>");
}

void readFile(const List *Poly[]) {

    FILE *inFile;
    inFile = fopen("equations.txt", "r");

    if (inFile == NULL) {
        printf("Error !!,file not exists.. \n");
        printf("-Make sure that the file name is \"equations.txt\"\n");
        printf("Try Again...");
        return;
        //exit(-1);
    }
    string strIn;
    fgets(strIn, MAX_STRING, inFile);
    do {
        stringToPoly(stringToTrim(strIn), Poly[numOfPolys]);
        numOfPolys++;
    } while (fgets(strIn, MAX_STRING, inFile) != NULL);

    fclose(inFile);
}

boolean IsEmpty(Node *L) {
    return L->next == NULL;
}

boolean IsLast(struct node *n, struct node *L) {
    return n->next == NULL;
}

void PrintPolyOnScreen(Node *nd) {

    pntr tempLIST = nd->next;
    boolean isbegin = true;

    while (tempLIST != NULL) {
        if (!(int) tempLIST->coefficient) {
            tempLIST = tempLIST->next;
            printf(""); // zero value
            continue;
        }
        if (tempLIST->coefficient == 1 || tempLIST->coefficient == -1) {
            if (isbegin) {
                if (tempLIST->power > 0) {
                    if (tempLIST->coefficient == 1) {
                        /*do nothing*/
                    } else if (tempLIST->coefficient == -1) {
                        printf("-");
                    }
                } else {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        printf("%.0lf", tempLIST->coefficient);
                    else
                        printf("%.3lf", tempLIST->coefficient);
                }

                isbegin = false;
            } else {
                if (tempLIST->power > 0) {
                    if (tempLIST->coefficient == 1) {
                        printf(" +");
                    } else if (tempLIST->coefficient == -1) {
                        printf(" -");
                    }
                } else {
                    if (tempLIST->coefficient == 1) {
                        if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                            printf(" +%.0lf", tempLIST->coefficient);
                        else
                            printf(" +%.3lf", tempLIST->coefficient);
                    } else if (tempLIST->coefficient == -1) {
                        if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                            printf(" %.0lf", tempLIST->coefficient);
                        else
                            printf(" %.3lf", tempLIST->coefficient);
                    }
                }
            }
        } else if (tempLIST->coefficient > 0 || tempLIST->coefficient < 0) {
            if (isbegin) {
                if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                    printf("%.0lf", tempLIST->coefficient);
                else
                    printf("%.3lf", tempLIST->coefficient);
                isbegin = false;
            } else {
                if (tempLIST->coefficient > 0) {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        printf(" +%.0lf", tempLIST->coefficient);
                    else
                        printf(" +%.3lf", tempLIST->coefficient);
                } else {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        printf(" %.0lf", tempLIST->coefficient);
                    else
                        printf(" %.3lf", tempLIST->coefficient);
                }
            }

            if (tempLIST->power > 0) {
                printf("*");
            }
        }

        if (tempLIST->power == 0) {}
        else {
            printf("x");

            if (tempLIST->power != 1) {
                printf("^%.0i", tempLIST->power);
            }
        }

        tempLIST = tempLIST->next;
    }
}

/*used for debugging */
void PrintList(Node *L) {

    Node *P = L;
    if (IsEmpty(L))
        printf("Empty list\n");
    else {
        do {
            P = P->next;
            printf("%fx^%d\t", P->coefficient, P->power);
        } while (!IsLast(P, L));
        printf("\n");
    }

}

void addPoly(const List result, Node *f[]) {

    pntr tmp, tmp_res;
    tmp_res = result;

    for (int i = 0; i < numOfPolys; i++) {

        tmp = f[i]->next;
        while (tmp != NULL) {
            tmp_res->next = (pntr) malloc(sizeof(Node));
            tmp_res = tmp_res->next;

            tmp_res->coefficient = tmp->coefficient;
            tmp_res->power = tmp->power;
            tmp_res->next = NULL;

            tmp = tmp->next;
        }

    }


    sortList(result);
}

void subPoly(const List result, Node *f[]) {

    pntr tmp;
    for (int i = 1; i < numOfPolys; i++) {
        tmp = f[i]->next;
        while (tmp != NULL) {
            (tmp->coefficient) *= (-1);
            tmp = tmp->next;
        }
    }
    addPoly(result, f);
    for (int i = 1; i < numOfPolys; i++) {
        tmp = f[i]->next;
        while (tmp != NULL) {
            (tmp->coefficient) *= (-1);
            tmp = tmp->next;
        }
    }
}

struct node *multiply2poly(struct node *f1, struct node *f2) {
    struct node *result = (struct node *) malloc(sizeof(struct node));
    pntr tmp1, tmp2, tmp_res;

    tmp1 = f1->next;
    tmp_res = result;

    while (tmp1 != NULL) {
        tmp2 = f2->next;

        while (tmp2 != NULL) {
            tmp_res->next = (pntr) malloc(sizeof(Node));
            tmp_res = tmp_res->next;

            tmp_res->coefficient = (tmp1->coefficient) * (tmp2->coefficient);
            tmp_res->power = (tmp1->power) + (tmp2->power);
            tmp_res->next = NULL;

            tmp2 = tmp2->next;
        }

        tmp1 = tmp1->next;
    }

    sortList(result);
    return result;

}

struct node *multiplyArrayOfPoly() {
    pntr result;
    result = Ploy[0];
    for (int i = 1; i < numOfPolys; ++i) {
        result = multiply2poly(result, Ploy[i]);
    }

    sortList(result);
    return result;
}

void displayAuthorInfo() {
    system("cls");
    system("COLOR 17");
    printf("\n\t\t\t \xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB-INFO-\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB           COMP2421          \xDB\n");
    printf("\t\t\t\xDB           Project#1         \xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB          @Author            \xDB\n");
    printf("\t\t\t\xDB     Name : Eyab Ghifari     \xDB\n");
    printf("\t\t\t\xDB     ID : 1190999            \xDB\n");
    printf("\t\t\t\xDB     Section : 1             \xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
}

void PrintPolyOnFile(Node *nd, int ASM) {
    FILE *outFile;
    outFile = fopen("results.txt", "a");
    if (outFile == NULL) {
        printf("Error In File !!\n");
        exit(1);
    }
    pntr tempLIST = nd->next;
    boolean isbegin = true;

    if (ASM == 1)
        fprintf(outFile, "the result of addition : ");
    else if (ASM == 2)
        fprintf(outFile, "the result of subtraction : ");
    else
        fprintf(outFile, "the result of multiplication :");
    while (tempLIST != NULL) {
        if (!(int) tempLIST->coefficient) {
            tempLIST = tempLIST->next;
            fprintf(outFile, ""); // zero value
            continue;
        }
        if (tempLIST->coefficient == 1 || tempLIST->coefficient == -1) {
            if (isbegin) {
                if (tempLIST->power > 0) {
                    if (tempLIST->coefficient == 1) {
                        /*do nothing*/
                    } else if (tempLIST->coefficient == -1) {
                        fprintf(outFile, "-");
                    }
                } else {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        fprintf(outFile, "%.0lf", tempLIST->coefficient);
                    else
                        fprintf(outFile, "%.3lf", tempLIST->coefficient);
                }

                isbegin = false;
            } else {
                if (tempLIST->power > 0) {
                    if (tempLIST->coefficient == 1) {
                        fprintf(outFile, " +");
                    } else if (tempLIST->coefficient == -1) {
                        fprintf(outFile, " -");
                    }
                } else {
                    if (tempLIST->coefficient == 1) {
                        if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                            fprintf(outFile, " +%.0lf", tempLIST->coefficient);
                        else
                            fprintf(outFile, " +%.3lf", tempLIST->coefficient);
                    } else if (tempLIST->coefficient == -1) {
                        if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                            fprintf(outFile, " %.0lf", tempLIST->coefficient);
                        else
                            fprintf(outFile, " %.3lf", tempLIST->coefficient);
                    }
                }
            }
        } else if (tempLIST->coefficient > 0 || tempLIST->coefficient < 0) {
            if (isbegin) {
                if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                    fprintf(outFile, "%.0lf", tempLIST->coefficient);
                else
                    fprintf(outFile, "%.3lf", tempLIST->coefficient);
                isbegin = false;
            } else {
                if (tempLIST->coefficient > 0) {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        fprintf(outFile, " +%.0lf", tempLIST->coefficient);
                    else
                        fprintf(outFile, " +%.3lf", tempLIST->coefficient);
                } else {
                    if ((tempLIST->coefficient) - ((int) tempLIST->coefficient) == 0)
                        fprintf(outFile, " %.0lf", tempLIST->coefficient);
                    else
                        fprintf(outFile, " %.3lf", tempLIST->coefficient);
                }
            }

            if (tempLIST->power > 0) {
                fprintf(outFile, "*");
            }
        }

        if (tempLIST->power == 0) {
            //do nothing
        } else {
            fprintf(outFile, "x");

            if (tempLIST->power != 1) {
                fprintf(outFile, "^%.0i", tempLIST->power);
            }
        }


        tempLIST = tempLIST->next;
    }
    fprintf(outFile, "\n");
}

void deleteList() {
    for (int i = 0; i < numOfPolys; ++i) {
        free(Ploy[i]);

    }
}
