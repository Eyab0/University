#include <stdio.h>
#include <stdlib.h>

/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/



float calculate_Price (int,int,double,float,double);
void print_Receipt  (int,int,double);

int main()
{
    int PizaaSize,ToppingNum;
    float ToppingPrice;
    double PizaaPrice,TotalPrice;

    printf("Enter the size of the pizza  (1=large, 2=medium or 3=small).\n");
    scanf("%d",&PizaaSize);

    printf(" Enter the number of toppings on the  pizza : \n");
    scanf("%d",&ToppingNum);

    FILE *prices = fopen("prices.txt","r");
    fscanf(prices,"%lf %f",&PizaaPrice,&ToppingPrice);

    TotalPrice = calculate_Price ( PizaaSize, ToppingNum,PizaaPrice,ToppingPrice,TotalPrice);
    print_Receipt(PizaaSize, ToppingNum ,TotalPrice);


    return 0;
}

 float calculate_Price (int PizaaSize,int ToppingNum,double PizaaPrice,float ToppingPrice , double TotalPrice )
{
    TotalPrice = (( PizaaPrice + (ToppingNum * ToppingPrice) ) / PizaaSize);
    return TotalPrice;
}


 void print_Receipt (int PizaaSize,int ToppingNum,double TotalPrice)
 {
    printf("Receipt:\n");
    printf("\t Size of Pizza = %d \n",PizaaSize);
    printf("\t Number of Toppings= %d\n ",ToppingNum);
    printf("\t Total Price = %.2lf sheikel \n",TotalPrice);
 }








