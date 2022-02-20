clear ALL
clc
syms y(t)
q2 = (diff(y,t,2) + 2*diff(y,t) + 5*y) ==(5 .* cos(1500.*t));
dy=diff(y,t) ;
cond1 = y(0)==0;
cond2 = dy(0)==0;
sol = dsolve(q2,[cond1 cond2])
smpSol = simplify(sol)
