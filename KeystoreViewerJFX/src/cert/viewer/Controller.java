package cert.viewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {

    @FXML
    private TextField filePathField;
    private String container;

    @FXML
    private ListView leftPane;

    @FXML
    private TextFlow rightPaneFlow;

    private Map<String, ArrayList<Text>> certificateMap = new HashMap();

    final String VALIDITYPERIOD = "Period of validity%s";
    final String VALID = "valid";
    final String INVALID = "non valid";
    final String CREATOR = "Issuer%s";
    final String NUMBER = "Serial number%s";
    final String START = "License start%s";
    final String END = "License expiration%s";
    final String OWNER = "Owner%s";
    final String ALGORITHM = "Signing algorithm%s";
    final String SIGN = "Certificate signature%s";
    final String NEWLINE = "\n";
    final String COLNEWLINE = " :\n      ";

    KeyStore keyStore = null; // key/certificate store
    ArrayList<String> listAliases = new ArrayList<>();

    public void actionSearch(ActionEvent actionEvent) {
        loadKeyStore();
    }

    void loadKeyStore() {
        FileInputStream fis;
        // Choose a keystore :
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to open");
        fileChooser.setInitialDirectory(new File("./"));  // ./ - means the Directory where the program started
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            container = file.getAbsolutePath();
            filePathField.setText(container);

            try {
                // Reading from keystore
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                fis = new FileInputStream(container);
                keyStore.load(fis, null);
                Enumeration<String> E = keyStore.aliases();

                // storing the certificates
                Vector<String> certs = new Vector<>();
                while (E.hasMoreElements()) {
                    certs.add((String) E.nextElement());
                }
                for (String certificate : certs) {
                    listAliases.add(certificate);
                }
                // creating an Observable List
                ObservableList<String> leftPaneWindowList = FXCollections.observableArrayList();
                for (int i = 0; i < listAliases.size(); i++) {
                    leftPaneWindowList.add(listAliases.get(i));
                }
                leftPane.setItems(leftPaneWindowList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // this is when you click on a certificate in the left ListView:
    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        System.out.println("clicked on " + leftPane.getSelectionModel().getSelectedItem());
        rightPaneFlow.getChildren().clear();
        showCertificate(leftPane.getSelectionModel().getSelectedItem().toString());
        System.out.println(leftPane.getSelectionModel().getSelectedItem().toString());
    }

    void showCertificate(final String name) {
        if (certificateMap.containsKey(name)) {
            rightPaneFlow.getChildren().addAll(certificateMap.get(name));
        } else {
            Certificate certificate = null;
            try {
                // reading the certificate
                certificate = keyStore.getCertificate(name);
                X509Certificate x509Certificate = (X509Certificate) certificate;

                String valid = "";
                try {
                    x509Certificate.checkValidity();
                    valid = VALID;
                } catch (Exception ex) {
                    valid = INVALID;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String end = sdf.format(x509Certificate.getNotAfter());
                String start = sdf.format(x509Certificate.getNotBefore());
                String sign = new sun.misc.BASE64Encoder().encode(x509Certificate.getSignature());
                String creator = x509Certificate.getIssuerDN().getName();
                String owner = x509Certificate.getSubjectDN().getName();
                String number = String.valueOf(x509Certificate.getSerialNumber());
                String algo = x509Certificate.getSigAlgName();

                String version = "" + x509Certificate.getVersion();

                // you can all add these in an array list and then add it to the TextFlow
                Text valPeriodTitle = new Text(createTitle(VALIDITYPERIOD));
                valPeriodTitle.setStyle("-fx-font-weight: bold");
                Text valPeriodText = new Text(createLineText(valid));

                Text creatorTitle = new Text(createTitle(CREATOR));
                creatorTitle.setStyle("-fx-font-weight: bold");
                Text creatorText = new Text(createLineText(creator));

                Text serNumTitle = new Text(createTitle(NUMBER));
                serNumTitle.setStyle("-fx-font-weight: bold");
                Text serNumText = new Text(createLineText(number));

                Text startDateTitle = new Text(createTitle(START));
                startDateTitle.setStyle("-fx-font-weight: bold");
                Text startDateText = new Text(createLineText(start));

                Text endDateTitle = new Text(createTitle(END));
                endDateTitle.setStyle("-fx-font-weight: bold");
                Text endDateText = new Text(createLineText(end));

                Text ownerCertTitle = new Text(createTitle(OWNER));
                ownerCertTitle.setStyle("-fx-font-weight: bold");
                Text ownerCertText = new Text(createLineText(owner));

                Text algorithmTitle = new Text(createTitle(ALGORITHM));
                algorithmTitle.setStyle("-fx-font-weight: bold");
                Text algorithmText = new Text(createLineText(algo));

                Text signatureTitle = new Text(createTitle(SIGN));
                signatureTitle.setStyle("-fx-font-weight: bold");
                Text signatureText = new Text(createLineText(sign));

                Text versionTitle = new Text(createTitle("Version%s"));
                versionTitle.setStyle("-fx-font-weight: bold");
                Text versionPrText = new Text(createLineText(version));

                // prepare a Store Map for loaded certificates:
                ArrayList<Text> certList = new ArrayList<>();
                certList.add(valPeriodTitle);
                certList.add(valPeriodText);
                certList.add(creatorTitle);
                certList.add(creatorText);
                certList.add(serNumTitle);
                certList.add(serNumText);
                certList.add(startDateTitle);
                certList.add(startDateText);
                certList.add(endDateTitle);
                certList.add(endDateText);
                certList.add(ownerCertTitle);
                certList.add(ownerCertText);
                certList.add(algorithmTitle);
                certList.add(algorithmText);
                certList.add(signatureTitle);
                certList.add(signatureText);
                certList.add(versionTitle);
                certList.add(versionPrText);

                // save the certificate in the Map to store the old certificates there, as well
                certificateMap.put(name, certList);

                rightPaneFlow.getChildren().addAll(valPeriodTitle, valPeriodText, creatorTitle, creatorText, serNumTitle, serNumText, startDateTitle, startDateText,
                        endDateTitle, endDateText, ownerCertTitle, ownerCertText, algorithmTitle, algorithmText, signatureTitle, signatureText, versionTitle, versionPrText);

                rightPaneFlow.setPadding(new Insets(7, 7, 7, 7));
            } catch (KeyStoreException keystoreException) {
                keystoreException.printStackTrace();
            } catch (NullPointerException nullpointerException) {
                nullpointerException.printStackTrace();
            }
        }// end of else
    } // end of method

    protected String createTitle(String inputString) {
        return String.format(inputString, COLNEWLINE);
    }

    protected String createLineText(String inputString) {
        return String.format(inputString + NEWLINE);
    }
}
