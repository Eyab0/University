%X2(t)= u(t-3) +r(t-6)-2r(t-9) +r(t-15) in the time interval [0 25]
clear all %clear all variables 
clc %clear command window
t = 0:0.0001:25;
x1 = heaviside(t-3);
x2=(t-6).*heaviside(t-6);
x3=2.*((t-9).*heaviside(t-9));
x4=(t-15).*heaviside(t-15);
x = x1 + x2 - x3 + x4;
figure('Name','Q1_3'); %figure name 
plot(t,x,'r'); %plot the function
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('x(t)','FontSize',12); % y label name
title('Question I - Part 3','FontSize',16); %title of the figure
grid on; 
grid minor; % more details on grid
axis([0 25 -3 5]); %set x & y axis 

