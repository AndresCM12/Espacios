from __future__ import division
from sympy import *

x, y, z, t, a, b, c = symbols('x y z t a b c')
k, m, n = symbols('k m n', integer=True)
f, g, h = symbols('f g h', cls=Function)

def main(val,cont):
    if(cont==3):
        #M = Matrix([[1.0, 1.0, 0.0, x],[1.0, 0.0, 1.0, y],[0.0, 1.0, 1.0, z]])
        M = Matrix([[int(val[0]), int(val[1]), int(val[2]),x], [int(val[3]), int(val[4]), int(val[5]),y], [int(val[6]), int(val[7]), int(val[8]),z]])
        try:
            return solve_linear_system_LU(M,[a, b, c])
        except:
            return ""
    else:
        M = Matrix([[int(val[0]),int(val[1]),x],[int(val[3]),int(val[4]),y]])
        try:
            return solve_linear_system_LU(M,[a, b])
        except:
            return ""



def det(val,cont):
    if(cont==3):
        M = Matrix([[val[0], val[1], val[2]], [val[3], val[4], val[5]], [val[6], val[7], val[8]]])
    else:
        M = Matrix([[val[0],val[1]],[val[3],val[4]]])

    if (M.det()!=0):
        return "Sí son "
    else:
        return "No son "