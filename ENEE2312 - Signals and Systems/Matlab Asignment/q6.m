clear all
syms t toe
clc
figure('Name','Q6 : y2(t) = (10e-8t)pi((t-2)/4), y2(t) = (10e-8tcos 100t) pi((t-6)/8)'); %figure name  
y1 = 10.*exp(-8.*toe).*(rectangularPulse(0,4,toe));
y2 = (10.*exp(-8.*(t-toe)).*(cos (100*(t-toe)))).*(rectangularPulse(2,10,t-toe));
func = int(y1*y2,toe,-inf,inf);
sol = simplify(func);
fplot(func,[2 3])
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('convolution','FontSize',12); % y label name
title('Convlution for y1(t) and y2(t)')
grid on; 
grid minor; % more details on grid

        
