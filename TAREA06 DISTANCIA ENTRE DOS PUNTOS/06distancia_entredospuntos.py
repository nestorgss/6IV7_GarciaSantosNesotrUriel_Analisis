
import numpy as np
import pandas as pd
from scipy.spatial import distance 


tiendas= {
    'Punto A': (2, 3),
    'Punto B': (5, 4),
    'Punto C': (1, 1),
    'Punto D': (6, 7),
    'Punto E': (3, 5),
    'Punto F': (8, 2),
    'Punto G': (4, 6),
    'Punto H': (2, 1)
   
}


df_tiendas= pd.DataFrame(tiendas).T
df_tiendas.columns=['X', 'Y']
print('Coordenadas de las tiendas: ')
print(df_tiendas)

 

distancias_punto1=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

distancias_punto2=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

distancias_punto3=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)




for i in df_tiendas.index:
    for j in df_tiendas.index:
    
        distancias_punto1.loc[i, j]= distance.euclidean(df_tiendas.loc[i], df_tiendas.loc[j])
        
        distancias_punto2.loc[i, j]= distance.cityblock(df_tiendas.loc[i], df_tiendas.loc[j])
        
        distancias_punto3.loc[i, j]= distance.chebyshev(df_tiendas.loc[i], df_tiendas.loc[j])
        
print('/n Distancia euclidiana entre cada una de las tiendas')
print(distancias_punto1)
print(' ')
print('/n Distancia cityblock entre cada una de las tiendas')
print(distancias_punto2)
print(' ')
print('/n Distancia Chebyshev entre cada una de las tiendas')
print(distancias_punto3)


