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
with Diagram('cargoserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxproductservice', graph_attr=nodeattr):
          productservice=Custom('productservice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxcargoservice', graph_attr=nodeattr):
          clientmock=Custom('clientmock','./qakicons/symActorWithobjSmall.png')
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          requestvalidator=Custom('requestvalidator','./qakicons/symActorWithobjSmall.png')
          cargorobot=Custom('cargorobot','./qakicons/symActorWithobjSmall.png')
          moveexec=Custom('moveexec','./qakicons/symActorWithobjSmall.png')
          sonarmock=Custom('sonarmock','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='alarm', **evattr, decorate='true', fontcolor='darkgreen') >> cargoservice
     sys >> Edge( label='alarm', **evattr, decorate='true', fontcolor='darkgreen') >> moveexec
     sonarmock >> Edge( label='alarm', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     moveexec >> Edge(color='magenta', style='solid', decorate='true', label='<engage<font color="darkgreen"> engagedone engagerefused</font> &nbsp; moverobot<font color="darkgreen"> moverobotdone moverobotfailed</font> &nbsp; >',  fontcolor='magenta') >> basicrobot
     cargorobot >> Edge(color='magenta', style='solid', decorate='true', label='<move<font color="darkgreen"> movedone movefailed</font> &nbsp; >',  fontcolor='magenta') >> moveexec
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<validate_request<font color="darkgreen"> validate_accepted validate_refused</font> &nbsp; >',  fontcolor='magenta') >> requestvalidator
     clientmock >> Edge(color='magenta', style='solid', decorate='true', label='<createProduct<font color="darkgreen"> createdProduct</font> &nbsp; >',  fontcolor='magenta') >> productservice
     clientmock >> Edge(color='magenta', style='solid', decorate='true', label='<load_request<font color="darkgreen"> load_accepted load_refused</font> &nbsp; >',  fontcolor='magenta') >> cargoservice
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getProduct<font color="darkgreen"> getProductAnswer</font> &nbsp; >',  fontcolor='magenta') >> productservice
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<transport<font color="darkgreen"> robot_home</font> &nbsp; >',  fontcolor='magenta') >> cargorobot
     sonarmock >> Edge(color='blue', style='solid',  decorate='true', label='<containerAtIOPort &nbsp; >',  fontcolor='blue') >> cargoservice
diag
