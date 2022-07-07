package sample;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

public class Controller implements Initializable {
    public static Graph g;
    public ChoiceBox<String> algo;
    @FXML
    private AnchorPane ap0;

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField tfSource;

    @FXML
    private TextField tfDestination;

    @FXML
    private AnchorPane ap1;

    @FXML
    private Text tfDistance;

    @FXML
    private TextArea taPath;
    private Vertex Source;
    private Vertex Destination;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    int NumOfVerticies = 0, NumOfEdges = 0;
    HashMap<String, Algorithem> algorithemHashMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithemHashMap = new HashMap<>();
        File f = new File("graphData.txt");


        try {
            Scanner sc;
            sc = new Scanner(f);

            NumOfVerticies = sc.nextInt();
            g = new Graph(NumOfVerticies);
            sc.nextLine();
            String[] split;
            Vertex v;
            for (int i = 0; i < NumOfVerticies; i++) {
                Circle c;
                split = sc.nextLine().split(" ");
                v = new Vertex(split[0], Double.parseDouble(split[2]), Double.parseDouble(split[1]));
                c = new Circle();

                c.setRadius(6);
                c.setLayoutX(((v.getX() - 34.11839) / 0.002827777778) + 15);
                c.setLayoutY(((33.34777 - v.getY()) / 0.005277123288));
                c.setFill(Color.GRAY);
                Label l = new Label(split[0]);
                l.setFont(Font.font(10));
                l.setLayoutY(c.getLayoutY() - 6);
                l.setLayoutX(c.getLayoutX() + 8);

                ap.getChildren().addAll(c, l);
                c.setId(i + "");
                v.setC(c);
                g.addVertex(v);
                final Vertex v2 = v;

                c.setOnMouseClicked(mouseEvent -> {
                    if (Source == null && tfSource.getText().compareTo("") == 0) {
                        Source = v2;
                        tfSource.setText(Source.getName());
                        v2.getC().setFill(Color.LAWNGREEN);
                        v2.getC().setRadius(8);
                    } else if (Destination == null && tfDestination.getText().compareTo("") == 0) {
                        Destination = v2;
                        tfDestination.setText(Destination.getName());
                        v2.getC().setFill(Color.ORANGERED);
                        v2.getC().setRadius(8);
                    }
                    if (Source == Destination) {
                        Alert a = new Alert(AlertType.WARNING);
                        a.setContentText("Error, Source And Destination Are The Same");
                        a.show();
                        Clear();
                    }
                });

            }
            sc.close();
            File f2 = new File("data.txt");
            Scanner scan = new Scanner(f2);
            while (scan.hasNextLine()) {
                split = scan.nextLine().split(",");
                Vertex src = g.getVList()[g.getVertexIndex(split[0])];
                Vertex dis = g.getVList()[g.getVertexIndex(split[1])];
                Edge edge = new Edge(src, dis, Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
                g.addEdge(edge);
            }
            algo.getItems().addAll("Depth First Search", "Breadth First Search", "Uniform Cost", "A* 1", "A* 2");
            algo.setValue("Depth First Search");
            algorithemHashMap.put("Depth First Search", new DFS(g));
            algorithemHashMap.put("Breadth First Search", new BFS(g));
            algorithemHashMap.put("Uniform Cost", new UniformCost(g, Edge::getCarDistance));
            algorithemHashMap.put("A* 1", new AStar(g, Edge::getCarDistance, Edge::getWalkDistance));
            algorithemHashMap.put("A* 2", new AStar(g, Edge::getWalkDistance, Edge::getAirDistance));

            //"Breadth First Search","Uniform Cost","A*"
            ap.setOnMousePressed(OnMousePressedEventHandler);
            ap.setOnMouseDragged(OnMouseDraggedEventHandler);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


    }

    EventHandler<MouseEvent> OnMousePressedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((AnchorPane) (t.getSource())).getTranslateX();
                    orgTranslateY = ((AnchorPane) (t.getSource())).getTranslateY();
                }
            };
    EventHandler<MouseEvent> OnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((AnchorPane) (t.getSource())).setTranslateX(newTranslateX);
                    ((AnchorPane) (t.getSource())).setTranslateY(newTranslateY);
                }
            };

    @FXML
    void Run(ActionEvent event) throws Exception {
        Algorithem d = algorithemHashMap.get(algo.getValue());
        ap1.setVisible(true);
        Alert a = new Alert(AlertType.WARNING);
        if (tfSource.getText().isEmpty() || tfDestination.getText().isEmpty()) {
            a.setContentText("Source Isn't A Valid Name");
            a.show();
            tfSource.clear();
            Clear();
            return;
        }
        Source = g.getVList()[g.getVertexIndex(tfSource.getText().trim())];
        Destination = g.getVList()[g.getVertexIndex(tfDestination.getText().trim())];
        if (Source == null) {
            a.setContentText("Source Isn't A Valid Name");
            a.show();
            tfSource.clear();
            Clear();
            return;
        } else if (Destination == null) {
            a.setContentText("Destination is Same As Source, Or its Invalid");
            a.show();
            tfDestination.clear();
            Clear();
            return;
        }
        Source.getC().setFill(Color.LAWNGREEN);
        Source.getC().setRadius(8);
        Destination.getC().setFill(Color.ORANGERED);
        Destination.getC().setRadius(8);
        d.setStart(Source);
        d.find(Destination);

        d.getPath(Destination);
        for (Vertex v : d.Expansion
        ) {
            System.out.println(v);
        }
        if (d.distance == Integer.MAX_VALUE) {
            tfDistance.setText("");
            taPath.appendText("No Path");
            return;
        }
        tfDistance.setText(String.valueOf(d.distance));
        taPath.appendText(Source.getName());
        Line l = new Line();
        l.setStartX(x_axis(Source.getX()));
        l.setStartY(y_axis(Source.getY()));
        for (int i = d.Path.size() - 1; i >= 0; i--) {
            l.setEndX(x_axis(d.Path.get(i).getX()));
            l.setEndY(y_axis(d.Path.get(i).getY()));
            l.setStrokeWidth(3);
            l.setOpacity(0.60);
            ap.getChildren().add(l);
            l = new Line();
            l.setStartX(x_axis(d.Path.get(i).getX()));
            l.setStartY(y_axis(d.Path.get(i).getY()));
            taPath.appendText("\n -> " + d.Path.get(i).getName());
        }
    }

    public void Clear() {
        tfSource.clear();
        tfDestination.clear();
        taPath.clear();
        tfDistance.setText("");
        if (Source != null) {
            Source.getC().setFill(Color.GREY);
            Source.getC().setRadius(6);
        }
        if (Destination != null) {
            Destination.getC().setFill(Color.GREY);
            Destination.getC().setRadius(6);
        }
        Source = null;
        Destination = null;

        ap1.getChildren().clear();
        ap1.setVisible(false);


        int s = ap.getChildren().size();

        for (int i = s - 1; i > (NumOfVerticies + NumOfVerticies + 1); i--) { //97 = 48 (for circle) + 48 (for label(name of city)) + 1 the image

            if (ap.getChildren().get(i) instanceof Line) {
                ap.getChildren().remove(i);
            }
        }
    }

    public void Exit() {
        System.exit(0);
    }


    public int x_axis(double Longitude) {
        double x = ((Longitude - 34.11839) / 0.002827777778) + 15;
        //double x = (Longitude-34.11839)*1040/3.32;
        return (int) x;
    }

    public int y_axis(double Latitude) {
        double y = (33.34777 - Latitude) / 0.005277123288;
        //double y= (33.34777-Latitude)*460/3.77;
        return (int) y;
    }

    private int distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        dist = dist * 0.8684;

        return ((int) dist);
    }


    /**
     * converts decimal degrees to radians
     *
     * @param deg
     * @return radians
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    /**
     * converts radians to decimal degrees
     *
     * @param rad degrees
     * @return decimal degrees
     */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void zoomIn() {
        Scale newScale = new Scale();
        newScale.setX(ap.getScaleX() + 0.15);
        newScale.setY(ap.getScaleY() + 0.15);
        newScale.setPivotX(ap.getScaleX());
        newScale.setPivotY(ap.getScaleY());
        ap.getTransforms().add(newScale);

    }

    public void zoomout() {
        Scale newScale = new Scale();
        newScale.setX(ap.getScaleX() - 0.15);
        newScale.setY(ap.getScaleY() - 0.15);
        newScale.setPivotX(ap.getScaleX());
        newScale.setPivotY(ap.getScaleY());
        ap.getTransforms().add(newScale);
    }

}
