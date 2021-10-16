#include <stdio.h>
#include <stdlib.h>

/***********************
 * Name : Eyab Ghifari *
 * ID : 1190999        *
 * Lab Section : 3     *
 ***********************/



int find_even_sum (int);
int find_odd_sum (int);


int main()
{

    int number , option_num, digits_num = 0,  sum_even_digits ,  sum_odd_digits ;

    printf("Enter any four digit number: \n ");
    scanf("%d", &number);

    int num = number;
    while(num != 0)
    {
        digits_num++; /*get number of digits in the number*/
        num /= 10;
    }

    if(digits_num != 4)
    {
        printf("\n%d is not a four digit number, goodbye\n", number);
        return 0;
    }

    printf("\nSelect what you want to do with the number 1-3: \n");
    printf("\t1_Print sum of even digits.\n");
    printf("\t2_Print sum of odd digits.\n");
    printf("\t3_Print relation of even/odd sum of digits larger/smaller/equal. \n");
    scanf("%d", &option_num);
    printf("Option number %d : \n ",option_num );

    sum_even_digits= find_even_sum (number);
    sum_odd_digits= find_odd_sum(number);

    switch(option_num){
        case 1:
            printf("sum of even digits = %d",  sum_even_digits);
            break;

        case 2:
            printf("sum of odd digits = %d",  sum_odd_digits);
            break;

        case 3:
            if(sum_even_digits > sum_odd_digits)
                printf("Sum of even digits in %d = %d  is larger than sum of odd digits = %d \n" ,number
                       ,sum_even_digits,sum_odd_digits);

            else if (sum_even_digits < sum_odd_digits)
                    printf("Sum of odd digits in %d = %d  is larger than sum of even digits = %d \n" ,number
                       ,sum_odd_digits,sum_even_digits);

            else
                printf("Sum of even digits in %d is equal to sum of odd digits = %d",number,sum_even_digits);

            break;

        default:
            printf("\n This is not a correct option\n");
    }
    return 0;
}

int find_even_sum (int number)
{
    int sum_even = 0;
    while(number != 0)
    {
        if((number % 2) == 0)
             sum_even += (number % 10); /* get sum of even digits in the number*/
        number /= 10;
    }
    return sum_even;
}

int find_odd_sum (int number)
{
    int sum_odd = 0;
    while(number != 0)
    {
        if((number % 2) != 0)
            sum_odd += (number % 10); /* get sum of odd digits in the number*/
        number /= 10;
    }
    return sum_odd;
}
