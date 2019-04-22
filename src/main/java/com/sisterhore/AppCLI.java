package com.sisterhore;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import com.sisterhore.controller.Controller;
import com.sisterhore.view.CLIView;
import com.sisterhore.view.View;

public class AppCLI {
    public static void main(String[] args) throws IOException, URISyntaxException {
//      CRDTTest test = new CRDTTest();
//      test.run();
        int port = args.length != 0 ? Integer.parseInt(args[0]) : 8887;
        Controller controller = null;
        View view = null;
        try {
            controller = new Controller(port);
            view = new CLIView(controller);
            view.start(args);
        } catch (UnknownHostException e) {
            System.out.println("Exception occured: " + e);
        }
    }
}
