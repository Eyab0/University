#include <stdio.h>
#include <stdlib.h>


/***********************
 *      COMP133        *
 *  Assignment four    *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/




int sum_of_divs(int n);
void  max_sum_and_pos(int num[],int size,int *posi,int *max_num);


int main()
{
    int num[100], max , *max_num , in_file , size , posi ;
    size = 0 ;

    FILE *in = fopen("nums.txt","r");
    in_file =fscanf(in,"%d",&num[size]);
    while( in_file!= EOF ) // keep looping until reach the least value
    {
         size++; // get number of values
         in_file=fscanf(in,"%d",&num[size]); // fill the values in array
    }
    max_sum_and_pos(num,size,&posi,&max_num);
    printf("\nThe number with max sum of divisors is (%d)  at position (%d) in the array. \n",max_num,posi);

    return 0;
}

int sum_of_divs(int n)
{
    int j,sum_divs=0;
    for(j=1;j<=n;j++) // keep looping from 1 until reach the entered number
    {
        if(n%j==0)
           sum_divs+=j; // get summation of numbers that it mod to the entered number equal zero
    }
    return sum_divs;
}

void max_sum_and_pos(int num[],int size,int *posi,int *max_num)
{
    int j, max = 0;
    for( j = 0; j < size ; j++ ) // keep looping until reach the size of  the array
    {
        if( sum_of_divs(num[j]) > max ) // to check the number has the maximum sum of divisors
        {
            max = sum_of_divs(num[j]) ; // get the maximum sum of divisors
           *posi = j; // get the position of the max number in the array
           *max_num = num[j] ; // get the number that has the maximum sum of divisors
        }

    }

}





