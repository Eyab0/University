#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include <ctype.h>


/***********************
 *      COMP2421       *
 *     Project#2       *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Section : 1         *
 ***********************/



#define MAX_STRING 100
typedef int boolean;
#define true 1
#define false 0
int numOfStrings = 0;
int numOfRedo = 0;
int numOfUndo = 0;
boolean writeToFile = false;
FILE *OutFile;
typedef char string[MAX_STRING];
typedef struct node *pntr;
struct node {
    string str;
    pntr next;
};
typedef pntr Stack;

//*******************************   Function   ******************************************
Stack CreateStack();

boolean is_empty(Stack stc);

void push(Stack stc, string element);

void pop(Stack stc);

void MakeEmpty(Stack stc);

char *top(Stack stc);

int str_len(const string s);

char *stringToTrim(char *str);

void displayInstructions();

void displayAuthorInfo();

void redo(Stack stc, Stack UndoStack);

void undo(Stack stc, Stack UndoStack);

void PrintStackTopToBottom(Stack s);

void PrintStackSequence(Stack stc);

void DisposeStack(Stack deleteStack);


//**************************************************************************************




//_________________________________Main Function________________________________________
int main() {

    system("COLOR F0");
    displayInstructions();
    system("cls");
    Stack mainStack = CreateStack();
    Stack TrashStack = CreateStack();
    string strIn;
    do {
        printf(">");
        fgets(strIn, MAX_STRING, stdin);

        if (!strcmp("undo", stringToTrim(strIn))) {
            if (!is_empty(mainStack)) {
                printf("[-] %s\n", top(mainStack));
                printf("------------------------------------\n");
                numOfUndo++;
                numOfStrings--;
                undo(mainStack, TrashStack);
                if (numOfStrings != 0) {
                    PrintStackSequence(mainStack);
                } else {

                    printf("<< All string have been deleted >>\n");
                }
                printf("------------------------------------\n");
            } else
                printf("Error !! , you didn't \"insert\" any string yet ...\n");

        } else if (!strcmp("redo", stringToTrim(strIn))) {
            if (!is_empty(TrashStack)) {
                numOfRedo++;
                numOfStrings++;
                redo(mainStack, TrashStack);
                PrintStackSequence(mainStack);
            } else
                printf("Error !! , You didn't \"undo\" any string yet...\n");

        } else if (!strcmp("print", stringToTrim(strIn))) {
            if (!is_empty(mainStack))
                PrintStackSequence(mainStack);
            else
                printf("Error !!, Empty stack , No strings to print...\n");

        } else if ((!strcmp("save", stringToTrim(strIn))) || (!strcmp("quit", stringToTrim(strIn)))) {
            writeToFile = true;
            PrintStackSequence(mainStack);


        } else {

            push(mainStack, strIn);
//            PrintStackSequence(mainStack);
            numOfStrings++;
            if (!is_empty(TrashStack) ) {
                DisposeStack(TrashStack);

            }

        }


    } while (strcmp("quit", stringToTrim(strIn)));
    displayAuthorInfo();

    return 0;
}

Stack CreateStack() {
    Stack newStack = (Stack) malloc(sizeof(struct node));
    if (newStack != NULL) {
        newStack->next = NULL;
        MakeEmpty(newStack);
    } else
        printf("Out Of Memory !\n");
    return newStack;
}

void MakeEmpty(Stack stc) {

    if (stc == NULL)
        printf("Out Of Space!\n");
    else
        while (!is_empty(stc))
            pop(stc);

}

boolean is_empty(Stack stc)  // check if empty
{
    return stc->next == NULL;
}

void push(Stack stc, string element)   // push to stack
{
//    printf("%s>>\n",element);
    Stack st = CreateStack();
    st->next = stc->next;
    stc->next = st;
    strcpy(st->str, element);

}

void pop(Stack stc)  // pop from the stack
{
    if (!is_empty(stc)) {

        Stack st = stc->next;
        stc->next = st->next;
        free(st);
    }
}

char *top(Stack stc) {
    if (!is_empty(stc)) {
        return (stc->next->str);
    } else
        return NULL;

}

void displayInstructions() {
    system("cls");
    printf("\n\t\t\t<< Welcome to COMP2421 Editor >>\n\n");
    printf("\t\t\tThere will be some special commands as follows:\n"
           "\t\t\t. undo: this will undo (i.e., remove) that last entered statement.\n"
           "\t\t\t2. redo: this will redo (i.e., re-add) that last removed statement.\n"
           "\t\t\t3. print: this command will print the entire stored input text.\n"
           "\t\t\t4. save: will save the text to a file called (output.txt).\n"
           "\t\t\t5. quit: will exit the program and save all results to output.txt\n\n");
    system("pause");

}

void displayAuthorInfo() {
    system("cls");
    system("COLOR 17");
    printf("\n\t\t\t \xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB-INFO-\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t\t -the file saved automatically..\n\n");
    printf("\t\t\t Details of your actions on the editor>> \n");
    printf("\t\t\t >Number of string you have saved: %d\n", numOfStrings);
    printf("\t\t\t >you have redo (%d) times , undo (%d) times ..\n", numOfRedo, numOfUndo);
    printf("\n");
    printf("\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB           COMP2421          \xDB\n");
    printf("\t\t\t\xDB           Project#2         \xDB\n");
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

int str_len(const string s) // length of string
{
    int i = 0;
    while (s[i] != '\0') {
        i++;
    }
    return i;
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

/*remove*/
void redo(Stack stc, Stack UndoStack) {

    push(stc, top(UndoStack));
    pop(UndoStack);


}

/*re-add*/
void undo(Stack stc, Stack UndoStack) {
//    printf("<<<%s>>>\n",top(stc));
    push(UndoStack, top(stc));
    pop(stc);

}

void PrintStackTopToBottom(Stack s) {

    Stack P = s;
    if (is_empty(s))
        printf("Empty Stack\n");
    else {
        printf("Result >>\n");
        if (writeToFile) {
            OutFile = fopen("output.txt", "w");
            if (OutFile == NULL) {
                printf("Error!");
                exit(1);
            }
            fprintf(OutFile, "Result >>\n");
        }
        while (top(P) != NULL) {
            P = P->next;
            if (writeToFile) {

                fprintf(OutFile, "[+] %s\n", P->str);
            } else
                printf("[+] %s\n", P->str);

        }

    }


}

void PrintStackSequence(Stack stc) {
    Stack StackToPrint = NULL;
    StackToPrint = CreateStack();
    Stack tempPntr = stc;
    if (is_empty(stc))
        printf("Empty Satck...\n");
    else {
        while (top(tempPntr) != NULL) {
            tempPntr = tempPntr->next;
            push(StackToPrint, tempPntr->str);
        }
        PrintStackTopToBottom(StackToPrint);
    }
}

void DisposeStack(Stack deleteStack) {
    MakeEmpty(deleteStack);
    free(deleteStack);
}

