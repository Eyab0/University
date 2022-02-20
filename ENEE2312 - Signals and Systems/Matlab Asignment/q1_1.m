%X1(t) = u(t-5)- u(t-11)
clear all %clear all variables 
clc %clear command window
t = -20:0.0001:20; %time
x = heaviside(t-5) - heaviside(t-11); %function
figure('Name','Q1_1'); %figure name 
plot(t,x,'r'); %plot the function
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('x(t)','FontSize',12); % y label name
title('Question I - Part 1','FontSize',16); %title of the figure
grid on; 
grid minor; % more details on grid
axis([0 15 -0.5 1.5]); %set x & y axis 
xticks(0 : 20);

