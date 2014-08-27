import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;

/**
 * The echo worker polls jobs from a job server and execute the echo function.
 *
 * The echo worker illustrates how to setup a basic worker
 */
public class EchoWorker implements GearmanFunction {

    /** The echo function name */
    public final static String ECHO_FUNCTION_NAME = "OPEN_FILE";

    /** The host address of the job server */
    public static final String ECHO_HOST = "127.0.0.1";

    /** The port number the job server is listening on */
    public static final int ECHO_PORT = 4730;

    public static void main(String... args) {

        System.out.println(EchoWorker.ECHO_FUNCTION_NAME);
        registWorker();


    }

    public static void registWorker(){
        /*
         * Create a Gearman instance
         */
        Gearman gearman = Gearman.createGearman();

        /*
         * Create the job server object. This call creates an object represents
         * a remote job server.
         *
         * Parameter 1: the host address of the job server.
         * Parameter 2: the port number the job server is listening on.
         *
         * A job server receives jobs from clients and distributes them to
         * registered workers.
         */
        GearmanServer server = gearman.createGearmanServer(
                EchoWorker.ECHO_HOST, EchoWorker.ECHO_PORT);

        /*
         * Create a gearman worker. The worker poll jobs from the server and
         * executes the corresponding GearmanFunction
         */
        System.out.println(server.toString());
        GearmanWorker worker = gearman.createGearmanWorker();
        /*
         *  Tell the worker how to perform the echo function
         */
        worker.addFunction(EchoWorker.ECHO_FUNCTION_NAME, new EchoWorker());
        worker.getServerCount();
        /*
         *  Tell the worker that it may communicate with the this job server
         */
        boolean b = worker.addServer(server);
        System.out.println(b);
    }


    public byte[] work(String function, byte[] data,
            GearmanFunctionCallback callback) throws Exception {

        /*
         * The work method performs the gearman function. In this case, the echo
         * function simply returns the data it received
         */

        System.out.println(new String(data));
        return data;
    }

}