/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import model.ChessboardModel;
import model.Position;
import model.InvalidPositionException;

/**
 *
 * @author Lenovo
 */
public class ModelAssertionTest {
    private ChessboardModel model;
    
    public ModelAssertionTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        model = new ChessboardModel();
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    // ========================
    // ====== TEST PARSE ======
    // ========================
    
    // === Positive tests ===

    @Test
    public void testParseCornerA1() {
        String pos = "A1";
        Position result = model.parse(pos);
        
        assertAll("Checking parsing of A1 position",
                () -> assertEquals(0, result.row()),
                () -> assertEquals(0, result.col())        
                        );
    }
    
    @Test
    public void testParseCornerH8() {
        String pos = "H8";
        Position result = model.parse(pos);
        
        assertAll("Checking parsing for H8 position",
                () -> assertEquals(7, result.row()),
                () -> assertEquals(7, result.col())
                );
    }
    
    @Test
    public void testParseLowerCase() {
        String pos = "e2";
        Position result = model.parse(pos);
        
        assertAll("Checking parsing for a position entered in lowercase",
                () -> assertEquals(1, result.row()),
                () -> assertEquals(4, result.col())
                );
    }
    
    @Test
    public void testParseMiddle() {
        String pos = "D4";
        Position result = model.parse(pos);
        
        assertAll("Checking parsing for a position entered in lowercase",
                () -> assertEquals(3, result.row()),
                () -> assertEquals(3, result.col())
                );
    }
    
    // === Negative tests ===
    
    @Test
    public void testParseNull() {
        assertThrows(NullPointerException.class, ()-> {
            model.parse(null);
        });
    }
    
    // === Parameterized test ===
    
    @ParameterizedTest()
    @CsvSource({
        "A1, 0, 0",
        "H8, 7, 7",
        "D4, 3, 3",
        "B2, 1, 1",  
        "a1, 0, 0",   
        "h8, 7, 7"    
    })
    void testParseParameterized(String inputPosition, int expectedRow, int expectedCol) {
        Position result = model.parse(inputPosition);

        assertAll("Verifying given position: " + inputPosition,
            () -> assertEquals(expectedRow, result.row(), "Wrong row index"),
            () -> assertEquals(expectedCol, result.col(), "Wrong column index")
        );
    }
    
    
    // ==== TEST ValidPlacement ====
    
    @Test
    public void testValidPlacement() {
        assertAll("Valid placements",
                () -> assertDoesNotThrow(() -> model.isValidPlacement("A1"),"A1 is okay"),
                () -> assertDoesNotThrow(() -> model.isValidPlacement("H8"),"H8 is okay"),
                () -> assertDoesNotThrow(() -> model.isValidPlacement("e4"),"e4 is okay")
                );
    }
    
    // ==== TEST SolutionValid ====
    
    @Test
    public void testEmptyBoardIsValid() {
        boolean isValid = model.isSolutionValid();
        
        assertTrue(isValid, "No queens = no attacks");
    }
    
    @Test
    public void testPairIsValid() {
        model.placeQueen("A1");
        model.placeQueen("B3");
        
        boolean isValid = model.isSolutionValid();
        assertTrue(isValid);
    }
    
    @Test
    public void testAllIsValid() {
        model.placeQueen("A1");
        model.placeQueen("b7");
        model.placeQueen("c5");
        model.placeQueen("d8");
        model.placeQueen("e2");
        model.placeQueen("f4");
        model.placeQueen("g6");
        model.placeQueen("h3");
        
        boolean isValid = model.isSolutionValid();
        assertTrue(isValid);
    }
    
}
