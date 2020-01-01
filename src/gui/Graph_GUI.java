package gui;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Graph_GUI {
    private static DGraph lastGraph;

    /**
     * saves last graph to a given file
     * @param filename
     * @throws IOException
     */
    public static void save(String filename) throws IOException {
        FileOutputStream out = new FileOutputStream(filename+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(out);
        if(lastGraph == null)return;
        oos.writeObject(lastGraph);
        oos.close();
        out.close();
    }

    /**
     * loads and draws from given file
     * @param filename
     * @throws Exception
     */
    public static void load(String filename)throws Exception {
        FileInputStream streamIn = new FileInputStream(filename);
        ObjectInputStream obj = new ObjectInputStream(streamIn);
        DGraph readCase = (DGraph) obj.readObject();
        StdDraw.clear();
        draw(readCase);
        lastGraph = readCase;
        streamIn.close();
        obj.close();
    }
    //auxiliary to find scale
    private static void setScale(DGraph graph)
    {
        Collection<node_data> c = graph.getV();
        Iterator<node_data> iterator = c.iterator();
        double max_x,min_x,max_y,min_y;
        if(iterator.hasNext()) {
            Point3D p = iterator.next().getLocation();
            max_x = p.x();
            min_x= p.x();
            max_y=p.y();
            min_y=p.y();
        }
        else {
            return;
        }
        while (iterator.hasNext()) {
            Point3D p = iterator.next().getLocation();
            if(p.x() < min_x)
            {
                min_x = p.x();
            }
            else if(p.x() > max_x)
            {
                max_x = p.x();
            }
            if(p.y() < min_y)
            {
                min_y = p.y();
            }
            else if(p.y() > max_y)
            {
                max_y = p.y();
            }
        }
        Range rx = new Range(min_x-20,max_x+20);
        Range ry = new Range(min_y-20,max_y+20);
        StdDraw.setXscale(rx.get_min(), rx.get_max());
        StdDraw.setYscale(ry.get_min(), ry.get_max());
    }
    /**
     * draws given graph in GUI
     * @param graph
     */
    public static void draw(DGraph graph)
    {
        StdDraw.setCanvasSize(600, 600);
        setScale(graph);
        Collection<node_data> c = graph.getV();
        Iterator<node_data> iterator = c.iterator();
        StdDraw.setPenColor(Color.blue);
        StdDraw.setPenRadius(0.02);
        while (iterator.hasNext())
        {
            node_data n = iterator.next();
            Point3D p = n.getLocation();
            StdDraw.point(p.x(),p.y());
            String s = n.getKey() +"";
            StdDraw.text(p.x()+3,p.y()+3,s);
        }
        StdDraw.setPenRadius(0.005);
        iterator = c.iterator();
        while (iterator.hasNext())
        {
            node_data n =iterator.next();
            Iterator<edge_data> e = graph.getE(n.getKey()).iterator();
            while (e.hasNext())
            {
                edge_data ed = e.next();
                Point3D src = n.getLocation();
                Point3D dest = graph.getNode(ed.getDest()).getLocation();
                double w = ed.getWeight() ;
                StdDraw.setPenColor(Color.yellow);
                StdDraw.line(src.x(), src.y(),dest.x()-1,dest.y()-1);
                StdDraw.setPenColor(Color.black);
                StdDraw.square(dest.x()-1,dest.y()-1,0.5);
                StdDraw.text((dest.x()+src.x())/2,(dest.y()+src.y())/2,String.format("%.2f", w));
            }
        }
        lastGraph = graph;
    }

    public static void main(String[] args) {
        Random random = new Random();
        DGraph graph = new DGraph();
        int nodes = 7;
        for (int i = 0; i <nodes ; i++) {
            node_data n= new Node(i,new Point3D(-100 + random.nextDouble()*100,-100 + random.nextDouble()*100),1 + random.nextDouble()*10);
            graph.addNode(n);
        }
        for (int i = 0; i < 11; i++) {
            int src =  random.nextInt(nodes);
            int dest =  random.nextInt(nodes);
            while (dest==src)
            {
                src =  random.nextInt(nodes);
                dest =  random.nextInt(nodes);
            }
            graph.connect(src, dest,1 + random.nextDouble()*10);
        }
        draw(graph);
        Graph_Algo ga = new Graph_Algo(graph);
        ga.shortestPath(1,5);
    }

}
