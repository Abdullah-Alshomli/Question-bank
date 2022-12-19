import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MCQBank extends Application {
    protected Stage window; // window is the primaryStage
    protected String fontName = "Baskerville Old Face";
    protected Scene mainScene;
    protected Button homeButton = new Button("Home");// button to go to the main stage
    protected String filename = "QuestionBank.dat";
    protected ComboBox<String> questionBox = new ComboBox<>();// combo box to choose a question
    protected MCQ theQuestion;// the object used to get the Question and it`s answers
    protected String[] mainArray = getQuestionsList(filename);// array of the questions

    @Override
    public void start(Stage primaryStage) {

        /* primary Stage settings */
        window = primaryStage;
        primaryStage.setTitle("MCQBank");
        primaryStage.setWidth(650);
        primaryStage.setHeight(900);
        primaryStage.setResizable(true);

        /* main scene */

        // primaryStage buttons
        Button createButton = new Button("Create Question");
        Button editButton = new Button("Edit Question");
        Button viewButton = new Button("View Question");
        Button deleteButton = new Button("Delete Question");

        // making a font to change the size of the buttons text
        Font font = new Font(fontName, 55);
        Font smallFont = new Font(fontName, 40);
        Label chooseText = new Label("Choose a Question");

        createButton.setFont(font);
        chooseText.setFont(font);
        editButton.setFont(smallFont);
        viewButton.setFont(smallFont);
        deleteButton.setFont(smallFont);

        // buttons and combo box size
        createButton.setPrefSize(500, 100);
        questionBox.setPrefSize(500, 50);
        editButton.setPrefSize(400, 100);
        viewButton.setPrefSize(400, 100);
        deleteButton.setPrefSize(400, 100);

        /* main array and question array */

        // adding the questions to the combo box
        updateBox(toQuestionArray(mainArray));

        // main panes
        GridPane nodePane = new GridPane();
        VBox nodeBox = new VBox();
        VBox nodeBox2 = new VBox();

        // adding nodes
        nodeBox.getChildren().addAll(createButton);
        nodeBox2.getChildren().addAll(chooseText, questionBox, viewButton, editButton, deleteButton);

        nodeBox2.setSpacing(15);
        nodeBox2.setAlignment(Pos.CENTER);

        nodePane.add(nodeBox, 0, 0);
        nodePane.add(nodeBox2, 0, 2);

        // nodePane alignment
        nodePane.setAlignment(Pos.CENTER);
        nodePane.setVgap(30);

        mainScene = new Scene(nodePane, 650, 900);

        // buttons functionality
        createButton.setOnAction(e -> switchToCreate());
        viewButton.setOnAction(e -> switchToView(questionBox.getValue()));
        editButton.setOnAction(e -> switchToEdit(questionBox.getValue()));
        deleteButton.setOnAction(e -> delete(questionBox.getValue()));

        // saving the array in the file upon exit
        window.setOnCloseRequest(e -> {
            updateFile(filename, mainArray);
        });

        /* final */
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /* Create Scene */
    public void switchToCreate() {

        // Home button
        homeButton.setOnAction(e -> home());
        VBox homeBox = new VBox();
        homeBox.getChildren().add(homeButton);
        GridPane finalPane = new GridPane();
        Font homeFont = new Font(fontName, 15);
        finalPane.add(homeButton, 0, 0);
        homeButton.setFont(homeFont);
        homeButton.setPrefSize(75, 50);

        // Font
        Font font1 = new Font("Baskerville Old Face", 40);
        Font font2 = new Font("Baskerville Old Face", 25);

        // create a text fields
        TextField Write1 = new TextField();
        TextField Write2 = new TextField();
        TextField Write3 = new TextField();
        TextField Write4 = new TextField();
        TextField WriteQ = new TextField();
        WriteQ.setPrefSize(450, 50);
        Write1.setPrefSize(300, 30);
        Write2.setPrefSize(300, 30);
        Write3.setPrefSize(300, 30);
        Write4.setPrefSize(300, 30);

        // create labels
        Label LabelQ = new Label("Write a Quistion", WriteQ);
        LabelQ.setContentDisplay(ContentDisplay.BOTTOM);
        LabelQ.setFont(font1);

        Label Label1 = new Label("  Answer 1 ", Write1);
        Label1.setContentDisplay(ContentDisplay.RIGHT);
        Label1.setFont(font2);

        Label Label2 = new Label("  Answer 2 ", Write2);
        Label2.setContentDisplay(ContentDisplay.RIGHT);
        Label2.setFont(font2);

        Label Label3 = new Label("  Answer 3 ", Write3);
        Label3.setContentDisplay(ContentDisplay.RIGHT);
        Label3.setFont(font2);

        Label Label4 = new Label("  Answer 4 ", Write4);
        Label4.setContentDisplay(ContentDisplay.RIGHT);
        Label4.setFont(font2);

        // Confirm creating button
        Button ConfirmCreating = new Button("Confirm");
        ConfirmCreating.setFont(font2);
        ConfirmCreating.setPrefSize(450, 20);

        // RadioButton for correct answer
        RadioButton Answer1 = new RadioButton();
        RadioButton Answer2 = new RadioButton();
        RadioButton Answer3 = new RadioButton();
        RadioButton Answer4 = new RadioButton();

        // ToggleGroup for the choices
        ToggleGroup answerGroup = new ToggleGroup();
        Answer1.setToggleGroup(answerGroup);
        Answer2.setToggleGroup(answerGroup);
        Answer3.setToggleGroup(answerGroup);
        Answer4.setToggleGroup(answerGroup);

        // create a Grid pane
        GridPane CreatePane = new GridPane();
        CreatePane.setAlignment(Pos.CENTER);
        CreatePane.setVgap(70);

        // HBox for arranging
        HBox H1 = new HBox(Answer1, Label1);
        HBox H2 = new HBox(Answer2, Label2);
        HBox H3 = new HBox(Answer3, Label3);
        HBox H4 = new HBox(Answer4, Label4);

        // VBox
        VBox V1 = new VBox(H1, H2, H3, H4, ConfirmCreating);
        V1.setSpacing(25);

        // add text field
        CreatePane.add(LabelQ, 0, 1);
        CreatePane.add(V1, 0, 2);

        // Stock Pane
        StackPane RealPane = new StackPane();
        RealPane.getChildren().add(CreatePane);

        // adding Home button
        finalPane.add(RealPane, 0, 1);
        finalPane.setAlignment(Pos.CENTER);
        finalPane.setVgap(20);

        // Confirm Creating functionality
        ConfirmCreating.setOnAction(e -> {
            // to check if the question is already exists in the array
            if (questionExists(WriteQ.getText()) == true) {
                message("Duplicated Question", "This question already exist");

            } else {

                String[] newArray;
                // to check if the main array is empty or not
                if (mainArray.length == 1) {
                    newArray = new String[mainArray.length + 5];

                    clearBox();
                } else {
                    newArray = new String[mainArray.length + 6];

                }

                String choice = new String();
                // to check if the user filled all the text fields
                if (WriteQ.getText().equals("") || Write1.getText().equals("") || Write2.getText().equals("")
                        || Write3.getText().equals("") || Write4.getText().equals("")) {

                    message("Not completed", "you need to fill all the boxes");

                }
                // to check if the user choose a correct answer
                else if (!(Answer1.isSelected() || Answer2.isSelected() || Answer3.isSelected()
                        || Answer4.isSelected())) {
                    message("Not completed", "you need to chick the correct answer");
                }
                // creating the question
                else {
                    for (int i = 0; i < mainArray.length; i++) {

                        newArray[i] = mainArray[i];
                    }
                    // to check what is the correct answer
                    if (Answer1.isSelected()) {
                        choice = Write1.getText();
                    } else if (Answer2.isSelected()) {
                        choice = Write2.getText();
                    } else if (Answer3.isSelected()) {
                        choice = Write3.getText();
                    } else {
                        choice = Write4.getText();
                    }

                    // adding the question and the answers to the new array
                    newArray[newArray.length - 1] = choice;
                    newArray[newArray.length - 2] = Write4.getText();
                    newArray[newArray.length - 3] = Write3.getText();
                    newArray[newArray.length - 4] = Write2.getText();
                    newArray[newArray.length - 5] = Write1.getText();
                    newArray[newArray.length - 6] = WriteQ.getText();

                    message("Created", "New question has been add");

                    // updating the data in the main array
                    updateMainArray(newArray);

                    // updating the data in the combo box
                    updateBox(toQuestionArray(mainArray));
                }

            }

        });

        // create a scene
        Scene Creating = new Scene(finalPane, 650, 900);

        // set the scene
        window.setScene(Creating);
        window.show();

    }

    /* View scene */
    public void switchToView(String question) {

        // to check if the user chose a question from the combo box
        if (question == null) {
            // popup message
            message("Error", "You need to select a question");

        } else {
            // getting the question answers
            String[] answerArray = getAnswers(question);

            // making an MCQ object
            theQuestion = new MCQ(question, answerArray[0], answerArray[1], answerArray[2], answerArray[3],
                    answerArray[4]);

            // Home button
            homeButton.setOnAction(e -> home());
            VBox homeBox = new VBox();
            homeBox.getChildren().add(homeButton);
            GridPane finalPane = new GridPane();
            Font homeFont = new Font(fontName, 15);
            finalPane.add(homeButton, 0, 0);
            homeButton.setFont(homeFont);
            homeButton.setPrefSize(75, 50);

            // Font
            Font font2 = new Font(fontName, 20);
            Font font3 = new Font(fontName, 30);

            // create labels
            Label mainLabel = new Label(theQuestion.question);
            mainLabel.setFont(font3);

            Label Label1 = new Label(theQuestion.answer1);
            Label1.setFont(font2);

            Label Label2 = new Label(theQuestion.answer2);
            Label2.setFont(font2);

            Label Label3 = new Label(theQuestion.answer3);
            Label3.setFont(font2);

            Label Label4 = new Label(theQuestion.answer4);
            Label4.setFont(font2);

            // Confirm choice Button
            Button confirm = new Button("Confirm");
            confirm.setFont(font2);
            confirm.setPrefSize(450, 20);

            // RadioButton for correct answer
            ToggleGroup viewGroup = new ToggleGroup();

            RadioButton Answer1 = new RadioButton();
            RadioButton Answer2 = new RadioButton();
            RadioButton Answer3 = new RadioButton();
            RadioButton Answer4 = new RadioButton();

            Answer1.setToggleGroup(viewGroup);
            Answer2.setToggleGroup(viewGroup);
            Answer3.setToggleGroup(viewGroup);
            Answer4.setToggleGroup(viewGroup);

            // confirm button functionality
            confirm.setOnAction(e -> {

                // siting the choice of the user
                String choice = new String();
                if (Answer1.isSelected()) {
                    choice = theQuestion.answer1;

                } else if (Answer2.isSelected()) {
                    choice = theQuestion.answer2;

                } else if (Answer3.isSelected()) {
                    choice = theQuestion.answer3;

                } else {
                    choice = theQuestion.answer4;
                }

                // message tells if the answer is correct or not
                if (choice.equals(theQuestion.correct)) {
                    message("Correct", "Your answer is (Correct)");

                } else {
                    message("Wrong", "Your answer is (Wrong)");

                }
            });

            // create a Grid pane
            GridPane viewPane = new GridPane();
            viewPane.setAlignment(Pos.CENTER);
            viewPane.setVgap(70);

            // HBox for arranging
            HBox H1 = new HBox(Answer1, Label1);
            HBox H2 = new HBox(Answer2, Label2);
            HBox H3 = new HBox(Answer3, Label3);
            HBox H4 = new HBox(Answer4, Label4);

            H1.setSpacing(10);
            H2.setSpacing(10);
            H3.setSpacing(10);
            H4.setSpacing(10);

            // VBox for arranging
            VBox V1 = new VBox(mainLabel, H1, H2, H3, H4);
            V1.setSpacing(25);

            // StackPane for the confirm button
            StackPane paneButton = new StackPane(confirm);

            viewPane.add(V1, 0, 0);
            viewPane.add(paneButton, 0, 1);

            // adding Home button
            finalPane.add(viewPane, 0, 1);
            finalPane.setAlignment(Pos.CENTER);
            finalPane.setVgap(200);

            // create a scene
            Scene viewScene = new Scene(finalPane, 650, 900);

            // set the scene

            window.setScene(viewScene);
            window.show();

        }
    }

    /* Edit scene */
    public void switchToEdit(String question) {

        // to check if the user chose a question from the combo box
        if (question == null) {
            // popup message
            message("Error", "You need to select a question");
        } else {

            // Home button
            homeButton.setOnAction(e -> home());
            VBox homeBox = new VBox();
            homeBox.getChildren().add(homeButton);
            GridPane finalPane = new GridPane();
            Font homeFont = new Font(fontName, 15);
            finalPane.add(homeButton, 0, 0);
            homeButton.setFont(homeFont);
            homeButton.setPrefSize(75, 50);

            // Font
            Font font1 = new Font(fontName, 40);
            Font font2 = new Font(fontName, 25);

            // getting the question answers
            String[] answerArray = getAnswers(question);

            // making an MCQ object
            theQuestion = new MCQ(question, answerArray[0], answerArray[1], answerArray[2], answerArray[3],
                    answerArray[4]);

            // create text fields
            TextField WriteQ = new TextField(question);
            TextField Write1 = new TextField(theQuestion.answer1);
            TextField Write2 = new TextField(theQuestion.answer2);
            TextField Write3 = new TextField(theQuestion.answer3);
            TextField Write4 = new TextField(theQuestion.answer4);

            WriteQ.setPrefSize(450, 50);
            Write1.setPrefSize(300, 30);
            Write2.setPrefSize(300, 30);
            Write3.setPrefSize(300, 30);
            Write4.setPrefSize(300, 30);

            // create labels
            Label LabelQ = new Label("Edit The Question", WriteQ);
            LabelQ.setContentDisplay(ContentDisplay.BOTTOM);
            LabelQ.setFont(font1);

            Label Label1 = new Label("  Answer 1 ", Write1);
            Label1.setContentDisplay(ContentDisplay.RIGHT);
            Label1.setFont(font2);

            Label Label2 = new Label("  Answer 2 ", Write2);
            Label2.setContentDisplay(ContentDisplay.RIGHT);
            Label2.setFont(font2);

            Label Label3 = new Label("  Answer 3 ", Write3);
            Label3.setContentDisplay(ContentDisplay.RIGHT);
            Label3.setFont(font2);

            Label Label4 = new Label("  Answer 4 ", Write4);
            Label4.setContentDisplay(ContentDisplay.RIGHT);
            Label4.setFont(font2);

            // Confirm creating Button
            Button ConfirmEditing = new Button("Confirm");
            ConfirmEditing.setFont(font2);
            ConfirmEditing.setPrefSize(450, 20);

            // RadioButton for correct answer
            ToggleGroup editGroup = new ToggleGroup();
            RadioButton Answer1 = new RadioButton();
            RadioButton Answer2 = new RadioButton();
            RadioButton Answer3 = new RadioButton();
            RadioButton Answer4 = new RadioButton();

            Answer1.setToggleGroup(editGroup);
            Answer2.setToggleGroup(editGroup);
            Answer3.setToggleGroup(editGroup);
            Answer4.setToggleGroup(editGroup);

            // confirm button functionality
            ConfirmEditing.setOnAction(e -> {
                String[] newArray = new String[mainArray.length];
                String choice = new String();
                // to check if the user filled all the text fields
                if ((WriteQ.getText().equals("") || Write1.getText().equals("") || Write2.getText().equals("")
                        || Write3.getText().equals("") || Write4.getText().equals(""))) {

                    message("Not completed", "you need to fill all the boxes");

                }
                // to check if the user choose a correct answer
                else if (!(Answer1.isSelected() || Answer2.isSelected() || Answer3.isSelected()
                        || Answer4.isSelected())) {
                    message("Not completed", "you need to chick the correct answer");

                }
                // Editing the question
                else {
                    // to check what is the correct answer
                    if (Answer1.isSelected()) {
                        choice = Write1.getText();

                    } else if (Answer2.isSelected()) {
                        choice = Write2.getText();

                    } else if (Answer3.isSelected()) {
                        choice = Write3.getText();

                    } else {
                        choice = Write4.getText();
                    }

                    newArray = new String[mainArray.length];
                    int index = 0;
                    for (int i = 0; i < mainArray.length; i++) {
                        // to change the data in the array
                        if (question.equals(mainArray[i])) {
                            newArray[index] = WriteQ.getText();
                            newArray[index + 1] = Write1.getText();
                            newArray[index + 2] = Write2.getText();
                            newArray[index + 3] = Write3.getText();
                            newArray[index + 4] = Write4.getText();
                            newArray[index + 5] = choice;
                            i = i + 5;
                            index = index + 6;

                        }

                        else {
                            newArray[index] = mainArray[i];
                            index++;
                        }

                    }

                    message("Change confirmation", "The question has been Updated");
                    // updating the data in the main array
                    updateMainArray(newArray);
                    // updating the data in the combo box
                    updateBox(toQuestionArray(mainArray));

                }
            });

            // create a Grid pane
            GridPane EditPane = new GridPane();
            EditPane.setAlignment(Pos.CENTER);
            EditPane.setVgap(70);

            // HBox for arranging
            HBox H1 = new HBox(Answer1, Label1);
            HBox H2 = new HBox(Answer2, Label2);
            HBox H3 = new HBox(Answer3, Label3);
            HBox H4 = new HBox(Answer4, Label4);

            // VBox
            VBox V1 = new VBox(H1, H2, H3, H4, ConfirmEditing);
            V1.setSpacing(25);
            // add text field
            EditPane.add(LabelQ, 0, 1);
            EditPane.add(V1, 0, 2);

            // Stock Pane
            StackPane RealPane = new StackPane();
            RealPane.getChildren().add(EditPane);

            // adding Home
            finalPane.add(RealPane, 0, 1);
            finalPane.setAlignment(Pos.CENTER);
            finalPane.setVgap(20);

            // create a scene
            Scene Editing = new Scene(finalPane, 650, 900);

            // set the scene
            window.setScene(Editing);
            window.show();

        }

    }

    /* Delete method */
    public void delete(String question) {
        if (question == null) {
            // popup message
            message("Error", "You need to select a question");
        } else {
            Stage popup = new Stage();

            // setting the stage
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Confirm");
            popup.setWidth(600);
            popup.setHeight(450);

            // adding the message
            Font font = new Font(fontName, 25);
            Label text = new Label("Are you sure you want to delete this question ?");
            Label theLabelQuestion = new Label("(" + question + ")");

            theLabelQuestion.setFont(font);
            text.setFont(font);

            // adding a close button
            Button closeButton = new Button("Close");
            closeButton.setFont(font);
            closeButton.setPrefSize(200, 100);

            closeButton.setOnAction(e -> {
                popup.close();
            });

            // adding Delete button
            Button deleteButton = new Button("Delete");
            deleteButton.setFont(font);
            deleteButton.setPrefSize(200, 100);

            deleteButton.setOnAction(e -> {
                String[] subArray;
                /* removing the question from the array */
                if (mainArray.length - 6 == 0) {
                    subArray = new String[1];

                    clearBox();

                } else {
                    subArray = new String[mainArray.length - 6];
                    int index = 0;

                    int length = mainArray.length;

                    for (int i = 0; i < mainArray.length; i++) {

                        if (question.equals(mainArray[i])) {
                            length--;
                            if (length <= 6) {
                                break;
                            } else {
                                System.out.println(length);
                                i = i + 6;
                                subArray[index] = mainArray[i];
                                index++;
                            }

                        } else {
                            length--;
                            subArray[index] = mainArray[i];
                            index++;

                        }

                    }

                }

                message("Delete confirmation", "The question has been Deleted");
                // updating the data in the main array
                updateMainArray(subArray);
                // updating the data in the combo box
                updateBox(toQuestionArray(mainArray));
                popup.close();

            });

            // alignment
            VBox vbox = new VBox(10);
            HBox hbox = new HBox(40);

            vbox.getChildren().addAll(text, theLabelQuestion);
            hbox.getChildren().addAll(closeButton, deleteButton);
            vbox.setAlignment(Pos.CENTER);
            hbox.setAlignment(Pos.CENTER);

            VBox finalBox = new VBox(10);
            finalBox.getChildren().addAll(vbox, hbox);
            finalBox.setAlignment(Pos.CENTER);

            // adding a scene
            Scene scene = new Scene(finalBox);
            popup.setScene(scene);
            popup.show();
        }
    }

    /* Home method */
    public void home() {
        // to get back to the primary stage
        window.setScene(mainScene);
        window.show();
    }

    /* full array to QuestionArray */
    public String[] toQuestionArray(String[] mainArray) {
        // making an array contains only the questions
        int length = mainArray.length / 6;
        if (length == 0) {
            length = 1;
        } else {
            length = length;
        }
        String[] questionArray = new String[length];
        int newIndex = 0;

        for (int qIndex = 0; qIndex <= mainArray.length - 6; qIndex = qIndex + 6) {
            questionArray[newIndex] = mainArray[qIndex];
            newIndex++;
        }

        return questionArray;
    }

    /* get Answers */
    public String[] getAnswers(String question) {
        // to get the answers for a question
        String[] answers = new String[5];
        int index = 0;
        for (int i = 0; !(mainArray[i].equals(question)); i++) {
            index++;
        }

        answers[0] = mainArray[index + 1];
        answers[1] = mainArray[index + 2];
        answers[2] = mainArray[index + 3];
        answers[3] = mainArray[index + 4];
        // the True answer
        answers[4] = mainArray[index + 5];

        return answers;
    }

    /* send message method */
    public void message(String title, String message) {
        // to send a message to the user

        Stage popup = new Stage();

        // setting the stage
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);
        popup.setWidth(400);
        popup.setHeight(250);

        // adding the message
        Font font = new Font(fontName, 25);
        Label lable = new Label(message);
        lable.setFont(font);

        // adding a close button
        Button closeButton = new Button("Close");
        closeButton.setFont(font);
        closeButton.setPrefSize(100, 50);

        closeButton.setOnAction(e -> {
            popup.close();
        });

        // alignment
        VBox vbox = new VBox(10);

        vbox.getChildren().addAll(lable, closeButton);
        vbox.setAlignment(Pos.CENTER);

        // adding a scene
        Scene scene = new Scene(vbox);
        popup.setScene(scene);
        popup.show();

    }

    /* update file method */
    public void updateFile(String filename, String[] QList) {
        // to update the data in the file
        try {
            FileOutputStream QfileW = new FileOutputStream(filename);
            ObjectOutputStream Write = new ObjectOutputStream(QfileW);
            for (String i : QList) {
                Write.writeBytes(i + "\n");
            }
            Write.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Get List Length method */
    public int getFileLength(String filename) {
        // uesd to make a list with the same length of the file
        int i = 0;
        try {
            FileInputStream QfileR = new FileInputStream(filename);
            ObjectInputStream Read = new ObjectInputStream(QfileR);
            String line = Read.readLine();
            while (line != null) {
                i++;
                line = Read.readLine();
            }
            Read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /* Questions List method */
    public String[] getQuestionsList(String filename) {
        // to get the data from the binary file and making the main array
        String[] QListfinal = new String[getFileLength(filename)];
        try {
            FileInputStream QfileR = new FileInputStream(filename);
            ObjectInputStream Read = new ObjectInputStream(QfileR);
            String line = Read.readLine();
            int i = 0;
            i = 0;

            while (line != null) {
                QListfinal[i] = line;
                line = Read.readLine();
                i++;
            }

            Read.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return QListfinal;
    }

    /* Update question box method */
    public void updateBox(String[] array) {
        // to update the data in the combo box
        questionBox.getItems().clear();
        for (int i = 0; i <= array.length - 1; i++) {
            questionBox.getItems().add(array[i]);
        }

    }

    /* to remove every item in the combo box */
    public void clearBox() {
        questionBox.getItems().clear();

    }

    /* search for question method */
    public boolean questionExists(String question) {
        // to make sure there is no duplicated questions in the main array
        for (int i = 0; i < mainArray.length; i++) {
            if (question.equals(mainArray[i])) {
                return true;
            }
        }
        return false;
    }

    /* updating the main array */
    public void updateMainArray(String[] array) {
        // updating the data in the main array
        mainArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            mainArray[i] = array[i];
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// class for the questions
class MCQ extends MCQBank {
    protected String question;
    protected String answer1;
    protected String answer2;
    protected String answer3;
    protected String answer4;
    protected String correct;

    MCQ(String question, String answer1, String answer2, String answer3, String answer4, String correct) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct = correct;
    }

}
