package co.apt;

import co.apt.service.OutputService;
import co.apt.service.impl.OutputServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputServiceImplTest {

    OutputService outputService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void init(){
        outputService = new OutputServiceImpl();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void test_dispense(){
        outputService.dispense("hot_tea");
        Assert.assertEquals("hot_tea is prepared\n", outContent.toString());
    }

    @Test
    public void test_outputUnavailable(){
        outputService.outputUnavailable("hot_tea", "sugar");
        Assert.assertEquals("hot_tea cannot be prepared because sugar is not available\n", outContent.toString());
    }

    @Test
    public void test_outputInsufficient(){
        outputService.outputInsufficient("hot_tea", "sugar");
        Assert.assertEquals("hot_tea cannot be prepared because item sugar is not sufficient\n", outContent.toString());
    }

    @Test
    public void test_refillRequired(){
        outputService.refillRequired("sugar");
        Assert.assertEquals("Refill required for : sugar\n", outContent.toString());
    }

    @Test
    public void test_refilled(){
        outputService.refilled("sugar", 200);
        Assert.assertEquals("Refilled item sugar by quantity 200\n", outContent.toString());
    }
}
