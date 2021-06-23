#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <string.h>
#include <ctype.h>
#include <limits.h>
#include<math.h>
/***********************
 *      COMP2421       *
 *     Project#5       *
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

//========================================   Struct   ==========================================

typedef struct {
    string cityName;
    int Distance;
} Graph;

//========================================   Global Initializing   ==========================================

FILE *inFile, *outFile;
int MAX_ID = 0; // Vertices
int inputId[5000]; // to save all ides from the file to use it in validation
int citiesId[5000]; // to save id of shortest path to use it to print cities names
int numOfCities;
boolean isFileRead = false;
int numOfLines = 0;


//========================================   Functions   ==========================================

char *stringToTrim(char *str); // trim the string and remove spaces

void displayMenu();     // show UI menu to user

void displayAuthorInfo(); // show the info. about the author and project

void BuildGraphFromFile(Graph **); // build matrix graph and load data to it and connect edges

int getMaxIdFromFile();   // get the maximum id from the file to set it as size of 2D array of graph

boolean isValidId(
        int);  //  check if the input id is exist in ides in the file & check if the user input -1 as id to exit program

void setShortestPath(int[], int[], int); // set the shortest path between 2 id & and fill the the cities index

void DijkstraAlgorithm(Graph **, int, int); // Dijkstra Algorithm to get the shortest path between two id and print it

void DisposeGraph(Graph **); //Free memory associated With Graph 2D Array

//=================================================================================================



/***************************************************************************
 *                              Note:                                      *
 *                                                                         *
 * i didn't use priority queue (heaps) because there is no enough time.     *
 * so instead of that i use loop to get the min distance not visited yet.  *
 * and implementing graph using matrix method.                             *
 * and my implementing of Dijkstra is greedy Algorithm                     *
 * i know my program is worst Time complexity                              *
 * but still my work is correct !!                                         *
 *                                                                         *
 *                      I hope You like it !                               *
 ***************************************************************************/






/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                    Main Function
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
int main() {


    MAX_ID = getMaxIdFromFile() + 1; // get the max id from file ant the size(Vertices) of the 2d array of graph
    Graph **matrixGraph = (Graph **) malloc(MAX_ID * sizeof(Graph *)); // matrix graph Initializing
    for (int i = 0; i < MAX_ID; ++i) {
        matrixGraph[i] = (Graph *) malloc(MAX_ID * sizeof(Graph));
    }

    int fromSource, toDestination;
    int choice;
    do {
        system("cls");
        displayMenu();
        scanf("%d", &choice);
        printf("\n");
        switch (choice) {
            case 1:
                system("cls");
                //check if the file is readied already
                if (!isFileRead) {
                    BuildGraphFromFile(matrixGraph);
                    printf("Data File Loaded Successfully..\n");
                    printf("Graph Has Been Build Successfully..\n\n");
                } else
                    printf("You Already Read The File !! \n");
                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 2:
                system("cls");
                // check if the file readied
                if (isFileRead) {
                    printf("Please Enter Your Start ID : ");
                    scanf("%d", &fromSource);
                    printf("Please Enter Your Destination ID :");
                    scanf("%d", &toDestination);
                    // check if the id is valid
                    if (isValidId(fromSource) && isValidId(toDestination)) {

                        if (fromSource == toDestination)
                            printf("You Are Here !! \nDon't Move !!\n");
                        else
                            DijkstraAlgorithm(matrixGraph, fromSource, toDestination);
                    } else {
                        printf("Invalid ID !! \n");
                        printf("This ID is Not Exist In \"segments.txt\" File !!\n");
                    }
                } else
                    printf("Please Read The File First ! \n");

                printf("\n\nPress any key to back to menu...");
                getch();
                break;
            case 3:
                DisposeGraph(matrixGraph); // free memory
                displayAuthorInfo(); // show info. about author
                return 0;

            default:
                system("cls");
                printf("Invalid choice !!\n");
                printf(">>Make sure you choose number (1 to 4) only from the available options.\n");
                printf("\n\nPress any key to back to menu and try again ...");
                getch();
                break;
        }
    } while (choice != 3);


    return 0;
}





//===================================   Implementation Of Functions  =======================================//

// Free memory associated With Graph 2D Array
void DisposeGraph(Graph **graph) {
    for (int index = 0; index < MAX_ID; ++index)
        free(graph[index]);
    free(graph);
}

void setShortestPath(int cityID[5000], int shortestPath[MAX_ID], int toDestination) {
    if (shortestPath[toDestination] == INT_MAX) return; // reach the source road
    setShortestPath(cityID, shortestPath,
                    shortestPath[toDestination]); //recursion call of function to get the parent of  index
    cityID[numOfCities++] = toDestination; // put cities id in the array to get the name of them
}

