#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include<string.h>
#include <ctype.h>
#include <limits.h>
#include<math.h>
/***********************
 *      COMP2421       *
 *     Project#3       *
 ***********************/

/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Section : 1         *
 ***********************/

#define MAX_STRING 100
#define MAX_STRING_LINE 500
typedef int boolean;
#define true 1
#define false 0
typedef char string[MAX_STRING];

//*******************************    Data    ********************************************
typedef struct // Declare a struct for data
{
    string PatientName;
    char Gender;
    string admissionDate;
    string birthDate;
    string Illness;
    string Address;
    string BloodType;
} Data;
//***************************************************************************************



//*******************************   AVL Tree    ******************************************
typedef struct AVLnode *AVLNode;
struct AVLnode {
    Data data;
    AVLNode Left;
    AVLNode Right;
    int Height; //Balance information
};
//***************************************************************************************



//*******************************   Hash Table   ******************************************
struct HashNode {
    Data data;
    int status; //0 ->empty ,, 1->used  ,, 2->deleted
};
//***************************************************************************************



typedef struct HashNode *HashPntr;
HashPntr *HashTable;
int HshTableSize;
int numOfPatients = 0;
boolean isFileRead = false;
FILE *outFile;

//*******************************   Functions   ******************************************
AVLNode FindMin(AVLNode);

AVLNode FindMax(AVLNode);

int Height(AVLNode);

int Max(int, int);

AVLNode SingleRotateWithLeft(AVLNode);

AVLNode SingleRotateWithRight(AVLNode);

AVLNode DoubleRotateWithLeft(AVLNode);

AVLNode DoubleRotateWithRight(AVLNode);

AVLNode Insert(AVLNode, string, char, string, string, string, string, string);

void PrintInOrder(AVLNode);

void PrintNodeInTree(AVLNode);

void PrintOutToFileAvl(AVLNode);

void PrintOutToFileHash();

void printHashTable();

int getBalance(AVLNode);

AVLNode deleteNode(AVLNode, string);

void FindByIllness(AVLNode, string);

AVLNode FindByName(string name, AVLNode T);

AVLNode readFile(AVLNode);

void stringToDate(string str);

boolean IsEmpty(AVLNode T);

void updatePatientInfo(AVLNode);

void DisposeAVL(AVLNode T);

char *stringToTrim(char *str);

void Rehashing();

int getNumOfUsedSpotsInHashTable();

int QuadraticIndex(int);

boolean isPrime(int);

int getFirstNextPrimeAfter2x(int);

int numOfAvlElements(AVLNode);

void createHashTable(AVLNode);

int stringHashing(char *s);

void insertInHashTable(string, char, string, string, string, string, string);

void fillTreeInHashTable(AVLNode);

int FindByNameInHashTable(string);

int deleteFromHashTable(string);

void DisposeAVL(AVLNode);

void searchInHashTable();

void displayAuthorInfo();

void displayAvlMenu();

void displayHashMenu();

//***************************************************************************************







