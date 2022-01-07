
from __future__ import division
from sympy import *

x, y, z, t, a, b, c = symbols('x y z t a b c')
k, m, n = symbols('k m n', integer=True)
f, g, h = symbols('f g h', cls=Function)


def main(val,cont):
    if(cont==3):
        M = Matrix([[1, 1, 1,x], [1, 1, 1,y], [1, 1, 5,z]])
        #return solve_linear_system_LU(Matrix([[1, 1, 0, x],[1, 0, 1, y],[0, 1, 1, z]]),[a, b, c])
    else:
        M = Matrix([[val[0],val[1],x],[val[3],val[4],y]])
        #return solve_linear_system_LU(Matrix([[1, 1, x],[1, 0, y]]),[a, b, c])
    return solve_linear_system_LU(M,[a, b, c])