void DijkstraAlgorithm(Graph **graph, int fromSource, int toDestination) {


    outFile = fopen("route.txt", "a"); // to sva the route info. in it
    numOfCities = 0;
    int MIN_DES; // minimum distance
    int MIN_PATH = 0; //index of the shortest Distance
    int shortestDistance[MAX_ID];
    int shortestPath[MAX_ID];
    boolean isVisited[MAX_ID]; // to check if a point is visited ot not
    memset(citiesId, 0, strlen((char *) citiesId)); // fill citiesId with a 0 value
    memset(shortestPath, 0, MAX_ID * sizeof(shortestPath[0])); // fill shortestPath with a 0 value
    memset(isVisited, false, MAX_ID * sizeof(isVisited[0])); // fill isVisited with a FALSE value
    for (int index = 0; index < MAX_ID; index++) {
        shortestDistance[index] = INT_MAX; //fill shortestDistance with a INT_MAX value  to use it to check if three is road or not
    }
    shortestDistance[fromSource] = 0;
    shortestPath[fromSource] = INT_MAX; // use it check if ot reach the source or not

    // to get the minimum distance vertex not visited
    for (int index = 0; index < MAX_ID; ++index) {
        MIN_DES = INT_MAX;
        for (int id = 0; id < MAX_ID; ++id) {
            if (!isVisited[id] && (shortestDistance[id] <= MIN_DES)) {
                MIN_DES = shortestDistance[id];
                MIN_PATH = id;
            }
        }
//    debugging
//    printf("++%d++\n",MIN_PATH);

        isVisited[MIN_PATH] = true;
        for (int Id = 0; Id < MAX_ID; Id++) {

            // check if the distance isn't zero & is visited & shortest distance isn't equal to INT_MAX
            // there is an edge from MIN_PATH to Id ( all Vertices)
            // and total distance of path from source to destination is smaller of value of distance of all Vertices

            if (graph[MIN_PATH][Id].Distance && !isVisited[Id] && shortestDistance[MIN_PATH] != INT_MAX
                && shortestDistance[MIN_PATH] + graph[MIN_PATH][Id].Distance < shortestDistance[Id]) {
                // update the distance
                shortestPath[Id] = MIN_PATH;
                shortestDistance[Id] = shortestDistance[MIN_PATH] + graph[MIN_PATH][Id].Distance;
            }
            if (MIN_PATH == toDestination) break;

        }
    }

//    printf("\n<<%d>>\n", shortestDistance[toDestination]);
//    printf("++%d++\n", numOfCities);

    if (shortestDistance[toDestination] == INT_MAX) {
        printf("Sorry , There is No Possible Path Between %d -> %d ..\n ", fromSource, toDestination);
        return;
    }

    // to set the id of cities to print them
    citiesId[numOfCities++] = fromSource;
    setShortestPath(citiesId, shortestPath, toDestination);

    boolean toFile = false;

    /*print the shortest path */
    /*to the screen and to file */
    PrintToFile:
    if (!toFile)
        printf("\n============================================================================================\n");
    if (toFile)
        fprintf(outFile,
                "\n============================================================================================\n");
    if (!toFile)
        printf("The Cities Route of the Shortest Path Between %d %c %d :\n\n", fromSource, 26, toDestination);
    if (toFile)
        fprintf(outFile, "The Cities Route of the Shortest Path Between %d -> %d :\n", fromSource, toDestination);
    for (int index = 0; index < numOfCities - 1; ++index) {
        if (index + 1 == (numOfCities - 1)) {
            if (!toFile)
                printf("\"%s\"", graph[citiesId[index]][citiesId[index + 1]].cityName);
            if (toFile)
                fprintf(outFile, "\"%s\"", graph[citiesId[index]][citiesId[index + 1]].cityName);
        } else {
            if (!toFile)
                printf("\" %s\" %c  ", graph[citiesId[index]][citiesId[index + 1]].cityName, 26);
            if (toFile)
                fprintf(outFile, "\" %s\" ->  ", graph[citiesId[index]][citiesId[index + 1]].cityName);

        }
        if (index % 5 == 0 && index != 0) {
            if (!toFile)
                printf("\n");
            if (toFile)
                fprintf(outFile, "\n");

        }
    }
    if (!toFile)
        printf("\n============================================================================================\n");
    if (toFile)
        fprintf(outFile,
                "\n============================================================================================\n");
    if (!toFile)
        printf("The [ID1][ID2] Route of the Shortest Path Between %d %c %d :\n\n", fromSource, 26, toDestination);
    if (toFile)
        fprintf(outFile, "The [ID1][ID2] Route of the Shortest Path Between %d -> %d :\n", fromSource, toDestination);
    for (int index = 0; index < numOfCities - 1; ++index) {
        if (index + 1 == (numOfCities - 1)) {
            if (!toFile)
                printf(" [%d][%d]", citiesId[index], citiesId[index + 1]);
            if (toFile)
                fprintf(outFile, " [%d][%d]", citiesId[index], citiesId[index + 1]);
        } else {
            if (!toFile)
                printf("[%d][%d]  %c ", citiesId[index], citiesId[index + 1], 26);
            if (toFile)
                fprintf(outFile, "[%d][%d]  -> ", citiesId[index], citiesId[index + 1]);
        }
        if (index % 5 == 0 && index != 0) {
            if (!toFile)
                printf("\n");
            if (toFile)
                fprintf(outFile, "\n");
        }
    }
    if (!toFile) {
        printf("\n============================================================================================\n");
        printf("\nThe Distance : %d KM\n", shortestDistance[toDestination]);
        printf("\n");
    } else {
        fprintf(outFile,
                "\n============================================================================================\n");
        fprintf(outFile, "\nThe Distance : %d KM\n", shortestDistance[toDestination]);
        fprintf(outFile, "\n");
    }
    if (!toFile)
        printf("\n============================================================================================\n");
    if (toFile)
        fprintf(outFile,
                "\n============================================================================================\n");

    int choice;
    if (toFile)
        printf("\nShortest Path has been written to \"route.txt\" File \n");
    if (!toFile) {
        printf("Do you want to print this Path info. to \"route.txt\" File ?\n");
        printf("1)Yes\n");
        printf("2)No\n");
        printf("Enter your choice number >>");
        scanf("%d", &choice);
        if (choice == 1) {
            toFile = true;
            goto PrintToFile;
        }
    }
}