//_________________________________Main Function_________________________________________
int main() {

    int choice;
    AVLNode AvlTree = NULL;  //create an empty AVL tree

    ////////////////////////////////////////////////<<AVL>>/////////////////////////////////////////////////////////////
    displayAvlMenu();
    do {
        displayAvlMenu();
        scanf("%d", &choice);
        switch (choice) {

            case 1:
                system("cls");
                AvlTree = readFile(AvlTree);
                if (isFileRead) {
                    printf("The file has been read successfully...\n");
                }
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 2:
                system("cls");
                printf("\n");
                if(isFileRead) {
                    printf("------------------------------------------------------------------------------------------------------------------------\n");
                    printf("%-15s|%-5s|%-15s|%-15s|%-10s|%-15s|%-10s\n", "  Patient Name", "  Gender ",
                           "  Date of admission ", "  Date of birth ", "    Illness  ", "   Address(City) ",
                           "   Blood type");
                    printf("------------------------------------------------------------------------------------------------------------------------\n");
                    PrintInOrder(AvlTree);
                    printf("\n");
                }
                else
                    printf("Read file first please !!\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 3:
                system("cls");
                if(isFileRead) {
                    string name, addD, brD, Il, Add, bld;
                    char g;
                    printf("enter the name of the patient :");
                    scanf("\n%[^\n]s", name);
                    printf("enter the Gender :");
                    scanf(" %c", &g);
                    printf("enter the date of admission as follow DDMMYYYY :");
                    scanf("\n%[^\n]s", addD);
                    printf("enter the date of birth as follow DDMMYYYY :");
                    scanf("\n%[^\n]s", brD);
                    printf("enter the name of illness :");
                    scanf("\n%[^\n]s", Il);
                    printf("enter the Address(City) :");
                    scanf("\n%[^\n]s", Add);
                    printf("enter the Blood type :");
                    scanf("\n%[^\n]s", bld);
                    AvlTree = Insert(AvlTree, name, g, addD, brD, Il, Add, bld);
                }
                else
                    printf("Read file first please !!\n");
                printf("\n\nPress any key to back to menu...");
                getch();


                break;

            case 4:
                system("cls");
                if(isFileRead) {
                    string nm;
                    printf("enter the name of the patient :");
                    scanf("\n%[^\n]s", nm);
                    if (FindByName(nm, AvlTree) == NULL) {
                        printf("Not Found !!\n");
                        printf("\n\nPress any key to back to menu...");
                        getch();
                        break;
                    }

                    PrintNodeInTree(FindByName(nm, AvlTree));
                }
                else
                    printf("Read file first please !!\n");
                printf("\n\nPress any key to back to menu...");
                getch();

                break;
            case 5:
                system("cls");
                if(isFileRead) {
                    string nameS;
                    printf("enter the name of the patient :");
                    scanf("\n%[^\n]s", nameS);
                    AVLNode temp = FindByName(nameS, AvlTree);
                    if (temp == NULL)
                        printf("Not Found !!\n");
                    else {
                        updatePatientInfo(temp);
                    }
                }
                else
                    printf("Read file first please !!\n");


                printf("\n\nPress any key to back to menu...");
                getch();


                break;

            case 6:
                system("cls");
                if(isFileRead) {
                    string nmd;
                    printf("enter the name of the patient :");
                    scanf("\n%[^\n]s", nmd);
                    if (FindByName(nmd, AvlTree) == NULL) {
                        printf("Not Found !!\n");
                        printf("\n\nPress any key to back to menu...");
                        getch();
                        break;
                    }
                    deleteNode(AvlTree, nmd);
                    printf("the patient deleted successfully !\n\n");
                }
                else
                    printf("Read file first please !!\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 7:
                system("cls");
                if(isFileRead) {
                    string il;
                    printf("enter the name of the Illness :");
                    scanf("\n%[^\n]s", il);
                    printf("------------------------------------------------------------------------------------------------------------------------\n");
                    printf("%-15s|%-5s|%-15s|%-15s|%-10s|%-15s|%-10s\n", "  Patient Name", "  Gender ",
                           "  Date of admission ", "  Date of birth ", "    Illness  ", "   Address(City) ",
                           "   Blood type");
                    printf("------------------------------------------------------------------------------------------------------------------------\n");
                    FindByIllness(AvlTree, il);
                }
                else
                    printf("Read file first please !!\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;

            case 8:
                system("cls");
                if(isFileRead)
                {
                   // OK GO
                }
                else {
                    printf("Read file first please !!\n");
                    return 0;
                }
                break;

            case 9:
                system("cls");
                outFile = fopen("patients_hash.data.txt", "w");
                if (outFile == NULL) {
                    printf("Error In File !! Could not open File !!\n");
                    exit(1);
                }
                PrintOutToFileAvl(AvlTree);
                displayAuthorInfo();
                DisposeAVL(AvlTree);
                return 0;


            default:
                system("cls");
                printf("Invalid choice !!\n");
                printf(">>Make sure you choose number (1 to 8) only from the available options.\n");
                printf("\n\nPress any key to back to menu and try again ...");
                getch();

                break;

        }
    } while (choice != 8);

    ////////////////////////////////////////////////<<HASHTABLE>>///////////////////////////////////////////////////////


    displayHashMenu();
    do {
        displayHashMenu();
        scanf("%d", &choice);
        switch (choice) {

            case 1:
                system("cls");
                createHashTable(AvlTree);
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 2:
                system("cls");
                printHashTable();
                printf("\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 3:
                system("cls");
                printf("\ntable size : %d\n\n", HshTableSize);
                printf("\n\nPress any key to back to menu...");
                getch();
                break;

            case 4:
                system("cls");
                printf("Hash function used : \n");
                printf("hashValue = (hashValue << 5)+ *(s++)\n");
                printf("return abs(hashValue%%tableSize)\n");
                printf("\n\nPress any key to back to menu...");
                getch();

                break;
            case 5:
                system("cls");
                string name, addD, brD, Il, Add, bld;
                char g;
                printf("enter the name of the patient :");
                scanf("\n%[^\n]s", name);
                printf("enter the Gender :");
                scanf(" %c", &g);
                printf("enter the date of admission as follow DDMMYYYY :");
                scanf("\n%[^\n]s", addD);
                printf("enter the date of birth as follow DDMMYYYY :");
                scanf("\n%[^\n]s", brD);
                printf("enter the name of illness :");
                scanf("\n%[^\n]s", Il);
                printf("enter the Address(City) :");
                scanf("\n%[^\n]s", Add);
                printf("enter the Blood type :");
                scanf("\n%[^\n]s", bld);
                insertInHashTable(name, g, addD, brD, Il, Add, bld);
                printf("\n\nPress any key to back to menu...");
                getch();


                break;

            case 6:
                system("cls");
                searchInHashTable();
                printf("\n\nPress any key to back to menu...");
                getch();
                break;


            case 7:
                system("cls");
                string nameDel;
                printf("enter the name of the patient to delete its record :");
                scanf("\n%[^\n]s", nameDel);
                if (deleteFromHashTable(nameDel) != -1)
                    printf("record has been deleted successfully!\n");
                else
                    printf("Name not found.\n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;

            case 8:
                system("cls");
                outFile = fopen("patients_hash.data.txt", "w");
                if (outFile == NULL) {
                    printf("Error In File !! Could not open File !!\n");
                    exit(1);
                }
                PrintOutToFileHash();
                displayAuthorInfo();
                free(HashTable);
                break;
            default:
                system("cls");
                printf("Invalid choice !!\n");
                printf(">>Make sure you choose number (1 to 9) only from the available options.\n");
                printf("\n\nPress any key to back to menu and try again ...");
                getch();

                break;

        }
    } while (choice != 8);


    return 0;
}

void PrintOutToFileAvl(AVLNode t) {

    if (t != NULL) {

        PrintOutToFileAvl(t->Left);
        fprintf(outFile, "%s,", t->data.PatientName);
        fprintf(outFile, "%c,", t->data.Gender);
        fprintf(outFile, "%s,", t->data.admissionDate);
        fprintf(outFile, "%s,", t->data.birthDate);
        fprintf(outFile, "%s,", t->data.Illness);
        fprintf(outFile, "%s,", t->data.Address);
        fprintf(outFile, "%s\n", t->data.BloodType);
        PrintOutToFileAvl(t->Right);
    }
}

void PrintOutToFileHash() {


    for (int index = 0; index < HshTableSize; index++) {
        fprintf(outFile, "%d] ", index);
        if (HashTable[index]->status == 2)
            fprintf(outFile, "<<<<<<<<<<<<<<<<<<<<<Deleted>>>>>>>>>>>>>>>>>>>>>>\n");

        else if (HashTable[index]->status == 0)
            fprintf(outFile, "<<<<<<<<<<<<<<<<<<<Empty Spot>>>>>>>>>>>>>>>>>>>>>>\n");

        else {
            fprintf(outFile, "%s,", HashTable[index]->data.PatientName);
            fprintf(outFile, "%c,", HashTable[index]->data.Gender);
            fprintf(outFile, "%s,", HashTable[index]->data.admissionDate);
            fprintf(outFile, "%s,", HashTable[index]->data.birthDate);
            fprintf(outFile, "%s,", HashTable[index]->data.Illness);
            fprintf(outFile, "%s,", HashTable[index]->data.Address);
            fprintf(outFile, "%s\n", HashTable[index]->data.BloodType);
        }

    }


}

void displayAvlMenu() {


    system("cls");
    system("COLOR F0");
    printf("\n\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB                   <<AVL Menu >>                        \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB 1.Read File and store patients Data.                   \xDB\n");
    printf("\t\t\t\xDB 2.Print The List of all patients Data.                 \xDB\n");
    printf("\t\t\t\xDB 3.Add a new patient with its Data.                     \xDB\n");
    printf("\t\t\t\xDB 4.Search for a patient and print its Data.             \xDB\n");
    printf("\t\t\t\xDB 5.Update a specific patient data.                      \xDB\n");
    printf("\t\t\t\xDB 6.Delete a specific patient from the system.           \xDB\n");
    printf("\t\t\t\xDB 7.Print List all patients that have the same illness.  \xDB\n");
    printf("\t\t\t\xDB 8.Implement the Hash Table.                            \xDB\n");
    printf("\t\t\t\xDB 9.Exit And Save Data in File ...                       \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t\t Enter Your Choice >>");
}

void displayHashMenu() {


    system("cls");
    system("COLOR F0");
    printf("\n\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB                << HashTable Menu >>                    \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB 1.Create Hash Table using the previous patients data.  \xDB\n");
    printf("\t\t\t\xDB 2.Print all hashed table.                              \xDB\n");
    printf("\t\t\t\xDB 3.Print out table size.                                \xDB\n");
    printf("\t\t\t\xDB 4.Print out the used hash function.                    \xDB\n");
    printf("\t\t\t\xDB 5.Add New Patient data into the hash table.            \xDB\n");
    printf("\t\t\t\xDB 6.Search for a patient and print its Data.             \xDB\n");
    printf("\t\t\t\xDB 7.Delete a specific patient from the system.           \xDB\n");
    printf("\t\t\t\xDB 8.Exit And Save HashTable back to File ...             \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t\t Enter Your Choice >>");
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
    printf("\t\t\t\xDB           Project#3         \xDB\n");
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

// this function perform a a single left rotate
AVLNode SingleRotateWithLeft(AVLNode K2) {
    AVLNode K1;

    K1 = K2->Left;
    K2->Left = K1->Right;
    K1->Right = K2;

    K2->Height = Max(Height(K2->Left), Height(K2->Right)) + 1;
    K1->Height = Max(Height(K1->Left), K2->Height) + 1;

    return K1;  /* New root */
}

// this function perform a a single right rotate
AVLNode SingleRotateWithRight(AVLNode K1) {
    AVLNode K2;

    K2 = K1->Right;
    K1->Right = K2->Left;
    K2->Left = K1;

    K1->Height = Max(Height(K1->Left), Height(K1->Right)) + 1;
    K2->Height = Max(Height(K2->Right), K1->Height) + 1;

    return K2;  /* New root */
}

// this function perform a a double left rotates
AVLNode DoubleRotateWithLeft(AVLNode K3) {
    /* Rotate between K1 and K2 */
    K3->Left = SingleRotateWithRight(K3->Left);

    /* Rotate between K3 and K2 */
    return SingleRotateWithLeft(K3);
}

// this function perform a a double right rotates
AVLNode DoubleRotateWithRight(AVLNode K1) {
    /* Rotate between K3 and K2 */
    K1->Right = SingleRotateWithLeft(K1->Right);

    /* Rotate between K1 and K2 */
    return SingleRotateWithRight(K1);
}

// this function finds the the smallest name ( the one on the far left)
AVLNode FindMin(AVLNode T) {
    if (T == NULL)
        return NULL;
    else if (T->Left == NULL)
        return T;
    else
        return FindMin(T->Left);
}

// this function finds the the biggest name ( the one on the far right)
AVLNode FindMax(AVLNode T) {
    if (T != NULL)
        while (T->Right != NULL)
            T = T->Right;

    return T;
}

// this function returns the height of a node , and -1 if its NULL
int Height(AVLNode P) {
    if (P == NULL)
        return -1;
    else
        return P->Height;
}

// this function returns the biggest number between the two entries
int Max(int Lhs, int Rhs) {
    return Lhs > Rhs ? Lhs : Rhs;
}

// this function inserts a new patient to the tree in correct position and regain the balance if needed
AVLNode Insert(AVLNode T, string PatientName, char Gender, string admissionDate, string birthDate, string Illness,
               string Address, string BloodType) {
    if (T == NULL) {
        /* Create and return a one-node tree */
        T = malloc(sizeof(struct AVLnode));
        if (T == NULL)
            printf("Out of space!!!");
        else {
            strcpy(T->data.PatientName, PatientName);
            T->data.Gender = Gender;
            strcpy(T->data.admissionDate, admissionDate);
            strcpy(T->data.birthDate, birthDate);
            strcpy(T->data.Illness, Illness);
            strcpy(T->data.Address, Address);
            strcpy(T->data.BloodType, BloodType);
            T->Height = 0;
            T->Left = T->Right = NULL;
        }
    } else if (strcasecmp(PatientName, T->data.PatientName) < 0) {
        T->Left = Insert(T->Left, PatientName, Gender, admissionDate, birthDate, Illness, Address, BloodType);
        if (abs(Height(T->Left) - Height(T->Right)) == 2)
            if (strcasecmp(PatientName, T->Left->data.PatientName) < 0)
                T = SingleRotateWithLeft(T);
            else
                T = DoubleRotateWithLeft(T);
    } else if (strcasecmp(PatientName, T->data.PatientName) > 0) {
        T->Right = Insert(T->Right, PatientName, Gender, admissionDate, birthDate, Illness, Address, BloodType);
        if (abs(Height(T->Right) - Height(T->Left)) == 2)
            if (strcasecmp(PatientName, T->Left->data.PatientName) > 0)
                T = SingleRotateWithRight(T);
            else
                T = DoubleRotateWithRight(T);
    }
    // else -> do nothing ->> is that the name is alredy exists

    T->Height = Max(Height(T->Left), Height(T->Right)) + 1;
    return T;
}

// this function prints all the data in the tree inOrder
void PrintInOrder(AVLNode t) {

    if (t != NULL) {

        PrintInOrder(t->Left);
        printf("   %-15s", t->data.PatientName);
        printf("   %c", t->data.Gender);
        printf("\t\t");
        stringToDate(t->data.admissionDate);
        printf("   \t");
        stringToDate(t->data.birthDate);
        printf("\t%s", t->data.Illness);
        printf("\t\t%s", t->data.Address);
        printf("      \t%s", t->data.BloodType);
        printf("\n");
        PrintInOrder(t->Right);
    }
}

// this function return the value of the height difference
int getBalance(AVLNode N) {
    if (N == NULL)
        return 0;
    return Height(N->Left) - Height(N->Right);
}

// this function deletes a node from the tree
AVLNode deleteNode(AVLNode root, string key) {
    // STEP 1: PERFORM STANDARD BST DELETE
    if (root == NULL)
        return root;

    // If the key to be deleted is smaller than the
    // root's key, then it lies in left subtree
    if (strcasecmp(key, root->data.PatientName) < 0)
        root->Left = deleteNode(root->Left, key);

    // If the key to be deleted is greater than the
    // root's key, then it lies in right subtree
    if (strcasecmp(key, root->data.PatientName) > 0)
        root->Right = deleteNode(root->Right, key);

        // if key is same as root's key, then This is
        // the node to be deleted

    else if (strcasecmp(key, root->data.PatientName) == 0) {
        // node with only one child or no child
        if ((root->Left == NULL) || (root->Right == NULL)) {
            AVLNode temp = root->Left ? root->Left : root->Right;

            // No child case
            if (temp == NULL) {
                temp = root;
                root = NULL;
            } else  // One child case
            {
                *root = *temp; // Copy the contents of
            }
            // the non-empty child
            free(temp);
        } else {

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            AVLNode temp = FindMin(root->Right);

            // Copy the inorder successor's data to this node
            strcpy(root->data.PatientName, temp->data.PatientName);

            // Delete the inorder successor
            root->Right = deleteNode(root->Right, temp->data.PatientName);
        }
    }
    // If the tree had only one node then return
    if (root == NULL)
        return root;

    // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
    root->Height = 1 + Max(Height(root->Left), Height(root->Right));
    // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to
    // check whether this node became unbalanced)
    int balance = getBalance(root);

    // If this node becomes unbalanced, then there are 4 cases

    // Left Left Case
    if (balance > 1 && getBalance(root->Left) >= 0)
        return SingleRotateWithRight(root);

    // Left Right Case
    if (balance > 1 && getBalance(root->Left) < 0) {
        root->Left = SingleRotateWithLeft(root->Left);
        return SingleRotateWithRight(root);
    }

    // Right Right Case
    if (balance < -1 && getBalance(root->Right) <= 0)
        return SingleRotateWithLeft(root);

    // Right Left Case
    if (balance < -1 && getBalance(root->Right) > 0) {
        root->Right = SingleRotateWithRight(root->Right);
        return SingleRotateWithLeft(root);
    }

    return root;
}

void FindByIllness(AVLNode t, string ill) {
    if (t != NULL) {
        FindByIllness(t->Left, ill);
        if (strcasecmp(ill, t->data.Illness) == 0) {
            printf("   %-15s", t->data.PatientName);
            printf("   %c", t->data.Gender);
            printf("\t\t");
            stringToDate(t->data.admissionDate);
            printf("   \t");
            stringToDate(t->data.birthDate);
            printf("\t%s", t->data.Illness);
            printf("\t\t%s", t->data.Address);
            printf("      \t%s", t->data.BloodType);
            printf("\n");
        }
        FindByIllness(t->Right, ill);
    }

}

// this function read file and fill data into avl tree
AVLNode readFile(AVLNode t) {

    FILE *inFile;
    inFile = fopen("patients.txt", "r");

    if (inFile == NULL) {
        printf("Error !!,file not exists.. \n");
        printf("-Make sure that the file name is \"patients.txt\"\n");
        printf("Try Again Later...");
        exit(-1);
    }
    string pName, gdr, admsDate, bDate, ill, addrs, Btype;
    char g;
    numOfPatients = 0;
    string strIn;
    fgets(strIn, MAX_STRING, inFile);
    do {
        sscanf(stringToTrim(strIn), "%[^#]#%[^#]#%[^#]#%[^#]#%[^#]#%[^#]#%s", pName, gdr, admsDate, bDate, ill, addrs,
               Btype);
        g = gdr[0];
        t = Insert(t, pName, g, admsDate, bDate, ill, addrs, Btype);
        numOfPatients++;
    } while (fgets(strIn, MAX_STRING_LINE, inFile) != NULL);

    isFileRead = true;
    fclose(inFile);
    return t;

}

char *stringToTrim(char *str) {
    while (isspace((unsigned char) *str)) {
        str++;
    }
    if (*str == 0)
        return str;

    char *last;
    last = str + strlen(str) - 1;
    while ((last > str) && (isspace((unsigned char) *last))) {
        last--;
    }

    last[1] = '\0';

    /*return new trimmed string */
    return str;
}

boolean IsEmpty(AVLNode T) {
    if (T == NULL)
        return true;
    return false;
}

void DisposeAVL(AVLNode T) {
    if (!IsEmpty(T)) {
        DisposeAVL(T->Left);
        free(T);
        DisposeAVL(T->Right);
    }
}

//this function to print the date in string
void stringToDate(string str) {

    char day[3];
    char month[10];
    char year[10];
    strncpy(day, str, 2);
    strcpy(month, &str[2]);
    strcpy(year, &str[4]);
    printf("%s-", day);
    printf("%.*s-", 2, month);
    printf("%s", year);

}

// this function searches for a patient name  in the tree.
AVLNode FindByName(string name, AVLNode T) {
    if (T == NULL)
        return NULL; // -> empty tree
    if (strcasecmp(name, T->data.PatientName) < 0)
        return FindByName(name, T->Left);
    else if (strcasecmp(name, T->data.PatientName) > 0)
        return FindByName(name, T->Right);
    else if (strcasecmp(name, T->data.PatientName) == 0)
        return T; // -> found !
    else
        return NULL; // -> not found !
}

void PrintNodeInTree(AVLNode t) {

    if (!IsEmpty(t)) {
        printf("------------------------------------------------------------------------------------------------------------------------\n");
        printf("%-15s|%-5s|%-15s|%-15s|%-10s|%-15s|%-10s\n", "  Patient Name", "  Gender ",
               "  Date of admission ", "  Date of birth ", "    Illness  ", "   Address(City) ",
               "   Blood type");
        printf("------------------------------------------------------------------------------------------------------------------------\n");
        printf("   %-15s", t->data.PatientName);
        printf("   %c", t->data.Gender);
        printf("\t\t");
        stringToDate(t->data.admissionDate);
        printf("   \t");
        stringToDate(t->data.birthDate);
        printf("\t%s", t->data.Illness);
        printf("\t\t%s", t->data.Address);
        printf("      \t%s", t->data.BloodType);
        printf("\n");
    } else
        printf("Empty Node !! \n\n");

}

void updatePatientInfo(AVLNode t) {

    string ad, br, ill, adr, bd;
    char gn;
    printf("enter the new info >>\n");
    printf("enter the Gender :");
    scanf(" %c", &gn);
    printf("enter the date of admission as follow DDMMYYYY :");
    scanf("\n%[^\n]s", ad);
    printf("enter the date of birth as follow DDMMYYYY :");
    scanf("\n%[^\n]s", br);
    printf("enter the name of illness :");
    scanf("\n%[^\n]s", ill);
    printf("enter the Address(City) :");
    scanf("\n%[^\n]s", adr);
    printf("enter the Blood type :");
    scanf("\n%[^\n]s", bd);
    strcpy(t->data.Illness, ill);
    strcpy(t->data.Address, adr);
    strcpy(t->data.birthDate, br);
    strcpy(t->data.BloodType, bd);
    strcpy(t->data.admissionDate, ad);
    t->data.Gender = gn;

}

void Rehashing() {

    if (HshTableSize != 0) {
        int numOfEntries = 0;
        int temp = HshTableSize;
        HashPntr *newTable = (HashPntr *) malloc(sizeof(HashPntr) * (temp));
        for (int i = 0; i < temp; i++) {
            newTable[i] = malloc(sizeof(struct HashNode));
            if (HashTable[i]->status == 1) {
                numOfEntries++;
                strcpy(newTable[i]->data.PatientName, HashTable[i]->data.PatientName);
                strcpy(newTable[i]->data.admissionDate, HashTable[i]->data.admissionDate);
                strcpy(newTable[i]->data.birthDate, HashTable[i]->data.birthDate);
                strcpy(newTable[i]->data.Illness, HashTable[i]->data.Illness);
                strcpy(newTable[i]->data.Address, HashTable[i]->data.Address);
                strcpy(newTable[i]->data.BloodType, HashTable[i]->data.BloodType);
                newTable[i]->data.Gender = HashTable[i]->data.Gender;
                newTable[i]->status = 1;
            } else
                newTable[i]->status = 0;
            free(HashTable[i]);
        }
        if (numOfEntries > 0) {
            HshTableSize = getFirstNextPrimeAfter2x(numOfEntries);
            HashTable = malloc(sizeof(HashPntr) * (HshTableSize));

            for (int i = 0; i < HshTableSize; i++) {
                HashTable[i] = (HashPntr) malloc(sizeof(struct HashNode));
                HashTable[i]->status = 0;
            }
        }
        for (int i = 0; i < temp; i++) {
            if (newTable[i]->status == 1) {
                insertInHashTable(newTable[i]->data.PatientName, newTable[i]->data.Gender,
                                  newTable[i]->data.admissionDate, newTable[i]->data.birthDate,
                                  newTable[i]->data.Illness, newTable[i]->data.Address, newTable[i]->data.BloodType);

                free(newTable[i]);
            }
        }
    }
}

int getNumOfUsedSpotsInHashTable() {
    int counter = 0;
    for (int i = 0; i < HshTableSize; i++)
        if (HashTable[i]->status == 1)
            counter++;
    return counter;
}

//This function returns the next empty index based on Open Addressing->Quadratic Hashing
int QuadraticIndex(int index) {
    if (HashTable[index % HshTableSize]->status == 0)
        return index;
    int i = 1;
    int nextIndex = (index + i * i) % HshTableSize;
    while (HashTable[nextIndex]->status == 1) {
        i++;
        nextIndex = (index + i * i) % HshTableSize;
    }
    return nextIndex;
}

boolean isPrime(int x) {

    for (int i = 3; i * i <= x; i += 2) {
        if (x % i == 0)
            return false;
    }
    return true;
}

// This function returns the first prime after a specific number.
int getFirstNextPrimeAfter2x(int x) {

    for (int i = 2 * x;; i++) {
        if (isPrime(i))
            return i;
    }
}

int numOfAvlElements(AVLNode T) {
    int counter = 1;
    if (T == NULL)
        return 0;
    else {
        if (T->Right != NULL)
            counter += numOfAvlElements(T->Right);
        if (T->Left != NULL)
            counter += numOfAvlElements(T->Left);
    }
    return counter;
}

void createHashTable(AVLNode T) {
    if (numOfAvlElements(T) > 0) {
        HshTableSize = getFirstNextPrimeAfter2x(numOfAvlElements(T));
        HashTable = (HashPntr *) malloc((HshTableSize) * sizeof(struct HashNode));
        int i;
        for (i = 0; i < HshTableSize; i++) {
            HashTable[i] = (HashPntr) malloc(sizeof(struct HashNode));
            HashTable[i]->status = 0;
        }
        fillTreeInHashTable(T);
        printf("Table Created Successfully!\n");
    }

}

//This function returns hash value for a String.
int stringHashing(char *s) {
    long hashValue = 0;
    while (*s != '\0')
        hashValue = (hashValue << 5) + *(s++);
    return abs(hashValue % HshTableSize);
}

void insertInHashTable(string PatientName, char Gender, string admissionDate, string birthDate, string Illness,
                       string Address, string BloodType) {
    int quadIndex = QuadraticIndex(stringHashing(PatientName));
    strcpy(HashTable[quadIndex]->data.PatientName, PatientName);
    strcpy(HashTable[quadIndex]->data.admissionDate, admissionDate);
    strcpy(HashTable[quadIndex]->data.birthDate, birthDate);
    strcpy(HashTable[quadIndex]->data.Illness, Illness);
    strcpy(HashTable[quadIndex]->data.Address, Address);
    strcpy(HashTable[quadIndex]->data.BloodType, BloodType);
    HashTable[quadIndex]->data.Gender = Gender;

    HashTable[quadIndex]->status = 1;
    int numOfUsedSpots = getNumOfUsedSpotsInHashTable();
    if (numOfUsedSpots > HshTableSize / 2)
        Rehashing();
}

void fillTreeInHashTable(AVLNode T) {
    if (T != NULL) {
        fillTreeInHashTable(T->Left);
        insertInHashTable(T->data.PatientName, T->data.Gender, T->data.admissionDate, T->data.birthDate,
                          T->data.Illness, T->data.Address, T->data.BloodType);
        fillTreeInHashTable(T->Right);
    }
}

int FindByNameInHashTable(string name) {
    int index = stringHashing(name);
    int i = 0;
    int newIndex = (index + i * i) % HshTableSize;
    while (strcasecmp(name, HashTable[newIndex]->data.PatientName) != 0) {
        i++;
        newIndex = (index + i * i) % HshTableSize;
        if (HashTable[newIndex]->status == 0)
            return -1; // not found -> empty
    }
    if (HashTable[newIndex]->status == 1)
        return newIndex;
    else
        return -1; //not found
}

int deleteFromHashTable(string name) {
    int key = FindByNameInHashTable(name);
    if (key != -1)
        HashTable[key]->status = 2;

    return key;
}

void searchInHashTable() {
    string name;
    printf("Enter the patient name to search fot it : \n");
    scanf("\n%[^\n]s", name);
    int index = FindByNameInHashTable(name);
    if (index == -1)
        printf("Name Not Found!\n");
    else {
        printf("------------------------------------------------------------------------------------------------------------------------\n");
        printf("%-15s|%-5s|%-15s|%-15s|%-10s|%-15s|%-10s\n", "  Patient Name", "  Gender ",
               "  Date of admission ", "  Date of birth ", "    Illness  ", "   Address(City) ",
               "   Blood type");
        printf("------------------------------------------------------------------------------------------------------------------------\n");

        printf("   %-15s", HashTable[index]->data.PatientName);
        printf("   %c", HashTable[index]->data.Gender);
        printf("\t\t");
        stringToDate(HashTable[index]->data.admissionDate);
        printf("   \t");
        stringToDate(HashTable[index]->data.birthDate);
        printf("\t%s", HashTable[index]->data.Illness);
        printf("\t\t%s", HashTable[index]->data.Address);
        printf("      \t%s", HashTable[index]->data.BloodType);
        printf("\n");
    }

}

void printHashTable() {

    printf("\n");
    printf("------------------------------------------------------------------------------------------------------------------------\n");
    printf("%-15s|%-5s|%-15s|%-15s|%-10s|%-15s|%-10s\n", "  Patient Name", "  Gender ",
           "  Date of admission ", "  Date of birth ", "    Illness  ", "   Address(City) ",
           "   Blood type");
    printf("------------------------------------------------------------------------------------------------------------------------\n");

    for (int index = 0; index < HshTableSize; index++) {


        if (HashTable[index]->status == 2)
            printf("\t\t<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Deleted>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");

        else if (HashTable[index]->status == 0)
            printf("\t\t<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Empty Spot>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");

        else {
            printf("   %-15s", HashTable[index]->data.PatientName);
            printf("   %c", HashTable[index]->data.Gender);
            printf("\t\t");
            stringToDate(HashTable[index]->data.admissionDate);
            printf("   \t");
            stringToDate(HashTable[index]->data.birthDate);
            printf("\t%s", HashTable[index]->data.Illness);
            printf("\t\t%s", HashTable[index]->data.Address);
            printf("      \t%s", HashTable[index]->data.BloodType);
            printf("\n");
        }

    }
    printf("--------------------------------------------------------------------------------------------------------------\n");
}


