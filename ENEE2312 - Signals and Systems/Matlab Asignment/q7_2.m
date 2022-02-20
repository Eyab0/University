clear all
clc
syms t q1
%m=10*cos(2*pi*100*t);
x = [-100,100];
y = dirac(x+100) + dirac(x-100);
idx = y == Inf; % find Inf
y(idx) = 5;     % set Inf to finite value
figure('Name','Q7_2 : spectral representation of the message'); %figure name
stem(x,y)
grid on;
axis([-110 110 -1 6]);
%c=15*cos(2*pi*1200*t);
x = [-1200,1200];
y = dirac(x+1200) + dirac(x-1200);
idx = y == Inf; 
y(idx) = 7.5;     % set Inf to finite value
figure('Name','Q7_2 : spectral representation of the carrier'); %figure name
stem(x,y)
grid on;
axis([-1300 1300 -2 8]);
%s=m.*c;
x = [1300,1100,-1300,-1100];
y = dirac(x-1300) + dirac(x+1100) + dirac(x+1300) + dirac(x-1100);
idx = y == Inf; 
y(idx) = 37.5;     % set Inf to finite value
figure('Name','Q7_2 : spectral representation of the modulated signal'); %figure name
stem(x,y)
grid on;
axis([-1400 1400 -5 50]);