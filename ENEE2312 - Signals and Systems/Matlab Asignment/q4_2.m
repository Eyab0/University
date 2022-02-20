clear ALL
clc
syms  t y(t)
q2= diff(y,t,2) + 10*diff(y,t) + 9*y == 5*cos(2000.*t);

Dy = diff(y,t);
c1 = y(0)==1;
c2 = Dy(0)==2;

rsp = dsolve(q2,[c1,c2])
smpSol = simplify(rsp)

