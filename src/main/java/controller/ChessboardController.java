/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ChessboardModel;
import view.ChessboardView;

/**
 *
 * @author Adam
 */
public class ChessboardController {
    
    private final ChessboardModel model;
    private final ChessboardView view;
    
    public ChessboardController(ChessboardModel model, ChessboardView view) {
        this.model = model;
        this.view = view;
    }
    
    private char [] positions;

}
