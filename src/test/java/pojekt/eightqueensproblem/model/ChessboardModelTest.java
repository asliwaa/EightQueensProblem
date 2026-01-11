package pojekt.eightqueensproblem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ChessboardModel} class.
 * Tests cover all public methods (excluding constructors and accessors).
 * All tests are parameterized and cover valid, invalid, and boundary scenarios.
 *
 * @author Adam
 * @version 5.0
 */
public class ChessboardModelTest {

    /**
     * Instance of the model to be tested.
     */
    private ChessboardModel model;

    /**
     * Sets up a fresh model instance before each test method execution.
     * Ensures test isolation.
     */
    @BeforeEach
    public void setUp() {
        model = new ChessboardModel();
    }

    // ========================================================
    // ==== TEST METHOD: parse(String pos)                 ====
    // ========================================================

    /**
     * Parameterized test for the {@code parse} method with valid inputs.
     * Checks if string coordinates are correctly converted to 0-based array indices.
     * Covers board boundaries (A1, H8, A8, H1) and case insensitivity.
     *
     * @param inputPosition The string input representing a position (e.g., "A1").
     * @param expectedRow The expected row index (0-7).
     * @param expectedCol The expected column index (0-7).
     */
    @ParameterizedTest(name = "Parsing valid position: {0} -> expecting row={1}, col={2}")
    @CsvSource({
        "A1, 0, 0", // Boundary: Top-left
        "H8, 7, 7", // Boundary: Bottom-right
        "a1, 0, 0", // Lowercase handling
        "D4, 3, 3", // Middle value
        "H1, 0, 7", // Boundary: Top-right
        "A8, 7, 0"  // Boundary: Bottom-left
    })
    public void testParseValid(String inputPosition, int expectedRow, int expectedCol) {
        Position result = model.parse(inputPosition);
        
        assertAll("Verifying position coordinates",
            () -> assertEquals(expectedRow, result.row(), "Row index mismatch"),
            () -> assertEquals(expectedCol, result.col(), "Column index mismatch")
        );
    }

    /**
     * Parameterized test for the {@code parse} method forcing runtime exceptions.
     * Ensures that null or empty/short inputs cause appropriate runtime exceptions.
     *
     * @param invalidInput The invalid input string.
     */
    @ParameterizedTest(name = "Parsing invalid input: {0} should throw Exception")
    @NullSource
    @ValueSource(strings = {"", "A"})
    public void testParseInvalid(String invalidInput) {
        assertThrows(RuntimeException.class, () -> {
            model.parse(invalidInput);
        }, "Should throw RuntimeException for invalid input structure");
    }

    // ========================================================
    // ==== TEST METHOD: isValidPlacement(String pos)      ====
    // ========================================================

    /**
     * Parameterized test for {@code isValidPlacement} with valid inputs.
     * These positions should be accepted without throwing any exception.
     *
     * @param pos Valid position string.
     */
    @ParameterizedTest(name = "Valid placement check: {0}")
    @ValueSource(strings = {"A1", "H8", "b2", "G7", "d4"})
    public void testIsValidPlacementSuccess(String pos) {
        assertDoesNotThrow(() -> model.isValidPlacement(pos), 
            "Valid position " + pos + " should not throw exception");
    }

    /**
     * Parameterized test for {@code isValidPlacement} with invalid formats or range.
     * Checks for length errors, null values, and positions outside the A-H, 1-8 range.
     *
     * @param invalidPos Invalid position string.
     */
    @ParameterizedTest(name = "Invalid format/range check: {0}")
    @NullSource
    @ValueSource(strings = {
        "",     // Empty
        "A",    // Too short
        "A11",  // Too long
        "Z9",   // Out of range (Char)
        "A0",   // Out of range (Digit too low)
        "A9",   // Out of range (Digit too high)
        "1A"    // Wrong order
    })
    public void testIsValidPlacementFormatAndRange(String invalidPos) {
        assertThrows(InvalidPositionException.class, () -> {
            model.isValidPlacement(invalidPos);
        }, "Should throw InvalidPositionException for invalid format or range");
    }

    /**
     * Parameterized test for {@code isValidPlacement} checking occupied fields.
     * Places a queen first, then attempts to validate placing another queen on the same spot.
     *
     * @param setupPos Position to place the initial queen.
     * @param targetPos Position to attempt to place again (should fail).
     */
    @ParameterizedTest(name = "Occupied check: Place {0}, Try {1}")
    @CsvSource({
        "A1, A1",
        "H8, H8",
        "C3, C3"
    })
    public void testIsValidPlacementOccupied(String setupPos, String targetPos) {
        // Arrange
        model.placeQueen(setupPos);
        
        // Act & Assert
        assertThrows(InvalidPositionException.class, () -> {
            model.isValidPlacement(targetPos);
        }, "Should throw InvalidPositionException when position is already occupied");
    }

    // ========================================================
    // ==== TEST METHOD: isSolutionValid()                 ====
    // ========================================================

    /**
     * Parameterized test for {@code isSolutionValid} covering game logic validation.
     * Accepts a sequence of moves (comma-separated) and the expected validity result.
     * Covers empty board, safe placements, and various attacking scenarios (row, col, diagonal).
     *
     * @param movesSequence Sequence of moves to apply (e.g., "A1, B3").
     * @param expectedResult Expected boolean result from isSolutionValid().
     */
    @ParameterizedTest(name = "Scenario: [{0}] -> Expected Valid: {1}")
    @CsvSource({
        // --- Positive Scenarios (Valid) ---
        "'', true",                   // Boundary: Empty board is valid
        "'A1', true",                 // Boundary: Single queen is valid
        "'A1, B3', true",             // Knight's move (safe)
        "'A1, C2', true",             // Safe distance
        "'A1, B3, C5, D2, E4', true", // Multiple safe queens

        // --- Negative Scenarios (Invalid - Attacks) ---
        "'A1, A5', false",            // Attack: Same Column
        "'H1, H8', false",            // Attack: Same Column (Boundary)
        "'A1, H1', false",            // Attack: Same Row
        "'A8, H8', false",            // Attack: Same Row (Boundary)
        "'A1, B2', false",            // Attack: Diagonal (Main)
        "'A1, H8', false",            // Attack: Diagonal (Longest)
        "'C1, A3', false",            // Attack: Diagonal (Secondary)
        "'A1, B3, A2', false"         // Mixed: Two safe, third attacks first (Column)
    })
    public void testIsSolutionValidParameterized(String movesSequence, boolean expectedResult) {
        // 1. Arrange: Apply moves if any
        if (movesSequence != null && !movesSequence.isEmpty()) {
            String[] moves = movesSequence.split(",\\s*");
            for (String move : moves) {
                // We assume input is valid for placement to focus on logic validation
                model.placeQueen(move);
            }
        }
        
        // 2. Act
        boolean result = model.isSolutionValid();
        
        // 3. Assert
        assertEquals(expectedResult, result, 
            "Validation result incorrect for sequence: " + movesSequence);
    }
}