#include <stdio.h>
#include <stdlib.h>
#include <string.h>


/***********************
 *      COMP133        *
 *  Assignment five    *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/


int main()
{
    FILE *in;
    int i,file,size=0,un=0,kn=0,j=0,counter=0,f,k=0;
    int const max =100;
    char dict [max][100];
    char enter [max], words [max][100];
    char known [max][100] ,unknown [max][100];

    in=fopen("dictionary.txt","r");

    if(in == NULL)
        {
           printf("Error opening file !\n\n");
            return(-1);
        }

    else
        {
          for(i=0; i<100; i++,size++)
            {

                file=fscanf(in,"%s",dict[i]);

                if(file==EOF)
                    break;
            }
        }

    printf("\n\t\t\t\t   WELCOME ");
    printf("\n\n--------------------------------------------------------------------------------\n");
    printf("Enter any sentence:\n\n");
    gets(enter);

    for(i=0;i<=(strlen(enter));i++)
    {
        if(enter[i]==' '||enter[i]=='\0')
        {
            words[counter][j]='\0';
            counter++;
            j=0;
        }
        else
        {
            words[counter][j]=enter[i];
            j++;
        }
    }

    for(i=0;i<size;i++)
        {

        for(j=0;j<counter;j++)
            {
                if(strcmp(words[j],dict[i])==0)
                    {
                        strcpy(known[kn],words[j]);
                        kn++;
                    }
            }
       }


    for (i=0;i<counter;i++)
        {
            for(j=0,f=0;j<size;j++)
                {
                    if(strcmp(words[i],dict[j])==0)
                        f++;
                }

            if(f==0)
                {
                     strcpy(unknown[k],words[i]);
                     k++;
                }
        }


    un=counter-kn;

    printf("\n\n--------------------------------------------------------------------------------\n");
    printf("\nknown words : \n\n");

    for (i=0;i<kn;i++)
        printf("%d) %s \n\n",i+1,known[i]);




    printf("--------------------------------------------------------------------------------\n");
    printf("\nunknown words : \n\n");

    for (i=0;i<un;i++)
        printf("%d) %s \n\n",i+1,unknown[i]);



    printf("--------------------------------------------------------------------------------\n");
    printf("\t\t\t\tGood Bye\n\n ");

    fclose(in);
    return 0;
}




















