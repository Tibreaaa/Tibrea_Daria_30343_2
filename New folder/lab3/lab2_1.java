package lab3;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.TableParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class lab2_1 {

    // FLRS table for T0 that implements P0-P1
    String differentiator = "" + //
            "{[<ZR><NM><NL><NL><NL>]" + //
            " [<PM><ZR><NM><NL><NL>]" + //
            " [<PL><PM><ZR><NM><NL>]" + //
            " [<PL><PL><PM><ZR><NM>]" + //
            " [<PL><PL><PL><PM><ZR>]}";

    // FLRS table for T1 that makes the selection according to the result P0-P1 (positive or negative)
    String separator="{[<NL,FF><NL,FF><FF,FF><FF,PL><FF,PL>]}";

    private int modelRunCounter = 0; // Counter for FLETPN model runs

    public lab2_1() {

        // the Petri network is being constructed
        TableParser parser = new TableParser();
        FuzzyPetriNet petriNet = new FuzzyPetriNet();

        // adding the input places
        int p0Inp = petriNet.addInputPlace();
        int p1Inp = petriNet.addInputPlace();

        // attaching to the transition t0 the corresponding FLRS table
        TwoXOneTable diffTable = parser.parseTwoXOneTable(differentiator);
        int t0 = petriNet.addTransition(0, diffTable);

        // add the arcs and the weights corresponding to the Petri Net
        petriNet.addArcFromPlaceToTransition(p0Inp, t0, 1.0);
        petriNet.addArcFromPlaceToTransition(p1Inp, t0, 1.0);

        // add the places and arc corresponding to the Petri Net
        int p2 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t0, p2);

        int t1 = petriNet.addTransition(0, parser.parseOneXTwoTable(separator));
        petriNet.addArcFromPlaceToTransition(p2, t1, 1.0);

        int p3 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p3);
        int p4 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p4);

        int t2Out =  petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p3, t2Out, 1.0);

        // associating an action of the output transition t2
        petriNet.addActionForOuputTransition(t2Out, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println( "Output From Transition 2: " + t.shortString());
                modelRunCounter++; // Increment the counter
            }
        });

        int t3Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p4, t3Out, 1.0);

        // associating an action of the output transition t3
        petriNet.addActionForOuputTransition(t3Out, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println("Output From Transition  3: " + t.shortString());
                modelRunCounter++; // Increment the counter
            }
        });

        // creating the date Petri Net executor and specifying the period in milliseconds
        AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);

        //  creating an object for visualizing the behavior of the Petri net
        FullRecorder recorder = new FullRecorder();
        executor.setRecorder(recorder);
        FuzzyDriver driver =   FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

        // launching the execution of the thread that contains the executor
        (new Thread(executor)).start();

        // Constructing the dictionary collection (map) for inputs
        Map<Integer, FuzzyToken> inps = new HashMap<>();
        inps.put(p0Inp, driver.fuzzifie(0.0));
        inps.put(p1Inp, driver.fuzzifie(0.0));

        // Placing the input tokens for the executor
        executor.putTokenInInputPlace(inps);

        // Pause for visualization purposes
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.stop(); // Stop the executor

        // Display the number of times the FLETPN model ran
        System.out.println("FLETPN model ran " + modelRunCounter + " times.");

        // visualizing the Petri Net and its behavior
        FuzzyPVizualzer.visualize(petriNet, recorder);
    }

    public static void main(String[] main) {
        new lab2_1();
    }
}
