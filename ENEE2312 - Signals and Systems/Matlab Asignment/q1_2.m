%A finite pulse (?(t)) with value = 6 and extension between 6 and 14
clear all %clear all variables 
clc %clear command window
syms t x   
x = 6.* rectangularPulse(6,14,t);  % pi((t-9)/14)
figure('Name','Q1_2'); %figure name 
fplot(x,'r'); %plot the function
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('x(t)','FontSize',12); % y label name
title('Question I - Part 2','FontSize',16); %title of the figure
grid on; 
grid minor; % more details on grid
axis([0 20 -1 10]); %set x & y axis 