package org.example.graphingcalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.graphingcalculator.expressions.Expression;

public class GraphingCalculator extends Application {
	public static void main (String[] args) {
		launch(args);
	}

	protected static final int WINDOW_WIDTH = 520, WINDOW_HEIGHT = 500;
	protected static final double MIN_X = -10, MAX_X = +10, DELTA_X = 0.01;
	protected static final double MIN_Y = -10, MAX_Y = +10;
	protected static final double GRID_INTERVAL = 5;
	protected static final String EXAMPLE_EXPRESSION = "2*x+5*x*x";
	protected final ExpressionParser expressionParser = new SimpleExpressionParser();

	private void graph (LineChart<Number, Number> chart, Expression expression, boolean clear) {
		final XYChart.Series series = new XYChart.Series();
		for (double x = MIN_X; x <= MAX_X; x += DELTA_X) {
			final double y = expression.evaluate(x);
			series.getData().add(new XYChart.Data(x, y));
		}
		if (clear) {
			chart.getData().clear();
		}
		chart.getData().addAll(series);
	}

	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle("Graphing Calculator");

		final Pane queryPane = new HBox(30);
		queryPane.setStyle("-fx-alignment: center;");

		final HBox expressionPane = new HBox(5);
		final Label label = new Label("y =");
		VBox labelContainer = new VBox(label);
		labelContainer.setAlignment(Pos.CENTER);
		label.setStyle("-fx-font-size: 13px;");
		final TextField textField = new TextField(EXAMPLE_EXPRESSION);

		expressionPane.getChildren().addAll(labelContainer, textField);
		queryPane.getChildren().add(expressionPane);

		final Button graphButton = new Button("Graph");
		final CheckBox diffBox = new CheckBox("Show Derivative");
		diffBox.setStyle("-fx-font-size: 12px;");

		final Pane graphPane = new Pane();
		final LineChart<Number, Number> chart = new LineChart<Number, Number>(new NumberAxis(MIN_X, MAX_X, GRID_INTERVAL), new NumberAxis(MIN_Y, MAX_Y, GRID_INTERVAL));
		chart.setLegendVisible(false);
		chart.setCreateSymbols(false);
		graphPane.getChildren().add(chart);
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				// Parse the new expression from the text field
				final Expression expression = expressionParser.parse(newValue);

				// Clear the chart and graph the new expression
				graph(chart, expression, true);

				// If "Show Derivative" is checked, graph the derivative as well
				if (diffBox.isSelected()) {
					final Expression derivative = expression.differentiate();
					graph(chart, derivative, false);
				}

				// Reset text color to normal
				textField.setStyle("-fx-text-fill: black");
			} catch (Exception e) {
				// Highlight invalid input in red
				textField.setStyle("-fx-text-fill: red");
			}
		});

		diffBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			try {
				// Get the current expression from the text field
				final Expression expression = expressionParser.parse(textField.getText());

				// Clear the chart and graph the original function (blue)
				graph(chart, expression, true);

				// Check if the derivative should be shown
				if (newValue) { // If checked, graph the derivative
					final Expression derivative = expression.differentiate();
					graph(chart, derivative, false);
				}

			} catch (Exception e) {
				// Handle any parsing exceptions or errors
				textField.setStyle("-fx-text-fill: red");
			}
		});

		graphButton.setStyle(
						"-fx-text-fill: black; " +         // White button text
						"-fx-font-size: 12px; " +          // Font size
						"-fx-background-radius: 5;"        // Rounded corners
		);

		textField.setStyle(
				"-fx-background-color: #f0f0f0; " + // Light gray background
						"-fx-border-color: #ccc; " +        // Subtle border
						"-fx-border-radius: 5;"             // Rounded border
		);

		queryPane.getChildren().add(diffBox);
		//queryPane.getChildren().add(graphButton);

		queryPane.setPadding(new javafx.geometry.Insets(20, 0, 0, 0));

		textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));
		
		final BorderPane root = new BorderPane();
		root.setTop(queryPane);
		root.setCenter(graphPane);

		final Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
