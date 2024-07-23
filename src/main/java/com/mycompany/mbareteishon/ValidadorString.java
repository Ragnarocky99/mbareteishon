/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon;

/**
 *
 * @author nahue
 */
public class ValidadorString {
    
    public static boolean isValid(String input) {
        // La expresión regular ^[0-9-]+$ asegura que solo contenga números y guiones
        String regex = "^\\d+-?\\d*$";
        return input.matches(regex);
    }
    
}
