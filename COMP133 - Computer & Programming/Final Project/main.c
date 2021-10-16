#include <stdio.h>
#include <stdlib.h>

/***********************
 *      COMP133        *
 * Project Phase three *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/



int size=0;
int const MAXSIZE=100;
int const NAMESIZE=50;
void displayMainMenu();
void addBook(char names[][NAMESIZE], int bins[], double  prices[],int status[]);
void removeBook(char names[][NAMESIZE], int bins[], double  prices[],int status[]);
void searchForBook(char names[][NAMESIZE], int bins[], double  prices[],int status[]);
void printBooks (char names[][NAMESIZE], int bins[], double prices[], int  status[]);
void uploadDataFile(char names[][NAMESIZE], int bins[], double  prices[],int status[]);
void updateDataFile(char names[][NAMESIZE], int bins[], double  prices[],int status[]);


int main()
{
    int choice;
    int bins[MAXSIZE] ;
    double prices[MAXSIZE];
    int status[MAXSIZE];
    char names[MAXSIZE][NAMESIZE];

    printf("\n\t\t   welcome to BookStore Management System.");
    printf("\n\n--------------------------------------------------------------------------------\n");
    uploadDataFile(names,bins,prices,status);
    displayMainMenu();
    scanf("%d",&choice);
    while (choice!= 5)
    {
        switch (choice)
        {
           case 1: addBook(names,bins,prices,status); break;
           case 2: removeBook(names,bins,prices,status); break;
           case 3: searchForBook(names,bins,prices,status); break;
           case 4: printBooks(names,bins,prices,status); break;
           default: printf("No such Operation! Please try again.\n");
        }
      displayMainMenu();
      scanf("%d",&choice);
    }

    updateDataFile(names,bins,prices,status);
    printf("\t\tThank you for using My BookStore Management System.\n\n\t\t\t\t  GoodBye\n\n");

    return 0;
}

void displayMainMenu()
{
    printf("\nPlease Select an Operation (1-5):\n\n");
    printf("1- Add a book\n");
    printf ("2- Remove a Book\n");
    printf ("3- Search for a Book\n");
    printf ("4- Print Book List  \n");
    printf ("5- Exit System\n");

}
void uploadDataFile(char names[][NAMESIZE],int bins[], double  prices[],int status[])
{
    FILE *in;
    int infile,i;
    in=fopen("books.txt","r");

    if(in == NULL)
        {
           printf("Error opening file !\n\n");
            exit(1);
        }

    for(i=0;!feof(in);i++,size++){

        if(size==MAXSIZE)
            break;
        fscanf(in,"%s%d%lf",names[i],&bins[i],&prices[i]);
        status[i]=1;



    }

    fclose(in);
    printf("Uploading Data File ...\n");
    printf("Data File uploaded\n ");
}

void addBook(char names[][NAMESIZE],int bins[], double  prices[],int status[])
{
    int id;
    int exists=0;
    if (size<MAXSIZE){

       printf("Enter the bin number of the book : \n");
       scanf("%d",&id);
      for (int i=0;i<size;i++)
        {

            if(bins[i]==id&&status[i]==1)
                {
                    printf("Book already exists !\n");
                    exists=1;
                    break;
                }
            if (bins[i]==id&&status[i]==0)
                {
                    status[i]=1;
                    exists=1;
                    printf("Book info has been updated.\n");
                    break;
                }

       }

       if(exists==0)
        {
            bins[size]=id;
            printf("Enter the price of the book: \n");
            scanf("%lf",&prices[size]);
            printf("Ente the name of the book : \n");
            scanf("%s",&names[size]);
            status[size]=1;
            size++;
            printf("Book info has been added.\n\n");
       }


    }
    else
        printf("sorry,no space to add a new book.\n");


}
void removeBook(char names[][NAMESIZE],int bins[], double  prices[],int status[]){

    int id,exists=0;
    if(size>0){

      printf("Enter the book bin to remove it : \n");
      scanf("%d",&id);
      for(int i=0;i<size;i++)
          {

            if(bins[i]==id&&status[i]==0){

              printf("Book info with bin %d has been removed previously !\n",id);
              exists=1;
              break;

            }



            if(bins[i]==id&&status[i]==1)
                {
                    status[i]=0;
                    printf("Book info with bin %d has been removed \n",id);
                    exists=1;
                    break;
                }


          }
       if(exists==0)
            printf("this book bin not exists!\n");




    }
    else
        printf("no books to remove,the list is empty !\n");


}
void searchForBook(char names[][NAMESIZE],int bins[], double  prices[],int status[]){

    int id,exists=0;
    if(size>0){

      printf("Enter the book bin to search for it : \n");
      scanf("%d",&id);
      for(int i=0;i<size;i++)
          {

            if(bins[i]==id&&status[i]==1)
                {
                    printf("\nBook info :\n\n");
                    printf("name : %s \t bin : %d \t  price : %.2lf\n",names[i],bins[i], prices[i]);
                    printf("\nBook info has been searched For\n\n");
                    exists=1;
                    break;
                }


          }
       if(exists==0)
            printf("this book bin not exists!\n");




    }
    else
        printf("no books to remove,the list is empty !\n");


}

void updateDataFile(char names[][NAMESIZE],int bins[], double  prices[],int status[]){

  printf("\nUpdating Data File ...\n");

  FILE *out;
  out=fopen("books.txt","w");
  for(int i=0;i<size;i++)
    {

        if(status[i]==1)
            fprintf(out,"%s %d %.2lf\n",names[i],bins[i],prices[i]);
        if(i==MAXSIZE)
            break;


    }

  fclose(out);

  printf("Data File updated \n\n");
  printf("\n\n--------------------------------------------------------------------------------\n");


}

void printBooks (char names[][NAMESIZE],int bins[], double prices[], int  status[]){

    int i;

    if(size!=0)
        {
    printf("\nAll Books Info : \n\n");
    printf(" names:\t\t\t         bins:\t  \t   prices:\n");
    printf("---------\t\t       ---------\t  ----------\n\n");
    for(i=0;i<size;i++)
        {
            if(status[i]==1)
                printf(" %s\t\t\t  %d\t\t   %.2lf\n\n",names[i],bins[i],prices[i]);
            if(i==MAXSIZE)
                break;
        }

    }

    else
        printf("the list is empty! \n");
}








