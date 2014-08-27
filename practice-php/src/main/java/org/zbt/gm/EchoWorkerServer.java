import java.io.IOException;

import org.gearman.Gearman;
import org.gearman.GearmanServer;

/**
 * The echo worker/server starts a new server and polls jobs from it job server
 *
 * The echo worker illustrates how to setup a basic worker
 */
public class EchoWorkerServer {
    public static void main(String... args) throws IOException {

        /*
         * Create a Gearman instance
         */
        Gearman gearman = Gearman.createGearman();

        try {

            /*
             * Start a new job server. The resulting server will be running in
             * the local address space.
             *
             * Parameter 1: The port number to listen on
             *
             * throws IOException
             */
            GearmanServer server = gearman
                    .startGearmanServer(EchoWorker.ECHO_PORT);

            /*
             * Create a gearman worker. The worker poll jobs from the server and
             * executes the corresponding GearmanFunction
             */


        } catch (IOException ioe) {

            /*
             * If an exception occurs, make sure the gearman service is shutdown
             */
            gearman.shutdown();

            // forward exception
            throw ioe;
        }
    }
}