clear ALL
clc
syms  t y(t)
q1 = diff(y,t)+5*y == 10.*heaviside(t);
cond = (y(0)==3);
rsp = dsolve(q1,cond)
smpSol = simplify(rsp)
