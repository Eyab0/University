#include <stdio.h>
#include <stdlib.h>

/***********************
 *      COMP133        *
 *  Assignment Three   *
 ***********************/


/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/


double power (double x, int n){
   double result=1;
   int d=1;
   while (d<=n) // keep looping n times
   {
       result*=x; // Multiply number x by himself n times
       d++;       //// increment d(the number of looping cycle)  by 1 to reach n times
   }


   return result;

}

int factorial (int n){

    int result=1 ;

    for (int k=2;k<=n;k++) // keep looping from number 1 to entered number
    {
        result*=k; // Multiply number 1 by the number 2 and 3 and so on until the entered number is reached
    }

    return result ;

}

double compute_ex  (double x){

    double term=0,result=0;
    int n=0;

    do{

    result+=term;
    term = ( power(x,n) / factorial(n) ); // Equation for the term = (x^n /n!)
    n ++; // increment n by 1

    }while(term>=0.0001); // stop when term is less than 0.0001


    return result;

}

int main()
{

    double x,result;
    printf("\n\t  Welcome to this program that compute the value of \n\tthe mathematical constant e to the power of x = (ex).\n\n");
    printf("Please Enter a value of x \n");
    scanf("%lf",&x);
    result = compute_ex(x);
    printf("e to the power %.2lf = %.2lf\n",x,result);



    return 0;
}
