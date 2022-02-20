clear ALL
clc
syms y(x)
q1 = (diff(y,x)+10*y == 2);
cond = y(0)==0;
sol = dsolve(q1,cond)
smpSol = simplify(sol)
