module UIEyab {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;

	opens application to javafx.graphics, javafx.fxml, javafx.base;

//	opens org.application to javafx.base ;

}
