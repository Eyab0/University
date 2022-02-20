clear all
clc
syms t q1
%power sd
x = [1300,1100,-1300,-1100];
y = dirac(x-1300) + dirac(x+1100) + dirac(x+1300) + dirac(x-1100);
idx = y == Inf; % find Inf
y(idx) = 37.5*37.5;     % set Inf to finite value
figure('Name','Q7_3 : spectral density of the modulated signal'); %figure name
stem(x,y)
grid on;
axis([-1500 1500 -50 1500]);