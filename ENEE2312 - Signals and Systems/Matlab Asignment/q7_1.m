clear all
clc
syms t q1
figure('Name','Q7_1 : modulated signal'); %figure name 
q1=(10*cos(2*pi*100*t)).*(15*cos(2*pi*1200*t)); 
fplot(q1);
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('m(t)','FontSize',12); % y label name
title('modulated signal')
axis([4.4 4.42 -150 150]);

