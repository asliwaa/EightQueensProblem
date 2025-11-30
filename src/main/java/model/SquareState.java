/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adam
 */
@Getter
@RequiredArgsConstructor
public enum SquareState {
    EMPTY("#"), QUEEN("X");
    
    private final String symbol;
    
    //Overriding toString method to display actual symbol (# or X) instead of it's name (EMPTY or QUEEN)
    @Override
    public String toString() {
        return symbol;
    }
}
