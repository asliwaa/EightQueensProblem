/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import model.ChessboardModel;
import model.Position;
import model.InvalidPositionException;
/**
 * Test class for ChessboardModel.
 * Covers public methods with parameterized tests for valid, invalid, and boundary scenarios.
 * @author Adam
 * @version 4.0
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
    
    /**
     * Parameterized test for the parse method with valid inputs.
     * Tests standard positions, lowercase handling, and boundary values (corners).
     * * @param inputPosition The string input representing a board position (e.g., "A1").
     * @param expectedRow The expected row index (0-7).
     * @param expectedCol The expected column index (0-7).
     */
    @ParameterizedTest(name = "Parsing valid position: {0} -> expecting row={1}, col={2}")
    @CsvSource({
        "A1, 0, 0", // Boundary: Top-left corner
        "H8, 7, 7", // Boundary: Bottom-right corner
        "A8, 7, 0", // Boundary: Bottom-left corner
        "H1, 0, 7", // Boundary: Top-right corner
        "D4, 3, 3", // Middle position
        "e2, 1, 4", // Lowercase handling
        "b7, 6, 1"  // Mixed case/random
    })
    public void testParseValidPositions(String inputPosition, int expectedRow, int expectedCol) {
        Position result = model.parse(inputPosition);
        
        assertAll("Verifying coordinates for " + inputPosition,
            () -> assertEquals(expectedRow, result.row(), "Row index should match"),
            () -> assertEquals(expectedCol, result.col(), "Column index should match")
        );
    }

    /**
     * Parameterized test for the parse method with null input.
     * Ensures that passing null throws a NullPointerException.
     * * @param input The null input.
     */
    @ParameterizedTest
    @NullSource
    public void testParseNullInput(String input) {
        assertThrows(NullPointerException.class, () -> {
            model.parse(input);
        }, "Parsing null should throw NullPointerException");
    }

    // ========================================================
    // ==== TEST METHOD: isValidPlacement(String pos)      ====
    // ========================================================

    /**
     * Parameterized test for isValidPlacement with valid empty positions.
     * These inputs should strictly pass without throwing exceptions.
     * * @param pos Valid position strings.
     */
    @ParameterizedTest(name = "Valid placement check for: {0}")
    @ValueSource(strings = {
        "A1", "H8", // Boundaries
        "a1", "h8", // Lowercase boundaries
        "C5", "F2", "d4" // Standard positions
    })
    public void testIsValidPlacementPositive(String pos) {
        assertDoesNotThrow(() -> model.isValidPlacement(pos), 
            "Valid position " + pos + " should not throw exception");
    }

    /**
     * Parameterized test for isValidPlacement with invalid formats or out-of-range values.
     * Tests empty strings, wrong lengths, and coordinates outside A-H/1-8.
     * * @param invalidPos Invalid position strings.
     */
    @ParameterizedTest(name = "Invalid format/range check for: {0}")
    @EmptySource // Tests empty string ""
    @ValueSource(strings = {
        "A",    // Too short
        "A11",  // Too long
        "ABC",  // Wrong format
        "I1",   // Column out of bound (>H)
        "A0",   // Row out of bound (<1)
        "A9",   // Row out of bound (>8)
        "1A",   // Wrong order
        "Z9"    // Completely out of range
    })
    public void testIsValidPlacementNegativeFormat(String invalidPos) {
        assertThrows(InvalidPositionException.class, () -> {
            model.isValidPlacement(invalidPos);
        }, "Position " + invalidPos + " should throw InvalidPositionException");
    }

    /**
     * Parameterized test checking if isValidPlacement detects occupied fields.
     * First places a queen at a setup position, then tries to validate placing another one there.
     * * @param setupPos Position to place the first queen.
     * @param targetPos Position to check (should be occupied).
     */
    @ParameterizedTest(name = "Occupied check: Place at {0}, try {1}")
    @CsvSource({
        "A1, A1",
        "H8, H8",
        "C3, C3"
    })
    public void testIsValidPlacementNegativeOccupied(String setupPos, String targetPos) {
        // Arrange
        model.placeQueen(setupPos);
        
        // Act & Assert
        assertThrows(InvalidPositionException.class, () -> {
            model.isValidPlacement(targetPos);
        }, "Placing on an occupied field (" + targetPos + ") should throw exception");
    }

    // ========================================================
    // ==== TEST METHOD: isSolutionValid()                 ====
    // ========================================================

    /**
     * Parameterized test for isSolutionValid covering both SUCCESS and FAILURE scenarios.
     * Takes a sequence of moves (comma-separated), applies them, and asserts the validity of the board.
     * * @param movesSequence String containing moves to simulate (e.g. "A1, B3").
     * @param expectedResult Boolean: true if solution should be valid, false otherwise.
     */
    @ParameterizedTest(name = "Scenario: [{0}] -> Expected: {1}")
    @CsvSource({
        // --- Positive Scenarios (Valid) ---
        "'', true",                   // Boundary: Empty board is valid
        "'A1', true",                 // Boundary: Single queen is valid
        "'A1, B3', true",             // Knight's move (safe)
        "'A1, C2', true",             // Safe distance
        "'A1, B3, C5, D2, E4', true", // Multiple safe queens

        // --- Negative Scenarios (Invalid) ---
        "'A1, A5', false",            // Attack: Same Column
        "'H1, H8', false",            // Attack: Same Column (Boundary)
        "'A1, H1', false",            // Attack: Same Row
        "'A8, H8', false",            // Attack: Same Row (Boundary)
        "'A1, B2', false",            // Attack: Diagonal (Main)
        "'A1, H8', false",            // Attack: Diagonal (Longest)
        "'C1, A3', false",            // Attack: Diagonal (Secondary)
        "'A1, C2, A4', false"         // Mixed: Two safe, third attacks first (Column)
    })
    public void testIsSolutionValidParameterized(String movesSequence, boolean expectedResult) {
        // 1. Parse inputs: split string by comma if it's not empty
        if (movesSequence != null && !movesSequence.isEmpty()) {
            String[] moves = movesSequence.split(",\\s*");
            
            // 2. Apply moves (State setup)
            for (String move : moves) {
                model.placeQueen(move);
            }
        }
        
        // 3. Verify logic
        assertEquals(expectedResult, model.isSolutionValid(), 
            "Validation result for sequence [" + movesSequence + "] is incorrect.");
    }

}
