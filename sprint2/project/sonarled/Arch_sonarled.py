### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('sonarledArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxiodevices', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorWithobjSmall.png')
          led=Custom('led','./qakicons/symActorWithobjSmall.png')
          sonardevice=Custom('sonardevice','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxcargoservice', graph_attr=nodeattr):
          cargoservice=Custom('cargoservice(ext)','./qakicons/externalQActor.png')
     sonar >> Edge( label='alarm', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar >> Edge( label='endalarm', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar >> Edge(color='blue', style='solid',  decorate='true', label='<containerAtIOPort &nbsp; >',  fontcolor='blue') >> cargoservice
     sonardevice >> Edge(color='blue', style='solid',  decorate='true', label='<sonardata &nbsp; >',  fontcolor='blue') >> sonar
diag