boolean isValidId(int id) {

    if (id == -1) {
        displayAuthorInfo();
        exit(0);
    }
    for (int index = 0; index < numOfLines * 2; ++index) {

        if (inputId[index] == id)
            return true;
    }

    return false;
}

int getMaxIdFromFile() {


    int MaxID = INT_MIN;
    inFile = fopen("segments.txt", "r");
    if (inFile == NULL) {
        printf("Error !!,file not exists.. \n");
        printf("-Make sure that the file name is \"segments.txt\"\n");
        printf("Try Again...");
        exit(-1);
    }
    string strIn;
    string CityName;
    int ID1, ID2, Distance, index = 0;
    fgets(strIn, MAX_STRING, inFile);
    do {


//       printf("<<%s>>\n",stringToTrim(strIn));
        sscanf(stringToTrim(strIn), "%d %d %[A-Za-z- ()?] %d", &ID1, &ID2, CityName, &Distance);
        inputId[index] = ID1;
        inputId[index + 1] = ID2;
//      printf("id : %d\t\t\tid : %d\t\tname: %s\t\t\tdes: %d\n",ID1, ID2, CityName, Distance);
        if (ID1 > MaxID)
            MaxID = ID1;
        if (ID2 > MaxID)
            MaxID = ID2;
        index += 2;
        numOfLines++;

    } while (fgets(strIn, MAX_STRING, inFile) != NULL);


//    printf("%d\n\n", MaxID);
    rewind(inFile); // to rad file again
    return MaxID;
}

void BuildGraphFromFile(Graph **graph) {

    for (int index1 = 0; index1 < MAX_ID; ++index1) {
        for (int index2 = 0; index2 < MAX_ID; ++index2) {
            graph[index1][index2].Distance = 0; // fill all graph distance  with a 0 value
            strcpy(graph[index1][index2].cityName, "notExist "); // fill all graph city name with a "not exist " value

        }

    }

    string strIn, CityName;
    int ID1, ID2, Distance;
    fgets(strIn, MAX_STRING_LINE, inFile);
    do {


//      printf("<<%s>>\n",stringToTrim(strIn));
        sscanf(stringToTrim(strIn), "%d %d %[A-Za-z- ()?] %d", &ID1, &ID2, CityName, &Distance);
//      printf("id : %d\t\t\tid : %d\t\tname: %s\t\t\tdes: %d\n",ID1, ID2, CityName, Distance);
        // build the graph matrix
        strcpy(graph[ID1][ID2].cityName, CityName);
        strcpy(graph[ID2][ID1].cityName, CityName);
        graph[ID1][ID2].Distance = Distance;
        graph[ID2][ID1].Distance = Distance;


    } while (fgets(strIn, MAX_STRING_LINE, inFile) != NULL);
/*    for (int i = 0; i < MAX_ID; ++i) {

        printf("{");
        for (int j = 0; j < MAX_ID; ++j) {
            printf("%d,",graph[i][j].Distance);

        }
        printf("},");
        printf("\n");
    }*/

    isFileRead = true;
    fclose(inFile);

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

void displayMenu() {


    system("cls");
    system("COLOR F0");
    printf("\n\n\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB               << Shortest Route Finder >>              \xDB\n");
    printf("\t\t\t\xDB                                                        \xDB\n");
    printf("\t\t\t\xDB 1.Load Data From \"segments.txt\" File & Build Graph.    \xDB\n");
    printf("\t\t\t\xDB 2.Compute The Shortest Path Between pair ID.           \xDB\n");
    printf("\t\t\t\xDB 3.Exit.                                                \xDB\n");
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
    printf("\t\t\t\xDB          Project#5          \xDB\n");
    printf("\t\t\t\xDB                             \xDB\n");
    printf("\t\t\t\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\xDB\xDB");
    printf("\xDB\xDB\xDB\xDB\xDB\n");
    printf("\n\t\t     <!---- Hope You Like My Project ----!>\n");
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


/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                    END
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **/
