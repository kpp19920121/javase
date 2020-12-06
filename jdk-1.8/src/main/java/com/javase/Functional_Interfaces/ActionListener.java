package com.javase.Functional_Interfaces;

import java.awt.event.ActionEvent;


@FunctionalInterface
public interface ActionListener {
	
	public abstract void actionPerformed(ActionEvent e) ;

}
