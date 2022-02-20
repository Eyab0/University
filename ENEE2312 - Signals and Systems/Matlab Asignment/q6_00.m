clear all
%syms t toe
clc
t= 0:0.001:3;
figure('Name','Q6 : y2(t) = (10e-8t)pi((t-2)/4), y2(t) = (10e-8tcos 100t) pi((t-6)/8)'); %figure name  
y1 = 10*exp(-8*t).*(rectangularPulse(0,4,t));
y2 = (10*exp(-8*t).*(cos (100*(t)))).*(rectangularPulse(2,10,t));
y=conv(y1,y2);
t=0:0.001:6;
plot(t,y)
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('convolution','FontSize',12); % y label name
title('Convlution for y1(t) and y2(t)')
grid on; 
grid minor; % more details on grid

        
