clear ALL
clc 

t = -2:0.000001:2;
y1 = sin(500*pi*t);
y2 = cos(2000*pi*t);

figure('Name','Q2_1'); %figure name 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
subplot(2,1,1);
plot(t,y1,'r');
xlim([-0.008 0.008]);
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('y1(t)','FontSize',12); % y label name
title('y1(t)= sin 500*pi*(t)','FontSize',16); %title of the figure
grid on; 
grid minor; % more details on grid
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
subplot(2,1,2);
plot(t,y2,'r');
xlim([-0.008 0.008]);
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('y2(t)','FontSize',12); % y label name
title('y2(t)= cos2000*pi*t','FontSize',16); %title of the figure
grid on; 
grid minor; % more details on grid
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
figure('Name','Q2_2'); %figure name 
m = y1 + y2;
n = y1 - y2;

subplot(2,1,1);
plot(t,m,'r');
xlim([-0.005 0.005]);
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('m(t)','FontSize',12); % y label name
title('m(t)= y1+y2','FontSize',16); %title of the plot
grid on; 
grid minor; % more details on grid

subplot(2,1,2);
plot(t,n,'r');
xlim([-0.005 0.005]);
xlabel('Time(Seconds)','FontSize',12); %x label name 
ylabel('n(t)','FontSize',12); % y label name
title('n(t)= y1-y2','FontSize',16); %title of the plot
grid on; 
grid minor; % more details on grid






